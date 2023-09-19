package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("获取公会邀请连接 Response VO")
@Data
public class LeagueInviteUrlRespVO {
    @ApiModelProperty(value = "邀请连接", example = "https://www.baidu.com/s/EORJFAOEIRA")
    private String url;

    @ApiModelProperty(value = "邀请连接绑定ID", example = "1")
    private Long bindId;
}
