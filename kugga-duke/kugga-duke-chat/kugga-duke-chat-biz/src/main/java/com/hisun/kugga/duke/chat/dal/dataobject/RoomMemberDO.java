package com.hisun.kugga.duke.chat.dal.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 聊天室成员 DO
 *
 * @author toi
 */
@TableName("chat_room_member")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 聊天室ID
     */
    private Long roomId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 室友昵称
     */
    private String nickname;
    /**
     * @see com.hisun.kugga.duke.chat.dal.dataobject.enums.RoomMemberRoleEnum
     * <p>
     * 室友角色 default 0
     */
    private Integer role;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
