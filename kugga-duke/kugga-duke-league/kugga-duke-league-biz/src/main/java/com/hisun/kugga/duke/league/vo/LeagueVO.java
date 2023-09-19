package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zuocheng
 */
@ApiModel("公会 VO")
@Data
public class LeagueVO {
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

    @ApiModelProperty(value = "公会总人数", example = "100")
    private Integer peopleNumber;
}
