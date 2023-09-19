package com.hisun.kugga.framework.common.wallet;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class WalletGeneralReqDTO<T extends WalletBaseReqBody> {
    private T body;
}
