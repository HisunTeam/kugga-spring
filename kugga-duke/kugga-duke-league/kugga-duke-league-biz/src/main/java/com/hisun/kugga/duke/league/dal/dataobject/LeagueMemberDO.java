package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 公会成员 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_member")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueMemberDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 是否已退会(是否已删除): false:未退会 true:已退会
     */
    @TableLogic
    private Boolean deleted;

}
