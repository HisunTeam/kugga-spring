package com.hisun.kugga.duke.league.vo.joinLeague;

import com.hisun.kugga.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 加入公会审批分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LeagueJoinApprovalPageReqVO extends PageParam {

    @ApiModelProperty(value = "审批状态 0未审批、1已同意、2已拒绝")
    private String approvalType;

    private Long userId;

    @ApiModelProperty(value = "公会id")
    private Long leagueId;

}
