package com.hisun.kugga.duke.league.vo.task;

import com.hisun.kugga.duke.enums.TaskTypeEnum;
import com.hisun.kugga.duke.league.bo.task.TaskLeagueBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class TaskInitVO {
    @ApiModelProperty(value = "任务总金额")
    @NotNull(message = "任务总金额不能为空")
    private BigDecimal amount;
    @ApiModelProperty(value = "任务类型：1 推荐报告 2 公会认证 3 聊天")
    @NotNull(message = "任务类型不能为空")
    private TaskTypeEnum type;

    @ApiModelProperty(value = "写推荐报告 任务类型1传值列表")
    private TaskInit1 taskInit1;
    @ApiModelProperty(value = "公会认证 任务类型2传值列表")
    private TaskInit2 taskInit2;
    @ApiModelProperty(value = "聊天 任务类型3传值列表")
    private TaskInit3 taskInit3;

    @Data
    public static class TaskInit1 {
        @ApiModelProperty(value = "用户选择的公会列表")
        private List<TaskLeagueBO> leagueList;
    }

    @Data
    public static class TaskInit2 {
        @ApiModelProperty(value = "公会ID")
        private Long byAuthLeagueId;
        @ApiModelProperty(value = "用户选择的公会列表")
        private List<TaskLeagueBO> leagueList;
    }

    @Data
    public static class TaskInit3 {
        /*        @ApiModelProperty(value = "双方用户是否在私人聊天室 true：已存在房间 false：不存在")
                private Boolean isInPrivateRoom;
                @ApiModelProperty(value = "房间ID")
                private Long roomId;*/
        @ApiModelProperty(value = "被私聊目标 用户ID")
        private Long byUserId;
        @ApiModelProperty(value = "被私聊目标 所在公会ID")
        private Long byLeagueId;
        @ApiModelProperty(value = "是否从聊天界面发起 true是 false否")
        private Boolean isRoomPayCheck;
    }
}
