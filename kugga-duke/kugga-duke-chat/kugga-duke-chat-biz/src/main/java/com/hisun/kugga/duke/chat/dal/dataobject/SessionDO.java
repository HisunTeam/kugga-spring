package com.hisun.kugga.duke.chat.dal.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天会话列 DO
 *
 * @author toi
 */
@TableName("chat_session")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 被接受消息者用户id
     */
    private Long receiveUserId;
    /**
     * @see com.hisun.kugga.duke.chat.dal.dataobject.enums.SessionTypeEnum
     * <p>
     * 会话类型。聊天室会话：0，聊天室通知会话：1
     */
    private Integer sessionType;
    /**
     * 聊天室ID
     */
    private Long roomId;

    /**
     * @see com.hisun.kugga.duke.chat.dal.dataobject.enums.RoomTypeEnum
     * 聊天室的类型 1: 群聊，0：私聊，2：单聊
     */
    private Integer roomType;

    private Long recordId;

    /**
     * 未读消息数
     */
    private Integer unread;
    /**
     * 是否显示
     */
    private Boolean visible;
    /**
     * 是否置顶
     */
    private Boolean sticky;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private LocalDateTime readTime;
}
