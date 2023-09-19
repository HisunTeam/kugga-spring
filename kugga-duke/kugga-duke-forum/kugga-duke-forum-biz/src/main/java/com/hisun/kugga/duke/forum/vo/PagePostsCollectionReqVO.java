package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("分页查询用户收藏贴子列表 request VO")
@Data
public class PagePostsCollectionReqVO extends PageParam {
    @ApiModelProperty(value = "收藏分组ID", example = "1")
    private Long groupId;
}
