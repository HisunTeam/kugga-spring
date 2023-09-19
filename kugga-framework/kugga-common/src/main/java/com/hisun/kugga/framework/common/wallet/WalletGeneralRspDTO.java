package com.hisun.kugga.framework.common.wallet;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class WalletGeneralRspDTO<T> {
    private String requestId;
    private String startDateTime;
    private String endDateTime;
    private T body;
    private String msgCd;
    private String msgInfo;
    private String msgType;
}
