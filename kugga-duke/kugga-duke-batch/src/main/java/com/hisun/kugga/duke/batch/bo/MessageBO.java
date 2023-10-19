package com.hisun.kugga.duke.batch.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Inner message
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageBO {

    /**
     * Message code
     */
    private String messageCode;
    /**
     * Scene (Recommendation letter, chat, authentication, invitation to join a guild, system notification)
     */
    private String scene;
    /**
     * Status (invitation, callback)
     */
    private String type;
    /**
     * Business ID (authentication ID, recommendation letter ID, etc.)
     */
    private Long businessId;
    /**
     * Business link
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
     * Initiator guild ID
     */
    private Long initiatorLeagueId;
    /**
     * Receiver guild ID
     */
    private Long receiverLeagueId;

    /**
     * Message parameters
     */
    private String messageParam;
    /**
     * Message content
     */
    private String content;
    /**
     * Read status (UR - Unread, R - Read)
     */
    private String readFlag;
    /**
     * Processing flag (ND - Not processed, D - Processed, AD - Already processed)
     */
    private String dealFlag;
}
