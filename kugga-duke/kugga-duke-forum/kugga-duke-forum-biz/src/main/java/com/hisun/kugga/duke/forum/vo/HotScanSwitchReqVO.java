package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("热贴推荐开/关 request VO")
@Data
public class HotScanSwitchReqVO {
    @ApiModelProperty(value = "贴子ID", required = true, example = "PP123124125152")
    @NotNull(message = "postsId field cannot be empty")
    private Long postsId;
}
