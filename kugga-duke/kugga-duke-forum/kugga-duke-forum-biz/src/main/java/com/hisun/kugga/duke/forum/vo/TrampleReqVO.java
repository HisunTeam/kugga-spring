package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("点踩/取消点踩 request VO")
@Data
public class TrampleReqVO {
    @ApiModelProperty(value = "信息ID", required = true, example = "PP123124125152")
    @NotBlank(message = "msgId field cannot be empty")
    private String msgId;
}
