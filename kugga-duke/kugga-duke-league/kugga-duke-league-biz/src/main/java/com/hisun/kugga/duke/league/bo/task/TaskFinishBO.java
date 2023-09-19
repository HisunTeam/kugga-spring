package com.hisun.kugga.duke.league.bo.task;

import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskFinishBO {

    @ApiModelProperty(value = "任务表ID")
    private Long id;
    @ApiModelProperty(value = "任务类型")
    private TaskTypeEnum type;

    @ApiModelProperty(value = "公会公告栏ID")
    private Long leagueNoticeId;
    @ApiModelProperty(value = "公告栏类型")
    private LeagueNoticeTypeEnum leagueNoticeType;

    @ApiModelProperty(value = "任务类型1传值列表 写推荐报告")
    private TaskFinish1 type1;
    @ApiModelProperty(value = "任务类型1传值列表 公会认证")
    private TaskFinish2 type2;
    @ApiModelProperty(value = "任务类型1传值列表 聊天")
    private TaskFinish3 type3;

    @Data
    public class TaskFinish1 {
    }

    @Data
    public class TaskFinish2 {
        @ApiModelProperty(value = "公会ID")
        private Long leagueId;
    }

    @Data
    public class TaskFinish3 {
        @ApiModelProperty(value = "是否同意聊天 true同意聊天 false拒绝")
        private Boolean agreeOrNo;
    }
}
