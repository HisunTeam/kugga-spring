package com.hisun.kugga.duke.chat.controller.app.vo.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 聊天室 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RoomBaseVO {

    @ApiModelProperty(value = "聊天室名字")
    private String name;

    @ApiModelProperty(value = "聊天室描述")
    private String description;

    @ApiModelProperty(value = "聊天室头像URL")
    private String avatar;

    @ApiModelProperty(value = "聊天室的类型", required = true)
    @NotNull(message = "聊天室的类型不能为空")
    private Integer roomType;

    @ApiModelProperty(value = "聊天室支付类型 工会内部免费聊天:0,工会外部付费聊天:1")
    private Integer payType;

    @ApiModelProperty(value = "聊天室最大人数", required = true)
    @NotNull(message = "聊天室最大人数不能为空")
    private Integer peopleLimit;

    @ApiModelProperty(value = "工会ID", required = true)
    @NotNull(message = "工会ID")
    private Long leagueId;

    /**
     * 支付状态
     */
    @ApiModelProperty(value = "聊天支付状态")
    private String payChatStatus;

    /**
     * 聊天发起方的用户ID
     */
    private Long inviterUserId;
}
