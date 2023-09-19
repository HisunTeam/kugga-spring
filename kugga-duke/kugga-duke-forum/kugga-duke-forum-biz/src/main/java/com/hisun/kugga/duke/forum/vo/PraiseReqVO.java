package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("点赞/取消点赞 request VO")
@Data
public class PraiseReqVO {
    @ApiModelProperty(value = "信息ID", required = true, example = "PP123124125152")
    @NotBlank(message = "msgId field cannot be empty")
    private String msgId;
}
