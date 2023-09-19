package com.hisun.kugga.duke.league.vo.joinLeague;

import com.hisun.kugga.duke.enums.LeagueJoinApprovalTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author admin
 */
@ApiModel("加入公会审批更新 Request VO")
@Data
public class LeagueJoinApprovalUpdateReqVO {

    @ApiModelProperty(value = "审批记录id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "审批状态 1已同意、2已拒绝")
    private LeagueJoinApprovalTypeEnum approvalType;

}
