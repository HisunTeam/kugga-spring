package com.hisun.kugga.duke.league.vo.joinLeague;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/9/13 17:18
 */
@Data
public class LeagueJoinPayReqVO {

    @ApiModelProperty(value = "内部订单号")
    private String appOrderNo;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @ApiModelProperty(value = "uuid")
    private String uuid;
}
