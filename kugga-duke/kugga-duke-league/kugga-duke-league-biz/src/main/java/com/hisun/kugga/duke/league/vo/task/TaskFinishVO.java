package com.hisun.kugga.duke.league.vo.task;

import com.hisun.kugga.duke.enums.TaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskFinishVO {

    @ApiModelProperty(value = "任务表ID", required = true, example = "1024")
    @NotNull(message = "任务ID不能为空")
    private Long id;
    @ApiModelProperty(value = "任务类型 （不能传'TASK_TYPE_1'）", required = true)
    @NotNull(message = "任务类型不能为空")
    private TaskTypeEnum type;

    @ApiModelProperty(value = "公会公告栏ID", required = true, example = "1024")
    @NotNull(message = "公会公告栏ID不能为空")
    private Long leagueNoticeId;

    @ApiModelProperty(value = "任务类型1传值列表 写推荐报告")
    private TaskFinish1 type1;
    @ApiModelProperty(value = "任务类型1传值列表 公会认证")
    private TaskFinish2 type2;
    @ApiModelProperty(value = "任务类型1传值列表 聊天")
    private TaskFinish3 type3;

    @Data
    public static class TaskFinish1 {
    }

    @Data
    public static class TaskFinish2 {
    }

    @Data
    public static class TaskFinish3 {
        @ApiModelProperty(value = "是否同意聊天 true同意聊天 false拒绝")
        private Boolean agreeOrNo;
    }
}
