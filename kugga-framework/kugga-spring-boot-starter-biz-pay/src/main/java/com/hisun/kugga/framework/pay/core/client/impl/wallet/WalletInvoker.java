package com.hisun.kugga.framework.pay.core.client.impl.wallet;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hisun.kugga.framework.common.enums.WalletEnum;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import com.hisun.kugga.framework.common.wallet.WalletGeneralRspDTO;
import com.hisun.kugga.framework.common.wallet.WalletHeader;
import com.hisun.kugga.framework.pay.core.client.impl.wallet.config.WalletProperties;
import com.hisun.kugga.framework.pay.core.enums.SignType;
import com.hisun.kugga.framework.pay.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.hisun.kugga.framework.pay.core.enums.CommConstant.*;
import static com.hisun.kugga.framework.pay.core.enums.PayFrameworkErrorCodeConstants.*;

/**
 * @author: zhou_xiong
 */
@Slf4j
public class WalletInvoker extends AbstractWalletInvoker {
    private WalletProperties walletProperties;

    public WalletInvoker(WalletProperties walletProperties) {
        super(walletProperties);
        this.walletProperties = walletProperties;
    }

    @Override
    public String doSignature(String nonceStr, WalletBaseReqBody walletBaseReqBody) {
        Map<String, String> data = generateData(walletBaseReqBody);
        data.put(FIELD_APPID, walletBaseReqBody.getAppid());
        data.put(FIELD_NONCE_STR, nonceStr);
        data.put(FIELD_SIGN_TYPE, walletProperties.getSignType().name());
        String signature = null;
        try {
            signature = SignatureUtil.generateSignature(data, walletProperties.getAppSecret(), walletProperties.getSignType());
        } catch (Exception e) {
            log.error("生成签名失败，数据为：{}", walletBaseReqBody);
            ServiceException.throwServiceException(SIGN_FAILED);
        }
        return signature;
    }

    @Override
    public void verifySign(WalletHeader walletHeader, JSONObject rspBody) {
        Map<String, String> paramMap = parse(rspBody);
        paramMap.put(FIELD_SIGN_TYPE, walletHeader.getSignType());
        paramMap.put(FIELD_APPID, walletHeader.getAppid());
        paramMap.put(FIELD_NONCE_STR, walletHeader.getNonceStr());
        try {
            boolean valid = SignatureUtil.isSignatureValid(paramMap, walletHeader.getSign(), walletProperties.getAppSecret(),
                    StrUtil.equals(walletHeader.getSignType(), SignType.MD5.name()) ? SignType.MD5 : SignType.HMACSHA256);
            if (!valid) {
                ServiceException.throwServiceException(SIGN_VERIFY_FAILED);
            }
        } catch (Exception e) {
            log.error("验签失败，数据为：{}", paramMap);
            ServiceException.throwServiceException(SIGN_VERIFY_FAILED);
        }
    }

    private Map<String, String> parse(JSONObject rspBody) {
        Map<String, String> paramMap = new HashMap<>(16);
        rspBody.entrySet().stream().forEach(entry -> {
            if (ObjectUtil.isNotNull(entry.getValue())) {
                if (ClassUtil.isBasicType(entry.getValue().getClass()) || entry.getValue() instanceof BigDecimal) {
                    paramMap.put(entry.getKey(), StrUtil.toString(entry.getValue()));
                } else {
                    // value值使用json工具进行转换
                    paramMap.put(entry.getKey(), JSONUtil.toJsonStr(entry.getValue()));
                }
            }
        });
        return paramMap;
    }


    private Map<String, String> generateData(Object object) {
        // body内的参数使用json转换
        Map<String, String> paramMap = new HashMap<>(16);
        Field[] declaredFields = object.getClass().getDeclaredFields();
        Class<?> superclass = object.getClass().getSuperclass();
        if (ObjectUtil.isNotNull(superclass)) {
            Field[] superclassDeclaredFields = superclass.getDeclaredFields();
            declaredFields = ArrayUtil.addAll(declaredFields, superclassDeclaredFields);
        }
        for (Field el : declaredFields) {
            el.setAccessible(true);
            Object value = null;
            try {
                value = el.get(object);
            } catch (IllegalAccessException e) {
                // ignored
            }
            // 把不为空的放进去
            if (ObjectUtil.isNotNull(value)) {
                if (ClassUtil.isBasicType(el.getType())) {
                    paramMap.put(el.getName(), StrUtil.toString(value));
                } else {
                    // value值使用json工具进行转换
                    paramMap.put(el.getName(), JSONUtil.toJsonStr(value));
                }
            }
        }
        return paramMap;
    }

    @Override
    public void failed(String url, WalletGeneralRspDTO walletGeneralRspDTO) {
        log.error("调用钱包API失败，url：{}，requestId：{}，msgCd：{}，msgInfo：{}",
                url,
                walletGeneralRspDTO.getRequestId(),
                walletGeneralRspDTO.getMsgCd(),
                walletGeneralRspDTO.getMsgInfo());
        ServiceException.throwServiceException(codeEscape(walletGeneralRspDTO.getMsgCd()));
    }

    public String getUrl(WalletEnum walletEnum) {
        return Optional.ofNullable(walletProperties.getUrls().get(walletEnum.name()))
                .orElseThrow(() -> new ServiceException(URL_NOT_EXISTS));
    }
}
