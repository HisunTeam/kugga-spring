package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zuocheng
 */
@ApiModel("论坛板块 VO")
@Data
public class PlateVO {
    @ApiModelProperty(value = "板块ID", required = true, example = "1")
    private Long id;

    /**
     * 板块类型
     */
    @ApiModelProperty(value = "板块值", required = true, example = "common:普通")
    private String plateValue;

    /**
     * 板块名称
     */
    @ApiModelProperty(value = "板块名称", required = true, example = "java")
    private String plateName;

    /**
     * 板块简介
     */
    @ApiModelProperty(value = "板块描述", example = "一次编写，处处可运行")
    private String plateDesc;

    /**
     * 板块头像
     */
    @ApiModelProperty(value = "板块头像", example = "https://www.baidu.com/img01.png")
    private String plateAvatar;

    /**
     * 匿名标识 true: 是 false: 否
     */
    @ApiModelProperty(value = "匿名标识", example = "true: 是 false: 否")
    private Boolean anonFlag;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "1661915853000")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "更新时间", example = "1661915853000")
    private LocalDateTime updateTime;
}
