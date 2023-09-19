package com.hisun.kugga.duke.league.vo.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskChatVO {
    @ApiModelProperty(value = "被发起者ID")
    private Long byUserId;
    @ApiModelProperty(value = "被发起者公会ID")
    private Long byLeagueId;
}
