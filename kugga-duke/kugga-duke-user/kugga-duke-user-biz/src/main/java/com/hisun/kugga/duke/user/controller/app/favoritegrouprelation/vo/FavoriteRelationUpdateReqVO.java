package com.hisun.kugga.duke.user.controller.app.favoritegrouprelation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("收藏关联更新 Request VO")
@Data
@ToString(callSuper = true)
public class FavoriteRelationUpdateReqVO {

    @ApiModelProperty(value = "分组id", required = true)
    @NotNull(message = "分组id不能为空")
    private Long groupId;

    @ApiModelProperty(value = "原分组id", required = true)
    @NotNull(message = "原分组id不能为空")
    private Long oldGroupId;

    @ApiModelProperty(value = "G-公会 T-推荐信 P-帖子", required = true)
    @NotNull(message = "G-公会 T-推荐信 P-帖子 不能为空")
    private String type;

    @ApiModelProperty(value = "收藏id列表")
    private List<Long> favoriteIds;
}
