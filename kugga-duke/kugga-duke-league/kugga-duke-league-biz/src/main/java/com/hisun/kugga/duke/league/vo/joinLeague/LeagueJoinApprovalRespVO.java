package com.hisun.kugga.duke.league.vo.joinLeague;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("加入公会审批 Response VO")
@Data
@ToString(callSuper = true)
public class LeagueJoinApprovalRespVO {

    @ApiModelProperty(value = "加入公会记录id")
    private Long id;

    @ApiModelProperty(value = "审批记录id")
    private Long approvalId;

    private Long userId;

    private Long leagueId;

    private Integer approvalStatus;

    private LocalDateTime createTime;
    /**
     * 申请理由
     */
    private String joinReason;

    private String firstName;

    private String lastName;

    private String leagueName;

    private String leagueAvatar;

    /**
     * 用户头像
     */
    private String avatar;

    /*
    select approvalId,userId,leagueId,approvalStatus,createTime,
               u.first_name firstName,
               u.last_name lastName,
               l.league_name leagueName
     */

}
