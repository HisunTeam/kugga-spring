package com.hisun.kugga.duke.league.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 公会标签配枚举配置表 DO
 *
 * @author 芋道源码
 */
@TableName("duke_league_label")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueLabelDO {
    /**
     * 枚举ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 排序编号(用于自定义排序规则)
     */
    private Integer sortId;

    /**
     * 显示标识 true:显示 false:隐藏
     */
    private Boolean displayFlag;

    /**
     * 标签值
     */
    private String labelValue;

    /**
     * 语言
     */
    private String language;

    /**
     * 标签名
     */
    private String labelName;

    /**
     * 标签背图
     */
    private String labelBackground;

    /**
     * 背景渐变样式
     */
    private String labelLinearGradient;

    /**
     * 标签描述
     */
    private String labelDesc;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updater;

    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
