package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("分页查询板块 request VO")
@Data
public class PlateReqVO {
    @ApiModelProperty(value = "板块", required = true, example = "0:公会贴 1:匿名贴")
    @NotBlank(message = "plate field cannot be empty")
    private String plate;

    @ApiModelProperty(value = "组ID，plate=0时必填(公会ID)", example = "1")
    private Long groupId;
}
