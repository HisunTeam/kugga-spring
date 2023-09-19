package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zuocheng
 */
@ApiModel("批量取消收藏 request VO")
@Data
public class BatchCancelCollectionReqVO {
    @ApiModelProperty(value = "被取消收藏贴子ID列表")
    private List<Long> postsIds;
}
