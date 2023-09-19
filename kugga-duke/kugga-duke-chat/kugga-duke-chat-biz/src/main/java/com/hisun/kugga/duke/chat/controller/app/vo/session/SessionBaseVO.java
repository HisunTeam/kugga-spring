package com.hisun.kugga.duke.chat.controller.app.vo.session;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 聊天会话列 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SessionBaseVO {

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long receiveUserId;

    @ApiModelProperty(value = "会话类型", required = true)
    @NotNull(message = "会话类型不能为空")
    private Integer sessionType;

    @ApiModelProperty(value = "聊天室ID", required = true)
    @NotNull(message = "聊天室ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "未读消息数", required = true)
    @NotNull(message = "未读消息数不能为空")
    private Integer unread;

    @ApiModelProperty(value = "是否显示", required = true)
    @NotNull(message = "是否显示不能为空")
    private Boolean visible;

    @ApiModelProperty(value = "是否置顶", required = true)
    @NotNull(message = "是否置顶不能为空")
    private Boolean sticky;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "消息id")
    private Long recordId;

    @ApiModelProperty(value = "消息已读时间")
    private LocalDateTime readTime;
}
