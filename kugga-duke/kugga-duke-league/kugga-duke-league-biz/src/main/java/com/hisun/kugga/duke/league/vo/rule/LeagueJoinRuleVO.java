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
public class LeagueJoinRuleVO {
    @ApiModelProperty(value = "公会ID", required = true)
    private Long leagueId;

    @ApiModelProperty(value = "是否允许用户加入")
    private Boolean enabledUserJoin;
    @ApiModelProperty(value = "是否需要管理员审批")
    private Boolean enabledAdminApproval;

    @ApiModelProperty(value = "用户加入价格 ")
    private BigDecimal userJoinPrice;

    @ApiModelProperty(value = "订阅生效选择 1_0_0_0")
    private String subscribeSelect;

    @ApiModelProperty(value = "订阅生效选择 1生效 0失效")
    private SubscribeSelectVo subscribeSelectVo;
    /**
     * 按月订阅价格
     */
    private BigDecimal subscribeMonthPrice;
    /**
     * 按季度订阅价格
     */
    private BigDecimal subscribeQuarterPrice;
    /**
     * 按年订阅价格
     */
    private BigDecimal subscribeYearPrice;
    /**
     * 永久订阅价格
     */
    private BigDecimal subscribeForeverPrice;


}
