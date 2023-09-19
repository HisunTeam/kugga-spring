package com.hisun.kugga.duke.pay.api.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class CreateAccountReqBody extends WalletBaseReqBody {
    /**
     * 合作方用户id	可不传
     */
    private String appUserId;
    /**
     * 备注信息
     */
    private String remark;
}
