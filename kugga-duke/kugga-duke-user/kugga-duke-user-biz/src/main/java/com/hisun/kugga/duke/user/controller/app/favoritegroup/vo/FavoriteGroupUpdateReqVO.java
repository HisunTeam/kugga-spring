package com.hisun.kugga.duke.user.controller.app.favoritegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("收藏分组更新 Request VO")
@Data
@ToString(callSuper = true)
public class FavoriteGroupUpdateReqVO  {


    @ApiModelProperty(value = "分区列表", required = true)
    @NotEmpty(message = "groups not is empty")
    private List<FavoriteGroupVO> groups;

    @ApiModelProperty(value = "G-公会 T-推荐信 P-贴子", required = true)
    @NotNull(message = "G-公会 T-推荐信不能为空")
    private String type;

}
