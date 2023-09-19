package com.hisun.kugga.duke.forum.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("论坛简单板块 VO")
@Data
public class SimplePlateVO {
    /**
     * 板块名称
     */
    @ApiModelProperty(value = "板块名称", required = true, example = "java")
    private String plateName;

    /**
     * 板块头像
     */
    @ApiModelProperty(value = "板块头像", example = "https://www.baidu.com/img01.png")
    private String plateAvatar;

    /**
     * 访问权限 true: 是 false: 否
     */
    @ApiModelProperty(value = "访问权限", example = "true: 是 false: 否")
    private Boolean visitAuth;

    @ApiModelProperty(value = "申请加入价格", example = "100")
    private BigDecimal userJoinPrice;

    @ApiModelProperty(value = "加入时是否需要管理员审批", example = "true or false")
    private Boolean enabledAdminApproval;

    @ApiModelProperty(value = "是否允许用户主动加入公会", example = "true or false")
    private Boolean enabledUserJoin;

    @ApiModelProperty(value = "用户是否为管理员", example = "true or false")
    private Boolean adminFlag;
}
