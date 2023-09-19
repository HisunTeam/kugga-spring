package com.hisun.kugga.duke.bos.controller.admin.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公会详情
 *
 * @author zuocheng
 */
@ApiModel("公会标签 VO")
@Data
public class LeagueLabelVO {

    @ApiModelProperty(value = "公会标签编号", required = true, example = "1")
    private String labelValue;

    @ApiModelProperty(value = "排序编号", required = true, example = "1")
    private Integer sortId;

    @ApiModelProperty(value = "公会标签名称", required = true, example = "牛人")
    private String labelName;

}
