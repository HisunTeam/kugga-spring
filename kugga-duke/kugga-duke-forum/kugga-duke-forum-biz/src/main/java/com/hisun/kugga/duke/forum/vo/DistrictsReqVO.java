package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("查询分区列表 request VO")
@Data
public class DistrictsReqVO {
    @ApiModelProperty(value = "论坛ID", required = true, example = "1(公会论坛时传公会ID,匿名论坛时传0)")
    @NotNull(message = "forumId not is null")
    private Long forumId;
}
