package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("贴子回复列表(目前只支持按楼层排序) request VO")
@Data
public class PostsFloorReqVO extends PageParam {
    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    @NotNull(message = "postsId field cannot be empty")
    private Long postsId;

    @ApiModelProperty(value = "排序规则", required = true, example = "0:创建时间 1:最新回复 2:热度")
    private String sortType;
}
