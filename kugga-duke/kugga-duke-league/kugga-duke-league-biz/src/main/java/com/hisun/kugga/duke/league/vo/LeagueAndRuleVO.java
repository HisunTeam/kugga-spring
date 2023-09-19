package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zuocheng
 */
@ApiModel("公会与公会规则 VO")
@Data
public class LeagueAndRuleVO {
    @ApiModelProperty(value = "公会编号", required = true, example = "001")
    private Long leagueId;

    @ApiModelProperty(value = "公会名称", required = true, example = "时躺平一时爽，一直躺平一直爽")
    private String leagueName;

    @ApiModelProperty(value = "公会头像", example = "/img/tp.png")
    private String leagueAvatar;

    @ApiModelProperty(value = "公会描述", required = true, example = "躺平身体健康,卷王谁都不好过")
    private String leagueDesc;

    @ApiModelProperty(value = "公会激活标识", example = "true:已激活,false:未激活")
    private Boolean leagueActivationFlag;

    @ApiModelProperty(value = "公会是否已被认证", example = "true:已认证,false:未认证")
    private Boolean leagueAuthFlag;

    @ApiModelProperty(value = "是否开通认证", example = "true:是 false:否")
    private Boolean enabledAuth;

    @ApiModelProperty(value = "认证价格", example = "100.00")
    private BigDecimal authPrice;

    @ApiModelProperty(value = "写推荐报告价格", example = "100.00")
    private BigDecimal reportPrice;

    @ApiModelProperty(value = "聊天价格", example = "100.00")
    private BigDecimal chatPrice;

    @ApiModelProperty(value = "是否允许用户加入", example = "true:是 false:否")
    private Boolean enabledUserJoin;
}
