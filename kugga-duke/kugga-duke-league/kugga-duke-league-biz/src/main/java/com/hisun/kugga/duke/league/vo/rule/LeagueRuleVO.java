package com.hisun.kugga.duke.league.vo.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-22 15:37
 */
@Data
public class LeagueRuleVO {
    @ApiModelProperty(value = "公会ID", required = true)
    private Long leagueId;

    @ApiModelProperty(value = "公会名称", required = true)
    private String leagueName;
    @ApiModelProperty(value = "公会描述", required = true)
    private String leagueDesc;

    @ApiModelProperty(value = "公会是否已被认证", required = true)
    private Boolean leagueAuthFlag;

    @ApiModelProperty(value = "是否开通认证")
    private Boolean enabledAuth;
    @ApiModelProperty(value = "认证价格")
    private BigDecimal authPrice;
    @ApiModelProperty(value = "写推荐报告价格")
    private BigDecimal reportPrice;
    @ApiModelProperty(value = "聊天价格")
    private BigDecimal chatPrice;
    @ApiModelProperty(value = "是否允许用户加入")
    private Boolean enabledUserJoin;
    @ApiModelProperty(value = "是否需要管理员审批")
    private Boolean enabledAdminApproval;
    @ApiModelProperty(value = "工会头像")
    private String leagueAvatar;
    @ApiModelProperty(value = "是否允许热贴检索开关", example = "false:不允许 true:允许")
    private Boolean postsSearchSwitch;

    @ApiModelProperty(value = "用户加入价格 ")
    private BigDecimal userJoinPrice;
    /**
     * 按月订阅价格
     */
    @ApiModelProperty(value = "订阅生效选择 1_0_0_0")
    private String subscribeSelect;
    @ApiModelProperty(value = "按月订阅价格 ")
    private BigDecimal subscribeMonthPrice;
    @ApiModelProperty(value = "按季度订阅价格 ")
    private BigDecimal subscribeQuarterPrice;
    @ApiModelProperty(value = "按年订阅价格 ")
    private BigDecimal subscribeYearPrice;
    @ApiModelProperty(value = "永久订阅价格 ")
    private BigDecimal subscribeForeverPrice;
}
