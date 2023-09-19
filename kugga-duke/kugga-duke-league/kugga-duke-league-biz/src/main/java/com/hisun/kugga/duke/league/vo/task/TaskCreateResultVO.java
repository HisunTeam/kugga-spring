package com.hisun.kugga.duke.league.vo.task;

import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskCreateResultVO {

    @ApiModelProperty(value = "公会认证 任务类型2")
    private leagueAuth leagueAuth;
    @ApiModelProperty(value = "聊天 任务类型3")
    private chat chat;

    @Data
    public static class leagueAuth {
    }

    @Data
    public static class chat {
        @ApiModelProperty(value = "付费状态 1免费 2付费")
        private PayTypeEnum payType;
    }
}
