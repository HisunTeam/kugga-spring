package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("回复 request VO")
@Data
public class ReplyReqVO {
    @ApiModelProperty(value = "信息ID", required = true, example = "POSTS33284029840131")
    @NotBlank(message = "msgId field cannot be empty")
    private String msgId;

    @ApiModelProperty(value = "回复内容", required = true, example = "贴子写的真好")
    @NotBlank(message = "content field cannot be empty")
    private String content;
}
