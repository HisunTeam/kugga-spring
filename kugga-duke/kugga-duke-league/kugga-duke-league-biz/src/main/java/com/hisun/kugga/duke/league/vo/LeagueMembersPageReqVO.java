package com.hisun.kugga.duke.league.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zuocheng
 */
@ApiModel("公会成员 Request VO")
@Data
public class LeagueMembersPageReqVO extends PageParam {
    @ApiModelProperty(value = "公会ID", required = true, example = "001")
    @NotNull(message = "公会ID不能为空")
    private Long leagueId;
}
