package com.hisun.kugga.duke.user.controller.app.favoritegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@ApiModel("管理后台 - 收藏分组 Response VO")
@Data
@ToString(callSuper = true)
public class FavoriteGroupRespVO {

    @ApiModelProperty(value = "论坛可创建分组数", example = "1(上限数不大于0时,则不可以在创建新的分组)")
    private Integer canCreateNum;

    @ApiModelProperty(value = "分组列表")
    private List<FavoriteGroupVO> groups;

    @ApiModelProperty(value = "G-公会 T-推荐信", required = true)
    private String type;
}
