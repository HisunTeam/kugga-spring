package com.hisun.kugga.framework.common.wallet;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class WalletHeader {
    private String appid;
    private String nonceStr;
    private String sign;
    private String signType;
}
