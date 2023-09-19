package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 贴子讨论表
 * </p>
 *
 * @author zuocheng
 * @since 2022-08-29 09:35:00
 */
@TableName("duke_posts_comment")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsCommentDO {

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 信息ID
     */
    private String msgId;

    /**
     * 讨论类型 1:回复楼层信息,2:回复的楼层讨论
     */
    private String msgType;

    /**
     * 贴子ID 与表duke_posts id匹配
     */
    private Long postsId;

    /**
     * 楼层ID 与duke_posts_floor id匹配
     */
    private Long floorId;

    /**
     * 被回复信息的ID(comment_type为0时 存duke_posts_floor表的id,为1时存duke_posts_comment的id)
     */
    private Long receiveId;

    /**
     * 评论者id
     */
    private Long userId;

    /**
     * 是否为楼主回复 true:是 false:否
     */
    private Boolean landlordFlag;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人ip
     */
    private String userIp;

    /**
     * 被评论人ID
     */
    private Long receiveUserId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 点赞人数
     */
    private Integer praiseNum;

    /**
     * 点踩人数
     */
    private Integer trampleNum;

    /**
     * 备注信息
     */
    private String rmk;

    /**
     * 更新者(管理员处理贴子时存在值)
     */
    private String updater;

    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
