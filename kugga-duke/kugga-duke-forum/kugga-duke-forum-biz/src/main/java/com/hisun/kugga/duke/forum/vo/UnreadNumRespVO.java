package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("未读消息条数 response VO")
@Data
public class UnreadNumRespVO {
    @ApiModelProperty(value = "未读条条数", required = true, example = "1")
    private Long num;
}
