package com.hisun.kugga.duke.chat.controller.app.vo.roommember;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 聊天室成员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RoomMemberBaseVO {

    @ApiModelProperty(value = "聊天室ID", required = true)
    @NotNull(message = "聊天室ID不能为空")
    private Long roomId;

    @ApiModelProperty(value = "用户ID", required = true)
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "室友昵称", required = true)
    @NotNull(message = "室友昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "室友角色", required = true)
    @NotNull(message = "室友角色不能为空")
    private Integer role;

}
