package com.hisun.kugga.duke.system.controller.app.message.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 消息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MessagesBaseVO {

    @ApiModelProperty(value = "链路id 邀请和回调")
    private String linkId;

    @ApiModelProperty(value = "场景 (推荐信、聊天、认证、邀请加入公会、系统通知)")
    private String scene;

    @ApiModelProperty(value = "状态(邀请、回调)")
    private String type;

    @ApiModelProperty(value = "通知对象(个人 公会)")
    private String target;

    @ApiModelProperty(value = "业务id (认证id、推荐信id..)")
    private Long businessId;

    @ApiModelProperty(value = "业务链接")
    private String businessLink;

    @ApiModelProperty(value = "发起者")
    private Long initiatorId;

    @ApiModelProperty(value = "接收者")
    private Long receiverId;

    @ApiModelProperty(value = "发起者公会id")
    private Long initiatorLeagueId;

    @ApiModelProperty(value = "接收者公会id")
    private Long receiverLeagueId;

    @ApiModelProperty(value = "发起者姓")
    private String initiatorLastname;

    @ApiModelProperty(value = "发起者名")
    private String initiatorFirstname;

    @ApiModelProperty(value = "发起者99duke*")
    private String initiatorUsername;

    @ApiModelProperty(value = "接收者姓")
    private String receiverLastname;

    @ApiModelProperty(value = "接收者名")
    private String receiverFirstname;

    @ApiModelProperty(value = "接收者99duke*")
    private String receiverUsername;

    @ApiModelProperty(value = "发起者公会名称")
    private String initiatorLeagueName;

    @ApiModelProperty(value = "发起者公会头像")
    private String initiatorLeagueAvatar;

    @ApiModelProperty(value = "接收者公会名称")
    private String receiverLeagueName;

    @ApiModelProperty(value = "接收者公会头像")
    private String receiverLeagueAvatar;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "钱")
    private BigDecimal amount;

}
