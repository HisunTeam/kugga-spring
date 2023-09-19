package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 论坛板块表
 * </p>
 *
 * @author zuocheng
 * @since 2022-09-02 10:02:09
 */
@TableName("duke_forum_plate")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumPlateDO {

    /**
     * 板块id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 板块类型
     */
    private String plateValue;

    /**
     * 语言
     */
    private String language;

    /**
     * 板块名称
     */
    private String plateName;

    /**
     * 板块简介
     */
    private String plateDesc;

    /**
     * 板块头像
     */
    private String plateAvatar;

    /**
     * 匿名标识 true: 是 false: 否
     */
    private Boolean anonFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    /**
     * 备注信息
     */
    private String rmk;

    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
