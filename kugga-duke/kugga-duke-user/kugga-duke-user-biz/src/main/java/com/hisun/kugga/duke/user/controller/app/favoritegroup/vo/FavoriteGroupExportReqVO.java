package com.hisun.kugga.duke.user.controller.app.favoritegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "管理后台 - 收藏分组 Excel 导出 Request VO", description = "参数和 FavoriteGroupPageReqVO 是一致的")
@Data
public class FavoriteGroupExportReqVO {


    @ApiModelProperty(value = "G-公会 T-推荐信 P-贴子")
    @NotNull(message = "type not is null")
    private String type;

}
