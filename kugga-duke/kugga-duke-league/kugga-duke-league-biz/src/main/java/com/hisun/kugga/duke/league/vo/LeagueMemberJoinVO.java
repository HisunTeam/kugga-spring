package com.hisun.kugga.duke.league.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zuocheng
 */
@ApiModel("公会成员列表 VO")
@Data
public class LeagueMemberJoinVO {
    /**
     * id
     */
    private Long id;

    /**
     * 公会ID
     */
    private Long leagueId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 成员级别:0超级管理员 1:管理员 2:成员
     */
    private Integer level;

    /**
     * 入会关系人 join_type 为0时则为邀请人,为1时则为同意人
     */
    private Long relationUserId;

    /**
     * 加入类型:0:通过邀请加入 1:自己申请加入
     */
    private Integer joinType;

    /**
     * 加入时间
     */
    private LocalDateTime joinTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 退出时间
     */
    private LocalDateTime quitTime;

    /**
     * 更新者
     */
    private String updater;

}
