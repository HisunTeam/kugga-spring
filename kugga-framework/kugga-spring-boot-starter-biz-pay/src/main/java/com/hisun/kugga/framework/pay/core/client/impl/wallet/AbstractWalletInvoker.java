package com.hisun.kugga.framework.pay.core.client.impl.wallet;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import com.hisun.kugga.framework.common.wallet.WalletGeneralReqDTO;
import com.hisun.kugga.framework.common.wallet.WalletGeneralRspDTO;
import com.hisun.kugga.framework.common.wallet.WalletHeader;
import com.hisun.kugga.framework.pay.core.client.impl.wallet.config.WalletProperties;
import com.hisun.kugga.framework.pay.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;

import static com.hisun.kugga.framework.pay.core.enums.CommConstant.*;

/**
 * @author: zhou_xiong
 */
@Slf4j
public abstract class AbstractWalletInvoker implements Signable<WalletBaseReqBody, JSONObject> {
    public static final String SUCCESS = "KONT0000";
    private WalletProperties walletProperties;

    public AbstractWalletInvoker(WalletProperties walletProperties) {
        this.walletProperties = walletProperties;
    }

    @Override
    public Boolean shouldSign() {
        return walletProperties.getShouldSign();
    }

    public <RspBody, ReqBody extends WalletBaseReqBody> RspBody invokePost(String url, ReqBody reqBody, Class<RspBody> rspBodyClass) {
        HttpRequest httpRequest = HttpRequest.post(url);
        reqBody.setAppid(walletProperties.getAppid());
        if (shouldSign()) {
            String nonceStr = SignatureUtil.generateNonceStr();
            String signature = doSignature(nonceStr, reqBody);
            httpRequest.header(FIELD_APPID, walletProperties.getAppid())
                    .header(FIELD_NONCE_STR, nonceStr)
                    .header(FIELD_SIGN, signature)
                    .header(FIELD_SIGN_TYPE, walletProperties.getSignType().name());
        }
        WalletGeneralReqDTO walletGeneralReqDTO = new WalletGeneralReqDTO().setBody(reqBody);
        HttpResponse httpResponse = httpRequest.body(JSONUtil.toJsonStr(walletGeneralReqDTO)).execute();
        WalletGeneralRspDTO result = JSONUtil.toBean(httpResponse.body(), WalletGeneralRspDTO.class);
        if (!StrUtil.equals(SUCCESS, result.getMsgCd())) {
            failed(url, result);
        }
        RspBody rspBody = JSONUtil.toBean((JSONObject) result.getBody(), rspBodyClass);
        if (shouldSign()) {
            WalletHeader walletHeader = new WalletHeader()
                    .setAppid(httpResponse.header(FIELD_APPID))
                    .setNonceStr(httpResponse.header(FIELD_NONCE_STR))
                    .setSign(httpResponse.header(FIELD_SIGN))
                    .setSignType(httpResponse.header(FIELD_SIGN_TYPE));
            verifySign(walletHeader, (JSONObject) result.getBody());
        }
        log.info("请求返回结果：requestId:{}, body:{}", result.getRequestId(), rspBody);
        return rspBody;
    }

    protected abstract void failed(String url, WalletGeneralRspDTO walletGeneralRspDTO);

}
