package com.hisun.kugga.duke.league.vo.joinLeague;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 加入公会审批 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class LeagueJoinApprovalBaseVO {

    @ApiModelProperty(value = "公会加入表id")
    private Long businessId;

    @ApiModelProperty(value = "审批状态 0未审批、1已同意、2已拒绝、3已过期")
    private String status;

}
