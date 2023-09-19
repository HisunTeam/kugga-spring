package com.hisun.kugga.framework.pay.core.client.impl.wallet;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import com.hisun.kugga.framework.common.wallet.WalletHeader;

/**
 * @author: zhou_xiong
 */
public interface Signable<T extends WalletBaseReqBody, RspBody> {
    /**
     * 是否签名
     *
     * @return
     */
    Boolean shouldSign();

    /**
     * 签名
     *
     * @param nonceStr
     * @param t
     * @return
     */
    String doSignature(String nonceStr, T t);

    /**
     * 验签
     *
     * @param rspBody
     */
    void verifySign(WalletHeader walletHeader, RspBody rspBody);
}
