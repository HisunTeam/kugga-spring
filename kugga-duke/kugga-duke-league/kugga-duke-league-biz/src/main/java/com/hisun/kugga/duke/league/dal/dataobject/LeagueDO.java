package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 公会 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDO {

    /**
     * 公会ID
     */
    @TableId(value = "id", type = IdType.INPUT)
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
    @TableLogic
    private Boolean deleted;
}
