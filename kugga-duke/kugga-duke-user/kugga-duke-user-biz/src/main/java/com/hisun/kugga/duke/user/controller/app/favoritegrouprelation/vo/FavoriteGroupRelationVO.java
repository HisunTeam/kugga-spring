package com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 收藏分组关联 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class FavoriteGroupRelationVO {

    @ApiModelProperty(value = "分组id")
    private Long groupId;

    @ApiModelProperty(value = "收藏id")
    private Long favoriteId;

}
