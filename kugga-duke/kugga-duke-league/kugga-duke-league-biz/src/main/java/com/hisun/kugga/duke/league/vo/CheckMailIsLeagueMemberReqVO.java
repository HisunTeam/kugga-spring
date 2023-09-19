package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("检查邮箱对应的用户是否为公会成员 Request VO")
@Data
public class CheckMailIsLeagueMemberReqVO {
    @ApiModelProperty(value = "邮箱", required = true, example = "mail@mail.com")
    @NotBlank(message = "邮箱号不能为空")
    private String mail;

    @ApiModelProperty(value = "公会ID", required = true, example = "001")
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;
}
