//package com.hisun.kugga.module.duke.controller.admin.messages.vo;
//
//import lombok.*;
//import java.util.*;
//import io.swagger.annotations.*;
//import com.hisun.kugga.framework.common.pojo.PageParam;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import static com.hisun.kugga.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
//
//@ApiModel(value = "管理后台 - 消息 Excel 导出 Request VO", description = "参数和 MessagesPageReqVO 是一致的")
//@Data
//public class MessagesExportReqVO {
//
//    @ApiModelProperty(value = "链路id 邀请和回调")
//    private String linkId;
//
//    @ApiModelProperty(value = "场景 (推荐信、聊天、认证、邀请加入公会、系统通知)")
//    private String scene;
//
//    @ApiModelProperty(value = "状态(邀请、回调)")
//    private String type;
//
//    @ApiModelProperty(value = "通知对象(个人 公会)")
//    private String target;
//
//    @ApiModelProperty(value = "业务id (认证id、推荐信id..)")
//    private Long businessId;
//
//    @ApiModelProperty(value = "业务链接")
//    private String businessLink;
//
//    @ApiModelProperty(value = "发起者")
//    private Long initiatorId;
//
//    @ApiModelProperty(value = "接收者")
//    private Long receiverId;
//
//    @ApiModelProperty(value = "发起者公会id")
//    private Long initiatorLeagueId;
//
//    @ApiModelProperty(value = "接收者公会id")
//    private Long receiverLeagueId;
//
//    @ApiModelProperty(value = "发起者姓")
//    private String initiatorLastname;
//
//    @ApiModelProperty(value = "发起者名")
//    private String initiatorFirstname;
//
//    @ApiModelProperty(value = "发起者99duke*")
//    private String initiatorUsername;
//
//    @ApiModelProperty(value = "接收者姓")
//    private String receiverLastname;
//
//    @ApiModelProperty(value = "接收者名")
//    private String receiverFirstname;
//
//    @ApiModelProperty(value = "接收者99duke*")
//    private String receiverUsername;
//
//    @ApiModelProperty(value = "发起者公会名称")
//    private String initiatorLeagueName;
//
//    @ApiModelProperty(value = "发起者公会头像")
//    private String initiatorLeagueAvatar;
//
//    @ApiModelProperty(value = "接收者公会名称")
//    private String receiverLeagueName;
//
//    @ApiModelProperty(value = "接收者公会头像")
//    private String receiverLeagueAvatar;
//
//    @ApiModelProperty(value = "消息内容")
//    private String content;
//
//    @ApiModelProperty(value = "钱")
//    private BigDecimal amount;
//
//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "开始创建时间")
//    private Date beginCreateTime;
//
//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
//    @ApiModelProperty(value = "结束创建时间")
//    private Date endCreateTime;
//
//}
