package com.hisun.kugga.duke.forum.vo;

/**
 * @author zuocheng
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("贴子详情 request VO")
@Data
public class PostsDetailsReqVO {
    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    @NotNull(message = "postsId field cannot be empty")
    private Long postsId;
}
