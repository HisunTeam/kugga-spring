package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("论坛分区 VO")
@Data
public class ForumDistrictVO {
    @ApiModelProperty(value = "分区ID", required = true, example = "1")
    private Long districtId;

    @ApiModelProperty(value = "论坛分区名称", required = true, example = "找工作")
    @NotBlank(message = "districtName not is null")
    private String districtName;
}
