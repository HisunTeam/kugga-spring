package com.hisun.kugga.duke.pay.api.wallet.dto;

import lombok.Data;

/**
 * @author: zhou_xiong
 */
@Data
public class CreateAccountRspBody {
    private String account;
    private String appid;
    private Integer balance;
    private String createTime;
    private String remark;
}
