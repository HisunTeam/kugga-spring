package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("热度标签列表 response VO")
@Data
public class HotLabelsRespVO {
    @ApiModelProperty(value = "标签列表")
    private List<LabelVO> labels;
}
