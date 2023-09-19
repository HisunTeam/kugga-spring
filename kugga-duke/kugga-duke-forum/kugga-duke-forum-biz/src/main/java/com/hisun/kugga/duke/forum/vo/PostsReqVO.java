package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zuocheng
 */
@ApiModel("查询贴子列表 request VO")
@Data
public class PostsReqVO extends PageParam {
    @ApiModelProperty(value = "板块", required = true, example = "0:公会贴 1:匿名贴")
    @NotBlank(message = "plate field cannot be empty")
    private String plate;

    @ApiModelProperty(value = "组ID，plate=0时必填(公会ID)", example = "1")
    private Long groupId;

    @ApiModelProperty(value = "排序规则", required = true, example = "0:创建时间 1:最新回复 2:热度")
    @NotBlank(message = "sortType field cannot be empty")
    private String sortType;

    @ApiModelProperty(value = "分区ID", required = true, example = "1")
    private Long districtId;
}
