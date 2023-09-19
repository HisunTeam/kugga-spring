package com.hisun.kugga.duke.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description: 参数信息
 * @author： Lin
 * @Date 2022/8/1 19:10
 */
@Data
public class ContentParamVo {
    /**
     * 发起人id
     */
    private Long initiatorId;
    /**
     * 发起人公会ID
     */
    private Long initiatorLeagueId;
    /**
     * 接收人id
     */
    private Long receiverId;
    /**
     * 接收人公会ID
     */
    private Long receiverLeagueId;
    /**
     * 非下面内部类里的金钱使用
     */
    private BigDecimal amount;

    @ApiModelProperty(value = "任务 公会认证 创建任")
    private TaskLeagueAuthCreate taskLeagueAuthCreate;
    @ApiModelProperty(value = "任务 公会认证 完成")
    private TaskLeagueAuthFinish taskLeagueAuthFinish;
    @ApiModelProperty(value = "任务 推荐报告")
    private TaskWriteReportCreate taskWriteReportCreate;
    @ApiModelProperty(value = "任务 聊天")
    private TaskChat taskChat;
    @ApiModelProperty(value = "加入公会")
    private JoinLeagueMessageVO joinLeagueMessageVO;
    @ApiModelProperty(value = "系统通知")
    private SystemNoticeMessageVO systemNoticeMessageVO;
    @ApiModelProperty(value = "邀请加入公会")
    private InviteJoinLeagueVO inviteJoinLeagueVO;
    @ApiModelProperty(value = "公会订阅vo")
    private LeagueSubscribeVO leagueSubscribeVO;

    @Data
    public static class TaskWriteReportCreate {
        @ApiModelProperty(value = "任务表ID", example = "1024", required = true)
        private Long id;
        @ApiModelProperty(value = "任务类型", required = true, example = "1")
        private Integer type;
        @ApiModelProperty(value = "公会公告栏ID", required = true, example = "1024")
        private Long leagueNoticeId;

        @ApiModelProperty(value = "公会ID", required = true, example = "1024")
        private Long byLeagueId;
        @ApiModelProperty(value = "用户 发起者ID")
        private Long useUserId;

        /**
         * 111.11  0.5 55.555   55.55
         * 0.3 33.333   33.33
         * 0.2 22.222   22.23
         * 精度问题都先算出个人和公会的，平台金额=总金额-其两个相加
         * 111.1  0.5 55.55
         * 0.3 33.33
         * 0.2 22.22
         * 推荐信、聊天按用户付款时的5:3:2比例分成，5成到达另一方用户
         * 公会认证 此时另一方用户单纯的点击，没有收益
         */
        @ApiModelProperty(value = "金额", required = false, example = "1")
        private BigDecimal amount;
    }

    @Data
    @Builder
    public static class TaskLeagueAuthCreate {
        @ApiModelProperty(value = "任务表ID")
        private Long id;
        @ApiModelProperty(value = "任务类型")
        private Integer type;
        @ApiModelProperty(value = "用户 发起者ID")
        private Long useUserId;
        @ApiModelProperty(value = "公会ID")
        private Long byAuthLeagueId;
        @ApiModelProperty(value = "公会公告栏ID")
        private Long leagueNoticeId;
        @ApiModelProperty(value = "金额", example = "1")
        private BigDecimal amount;
    }

    @Data
    @Builder
    public static class TaskLeagueAuthFinish {
        @ApiModelProperty(value = "公会ID")
        private Long leagueId;
    }

    @Data
    public static class TaskChat {
        @ApiModelProperty(value = "任务表ID")
        private Long id;
        @ApiModelProperty(value = "任务类型")
        private Integer type;
        @ApiModelProperty(value = "公会公告栏ID")
        private Long leagueNoticeId;
        @ApiModelProperty(value = "公会ID")
        private Long leagueId;

        @ApiModelProperty(value = "用户 发起者ID")
        private Long useUserId;

        /**
         * 推荐信、聊天按用户付款时的5:3:2比例分成，5成到达另一方用户
         */
        @ApiModelProperty(value = "金额", required = true, example = "1")
        private BigDecimal amount;
    }

    @Data
    public static class JoinLeagueMessageVO {
        @ApiModelProperty(value = "加入公会人的用户ID", example = "1", required = true)
        private Long joinUserId;
        @ApiModelProperty(value = "公会ID", required = true, example = "1")
        private Long leagueId;
        //第一个加入公会的金额
        // 第一次加入公会  您是第一个受邀加入公会后端开发工程师的会员，并获得10美元的奖金。
        @ApiModelProperty(value = "扣款金额", required = false, example = "1")
        private BigDecimal amount;
    }

    @Data
    public static class SystemNoticeMessageVO {
        @ApiModelProperty(value = "开始实际", required = true)
        private String startTime;
        @ApiModelProperty(value = "结束实际", required = true)
        private String endTime;
    }

    /**
     * 邀请加入公会信息
     */
    @Data
    public static class InviteJoinLeagueVO {
        @ApiModelProperty(value = "公会ID", required = true)
        private Long leagueId;
        @ApiModelProperty(value = "公会名称", required = true)
        private String leagueName;
        @ApiModelProperty(value = "邀请链接", required = true)
        private String inviteUrl;
    }

    /**
     * 公会订阅vo
     */
    @Data
    public static class LeagueSubscribeVO {
        @ApiModelProperty(value = "过期时间", required = true, example = "1")
        private LocalDateTime expireTime;
        @ApiModelProperty(value = "扣款金额", required = false, example = "1")
        private BigDecimal amount;
    }
}
