package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 贴子表
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:29:57
 */
@TableName("duke_posts")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsDO {

    /**
     * 贴子id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 信息ID
     */
    private String msgId;

    /**
     * 发贴人
     */
    private Long userId;

    /**
     * 贴子所属组(目前只支持所属公会,即此id关联公会id)
     */
    private Long groupId;

    /**
     * 板块 0公会论坛、1匿名论坛
     */
    private String plate;

    /**
     * 分区ID
     */
    private Long district;

    /**
     * 贴子标题
     */
    private String postsTitle;

    /**
     * 发贴人ip
     */
    private String userIp;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 最新回复人
     */
    private Long newReplyUserId;

    /**
     * 最新回复时间
     */
    private LocalDateTime replyTime;

    /**
     * 点赞人数
     */
    private Integer praiseNum;

    /**
     * 点踩人数
     */
    private Integer trampleNum;

    /**
     * 点击次数
     */
    private Integer clickNum;

    /**
     * 收藏数
     */
    private Integer collectNum;

    /**
     * 分享数
     */
    private Integer shareNum;

    /**
     * 热度
     */
    private Integer hotNum;

    /**
     * 评论数(包含楼层与楼层中的讨论)
     */
    private Integer commentNum;

    /**
     * 楼层计数(直接回复数)
     */
    private Integer floorCount;

    /**
     * 是否允许被热贴检索  false:不允许 true:允许
     */
    private Boolean hotSearchSwitch;

    /**
     * 更新者(管理员处理贴子时存在值)
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
