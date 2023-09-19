package com.hisun.kugga.duke.user.controller.vo.favorite;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 收藏 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FavoriteBaseVO {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "内容id 工会、推荐报告id..", required = true)
//    @NotNull(message = "内容id 工会、推荐报告id..不能为空")
    @NotNull(message = "Content ID cannot be empty")
    private Long contentId;

    @ApiModelProperty(value = "收藏类型 G-工会 T-推荐报告 P-贴子", required = true)
//    @NotNull(message = "收藏类型 G-工会 T-推荐报告不能为空")
    @NotNull(message = "Collection type cannot be empty")
    private String type;

}
