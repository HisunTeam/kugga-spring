package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签
 *
 * @author zuocheng
 */
@ApiModel("标签 VO")
@Data
public class LabelVO {
    @ApiModelProperty(value = "标签ID", example = "1")
    private Long labelId;

    @ApiModelProperty(value = "标签名称", example = "原神-娜希达")
    private String labelName;

    @ApiModelProperty(value = "标签热度", example = "100")
    private Long hotNum;
}
