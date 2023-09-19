package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("收藏/取消收藏 request VO")
@Data
public class PostsCollectionReqVO {
    @ApiModelProperty(value = "贴子ID", required = true, example = "1")
    @NotNull(message = "postsId field cannot be empty")
    private Long postsId;

    @ApiModelProperty(value = "分组id列表")
    private List<Long> groupIds;
}
