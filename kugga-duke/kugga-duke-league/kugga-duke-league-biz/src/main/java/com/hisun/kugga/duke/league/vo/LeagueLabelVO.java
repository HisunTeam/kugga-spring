package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zuocheng
 */
@ApiModel("公会标签 Response VO")
@Data
public class LeagueLabelVO {
    /**
     * 枚举ID
     */
    @ApiModelProperty(value = "标签ID", required = true, example = "1")
    private Long labelId;

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

    /**
     * 标签背图
     */
    @ApiModelProperty(value = "标签背景", example = "http://www.baidu.com/img.png")
    private String labelBackground;

    /**
     * 背景渐变样式
     */
    @ApiModelProperty(value = "标签渐变CSS", example = "我也不知道怎么举例")
    private String labelLinearGradient;

    /**
     * 标签描述
     */
    @ApiModelProperty(value = "标签描述", example = "我是描述")
    private String labelDesc;

    @ApiModelProperty(value = "隐藏标识", example = "true:隐藏 false:显示")
    private Boolean hiddenBoo;
}
