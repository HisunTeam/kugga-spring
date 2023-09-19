package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("论坛分区列表 response VO")
@Data
public class DistrictsRespVO {
    @ApiModelProperty(value = "论坛可创建分区数", example = "1(上限数不大于0时,则不可以在创建新的分区)")
    private Integer canCreateNum;

    @ApiModelProperty(value = "分区列表")
    private List<ForumDistrictVO> districts;
}
