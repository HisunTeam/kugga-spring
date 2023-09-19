package com.hisun.kugga.duke.user.controller.vo.favorite;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 收藏更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FavoriteUpdateReqVO extends FavoriteBaseVO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id cannot be empty")
    private Long id;

}
