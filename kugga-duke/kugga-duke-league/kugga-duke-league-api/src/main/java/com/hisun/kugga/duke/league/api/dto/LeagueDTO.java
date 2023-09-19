package com.hisun.kugga.duke.league.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeagueDTO {
    /**
     * 公会ID
     */
    private Long id;

    /**
     * 公会名称
     */
    private String leagueName;

    /**
     * 公会描述
     */
    private String leagueDesc;

    /**
     * 公会标签
     */
    private String leagueLabel;

    /**
     * 公会头像url
     */
    private String leagueAvatar;

    /**
     * 是否已认证 true:已认证 false:未认证
     */
    private Boolean authFlag;

    /**
     * 公会创建者
     */
    private Long createUserId;

    /**
     * 排序编号(用于自定义排序规则)
     */
    private Integer sortId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 公会状态 valid:生效 其它状态待定
     */
    private String status;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 删除状态 true:已删除 false:未删除
     */
    private Boolean deleted;
}
