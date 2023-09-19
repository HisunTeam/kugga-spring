package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("模糊匹配标签列表 response VO")
@Data
public class VagueLabelsRespVO {
    @ApiModelProperty(value = "模糊匹配到的标签", required = true)
    private List<LabelVO> labels;
}
