package com.hisun.kugga.duke.pay.controller.app.leaguebill.vo;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 公会账单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LeagueBillPageReqVO extends PageParam {

    @ApiModelProperty(value = "公会ID")
    @NotNull(message = "公会编号不能为空")
    private Long leagueId;

}
