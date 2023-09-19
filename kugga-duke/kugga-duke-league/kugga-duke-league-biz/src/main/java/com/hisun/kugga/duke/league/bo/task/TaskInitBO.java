package com.hisun.kugga.duke.league.bo.task;

import com.hisun.kugga.duke.enums.LeagueNoticeTypeEnum;
import com.hisun.kugga.duke.enums.TaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskInitBO {
    @ApiModelProperty(value = "该字段请勿传值 任务表ID")
    private Long id;
    @ApiModelProperty(value = "任务类型")
    private TaskTypeEnum type;
    @ApiModelProperty(value = "任务总金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "公会公告栏类型")
    private String orderRecord;

    @ApiModelProperty(value = "公会公告栏类型")
    private LeagueNoticeTypeEnum LeagueNoticeType;

    @ApiModelProperty(value = "任务类型1传值列表 写推荐报告")
    private TaskInit1 taskInit1;
    @ApiModelProperty(value = "任务类型2传值列表 公会认证")
    private TaskInit2 taskInit2;
    @ApiModelProperty(value = "任务类型3传值列表 聊天")
    private TaskInit3 taskInit3;

    @Data
    public static class TaskInit1 {
        @ApiModelProperty(value = "用户选择的公会列表")
        private List<TaskLeagueBO> leagueList;
    }

    @Data
    public static class TaskInit2 {
        @ApiModelProperty(value = "被认证公会ID")
        private Long byAuthLeagueId;
        @ApiModelProperty(value = "用户选择的公会列表")
        private List<TaskLeagueBO> leagueList;
    }

    @Data
    public static class TaskInit3 {
        @ApiModelProperty(value = "被私聊目标 用户ID")
        private Long byUserId;
        @ApiModelProperty(value = "被私聊目标 所在公会ID")
        private Long byLeagueId;
        @ApiModelProperty(value = "是否从聊天界面发起 true是 false否")
        private Boolean isRoomPayCheck;
    }
}
