package com.hisun.kugga.duke.forum.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("标签贴子列表 request VO")
@Data
public class LabelPostsReqVO  extends PageParam {
    @ApiModelProperty(value = "标签ID", required = true, example = "1")
    @NotNull(message = "labelId field cannot be empty")
    private Long labelId;
}
