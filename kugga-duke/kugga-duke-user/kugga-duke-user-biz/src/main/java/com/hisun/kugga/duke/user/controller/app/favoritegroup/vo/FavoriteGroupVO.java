package com.hisun.kugga.duke.user.controller.app.favoritegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 收藏分组更新 Request VO")
@Data
@ToString(callSuper = true)
public class FavoriteGroupVO   {
        @ApiModelProperty(value = "分组id", required = true)
        private Long id;

        @ApiModelProperty(value = "分区名称", required = true)
        @NotNull(message = "分区名称不能为空")
        private String groupName;

}
