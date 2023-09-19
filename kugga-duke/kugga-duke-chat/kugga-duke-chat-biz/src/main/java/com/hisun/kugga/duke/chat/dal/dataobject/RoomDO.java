package com.hisun.kugga.duke.chat.dal.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.chat.dal.dataobject.enums.PayChatStatusEnum;
import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 聊天室 DO
 *
 * @author toi
 */
@TableName("chat_room")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 聊天室名字
     */
    private String name;
    /**
     * 聊天室描述
     */
    private String description;
    /**
     * 聊天室头像URL
     */
    private String avatar;
    /**
     * @see com.hisun.kugga.duke.chat.dal.dataobject.enums.RoomTypeEnum
     * 聊天室的类型 1: 群聊，0：私聊，2：单聊
     */
    private Integer roomType;

    /**
     * @see PayTypeEnum
     * 聊天室支付类型 工会内部免费聊天:0,工会外部付费聊天:1
     */
    private Integer payType;

    /**
     * 聊天室最大人数
     */
    private Integer peopleLimit;

    /**
     * 付费聊天情况 过期时间
     * <p>
     * when on
     * PayTypeEnum.PAY_CHAT
     */
    private LocalDateTime expireTime;

    /**
     * 支付状态
     *
     * @see PayChatStatusEnum
     */
    private String payChatStatus;

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

    /**
     * 工会ID
     */
    private Long leagueId;

    /**
     * 聊天发起方的用户ID
     */
    private Long inviterUserId;
}
