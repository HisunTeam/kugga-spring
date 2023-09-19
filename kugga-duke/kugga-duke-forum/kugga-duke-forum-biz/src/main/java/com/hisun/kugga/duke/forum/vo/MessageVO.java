package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 论坛消息
 *
 * @author zuocheng
 */
@ApiModel("论坛消息 VO")
@Data
public class MessageVO {
    @ApiModelProperty(value = "消息ID", required = true, example = "1")
    private Long messageId;

    @ApiModelProperty(value = "已读/未读", required = true, example = "true:已读 false:未读")
    private Boolean readFlag;

    @ApiModelProperty(value = "回复所属贴子ID", required = true, example = "1")
    private Long replyPostsId;

    @ApiModelProperty(value = "回复所属楼层ID", required = true, example = "1")
    private Long replyFloorId;

    @ApiModelProperty(value = "回复的信息的ID", required = true, example = "1")
    private Long replyId;

    @ApiModelProperty(value = "回复消息的消息ID", required = true, example = "1")
    private String replyMsgId;

    @ApiModelProperty(value = "回复的内容", required = true, example = "1")
    private String replyContent;

    @ApiModelProperty(value = "创建时间", required = true, example = "1")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "回复用户ID", example = "1")
    private Long replyUserId;

    @ApiModelProperty(value = "回复用户头像", example = "/infra/file/get/35a12e57-4297-4faa-bf7d-7ed2f211c952")
    private String replyAvatar;

    @ApiModelProperty(value = "回复用户名", example = "99duke****")
    private String replyUserName;

    @ApiModelProperty(value = "回复昵称", example = "橘温旧茶")
    private String replyNickName;

    @ApiModelProperty(value = "回复姓", example = "LiLi")
    private String replyLastName;

    @ApiModelProperty(value = "回复名", example = "ku")
    private String replyFirstName;

    @ApiModelProperty(value = "被回复的消息ID", example = "1")
    private Long receiveId;

    @ApiModelProperty(value = "被回复信息的类型", example = "1")
    private String receiveMsgType;

    @ApiModelProperty(value = "被回复的内容", example = "哈哈哈哈哈哈哈")
    private String receiveContent;

    @ApiModelProperty(value = "被回复用户ID", example = "1")
    private Long receiveUserId;

    @ApiModelProperty(value = "被回复用户头像", example = "/infra/file/get/35a12e57-4297-4faa-bf7d-7ed2f211c952")
    private String receiveAvatar;

    @ApiModelProperty(value = "被回复用户名", example = "99duke****")
    private String receiveUserName;

    @ApiModelProperty(value = "被回复昵称", example = "橘温旧茶")
    private String receiveNickName;

    @ApiModelProperty(value = "被回复姓", example = "LiLi")
    private String receiveLastName;

    @ApiModelProperty(value = "被回复名", example = "ku")
    private String receiveFirstName;

    @ApiModelProperty(value = "板块ID", example = "1")
    private String plateId;

    @ApiModelProperty(value = "板块名称贴子所属组ID(plateId = 0时(公会),此为公会名）", example = "1")
    private String plateName;

    @ApiModelProperty(value = "贴子所属组ID(plateId = 0时(公会),此值为公会ID)", example = "1")
    private Long groupId;

    @ApiModelProperty(value = "板块组头像", example = "/infra/file/get/35a12e57-4297-4faa-bf7d-7ed2f211c952")
    private String plateAvatar;
}
