package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("公会标签简单列表 Response VO")
@Data
public class SimpleLabelsVO {
    /**
     * 标签值
     */
    @ApiModelProperty(value = "标签值", required = true, example = "1")
    private String labelValue;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名", required = true, example = "Influencer")
    private String labelName;
}
