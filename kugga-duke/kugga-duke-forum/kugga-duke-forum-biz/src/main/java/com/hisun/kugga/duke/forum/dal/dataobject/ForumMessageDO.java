package com.hisun.kugga.duke.forum.dal.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.forum.enums.MsgTypeEnums;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 论坛消息表
 * </p>
 *
 * @author zuocheng
 * @since 2022-09-02 10:02:09
 */
@TableName("duke_forum_message")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumMessageDO {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息所属人
     */
    private Long userId;

    /**
     * 原贴id
     */
    private Long postsId;

    /**
     * 楼层id
     */
    private Long floorId;

    /**
     * 回复的消息ID
     */
    private Long replyId;

    /**
     * 回复的信息ID
     */
    private String replyMsgId;

    /**
     * 被回复人的用户ID
     */
    private Long replyUserId;

    /**
     * 被回复的消息ID
     */
    private Long receiveId;

    /**
     * 回复类型 0:回复的贴子,1:回复楼层信息,2:回复指定信息
     */
    private MsgTypeEnums receiveMsgType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 已读未读标识 true:已读 false:未读
     */
    private Boolean readFlag;

    /**
     * 删除状态 true:已删除 false:未删除
     */
    @TableLogic
    private Boolean deleted;
}
