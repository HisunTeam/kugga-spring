package com.hisun.kugga.duke.chat.controller.app.vo.session;

import com.hisun.kugga.duke.chat.dal.dataobject.enums.PayChatStatusEnum;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("管理后台 - 聊天会话列 Response VO")
@Data
public class SessionRespVO extends SessionBaseVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "消息数据")
    private Object data;

    @ApiModelProperty(value = "名", example = "乔布斯")
    private String firstName;

    @ApiModelProperty(value = "姓", example = "史蒂夫")
    private String lastName;

    @ApiModelProperty(value = "聊天头像")
    private String avatar;

    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    @ApiModelProperty(value = "是否过期")
    private Boolean isExpire;

    /**
     * @see PayTypeEnum
     * 聊天室支付类型 工会内部免费聊天:0,工会外部付费聊天:1
     */
    @ApiModelProperty(value = "聊天室支付类型 工会内部免费聊天:0,工会外部付费聊天:1")
    private PayTypeEnum payType;

    @ApiModelProperty(value = "工会ID", required = true)
    private Long leagueId;

    @ApiModelProperty(value = "消息类型")
    private Integer messageType;

    /**
     * 支付状态
     *
     * @see PayChatStatusEnum
     */
    @ApiModelProperty(value = "支付状态。支付完成待确认聊天：TO_BE_CONFIRMED，确认聊天：CONFIRMED，聊天过期：EXPIRE")
    private String payChatStatus;

    @ApiModelProperty(value = "聊天发起方用户Id")
    private Long inviterUserId;

}
