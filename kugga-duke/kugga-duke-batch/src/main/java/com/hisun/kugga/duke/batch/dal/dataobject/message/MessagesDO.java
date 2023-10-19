package com.hisun.kugga.duke.batch.dal.dataobject.message;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * Message DO
 *
 * @author 芋道源码
 */
@TableName("duke_message")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagesDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * Message Key
     */
    private String messageKey;
    /**
     * Scene (Recommendation, Chat, Authentication, Invitation to Join Guild, System Notification)
     */
    private String scene;
    /**
     * Status (Invitation, Callback)
     */
    private String type;
    /**
     * Business ID (Authentication ID, Recommendation Letter ID, etc.)
     */
    private Long businessId;
    /**
     * Business Link
     */
    private String businessLink;
    /**
     * Initiator
     */
    private Long initiatorId;
    /**
     * Receiver
     */
    private Long receiverId;
    /**
     * Initiator's Guild ID
     */
    private Long initiatorLeagueId;
    /**
     * Receiver's Guild ID
     */
    private Long receiverLeagueId;

    /**
     * Message Parameters
     */
    private String messageParam;
    /**
     * Message Content
     */
    private String content;
    /**
     * Read Status (UR - Unread, R - Read)
     */
    private String readFlag;
    /**
     * Processing Flag (ND - Not Processed, D - Processed, AD - Already Processed)
     */
    private String dealFlag;

}

