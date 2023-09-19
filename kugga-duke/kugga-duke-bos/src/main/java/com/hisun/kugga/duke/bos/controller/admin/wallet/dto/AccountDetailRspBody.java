package com.hisun.kugga.duke.bos.controller.admin.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class AccountDetailRspBody {
    private String account;
    private Integer balance;
    private String createTime;
    private String internalFeeRate;
    private String remark;
}
