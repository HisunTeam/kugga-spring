package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 讨论信息
 *
 * @author zuocheng
 */
@ApiModel("讨论信息 VO")
@Data
public class CommentVO {
    @ApiModelProperty(value = "讨论ID", required = true, example = "1")
    private Long commentId;

    @ApiModelProperty(value = "信息ID", required = true, example = "COMMENT123124125152")
    private String msgId;

    @ApiModelProperty(value = "消息类型", required = true, example = "消息类型 0:回复的贴子信息 1:回复楼层信息,2:回复指定信息")
    private String msgType;

    @ApiModelProperty(value = "讨论内容", required = true, example = "评论的真好,下次别评论了")
    private String commentContent;

    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    private Long postsId;

    @ApiModelProperty(value = "创建时间", required = true, example = "1661915853000")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", required = true, example = "1661915853000")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "点赞人数", example = "0")
    private Integer praiseNum;

    @ApiModelProperty(value = "点踩人数", example = "0")
    private Integer trampleNum;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户头像", example = "/infra/file/get/35a12e57-4297-4faa-bf7d-7ed2f211c952")
    private String avatar;

    @ApiModelProperty(value = "用户名", example = "99duke****")
    private String userName;

    @ApiModelProperty(value = "昵称", example = "橘温旧茶")
    private String nickName;

    @ApiModelProperty(value = "姓", example = "LiLi")
    private String lastName;

    @ApiModelProperty(value = "名", example = "ku")
    private String firstName;

    @ApiModelProperty(value = "用户在当前公会的等级", example = "1")
    private Integer userGrowthLevel;

    @ApiModelProperty(value = "用户在当前公会的等级名称", example = "天使长")
    private String userLevelName;

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

    @ApiModelProperty(value = "楼主标识(此条回复是否为楼主回复) true, false", example = "false")
    private Boolean landlordFlag;

    @ApiModelProperty(value = "讨论归属(是否归属当前登录用户) true, false", example = "false")
    private Boolean belongToFlag;

    @ApiModelProperty(value = "当前用户对当前讨论点赞状态",  example = "true:已点,false:未点")
    private Boolean userPraiseFlag;

    @ApiModelProperty(value = "当前用户对当前讨论点踩状态",  example = "true:已点,false:未点")
    private Boolean userTrampleFlag;
}
