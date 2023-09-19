package com.hisun.kugga.duke.pay.controller.app.testAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试类 生产需要del
 */
@Data
public class AccountTestVO {

    @ApiModelProperty(value = "用户名 99duke*")
    private String userId;

    @ApiModelProperty(value = "用户昵称", required = true)
    private String leagueId;

    private String flag;

}
