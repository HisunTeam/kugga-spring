package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("回复详情(楼层信息+贴子部分信息)  Response VO")
@Data
public class MessageDetailsVO extends PostsFloorVO {
    @ApiModelProperty(value = "板块", required = true, example = "0:公会贴 其它待定")
    private String plate;

    @ApiModelProperty(value = "贴子所属组ID", example = "plate=0时,为公会ID")
    private Long groupId;
}
