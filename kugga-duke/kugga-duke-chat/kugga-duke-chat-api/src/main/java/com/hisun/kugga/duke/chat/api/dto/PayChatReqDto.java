package com.hisun.kugga.duke.chat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 付费聊天
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayChatReqDto {

    /**
     * 工会Id
     */
    private Long leagueId;

    /**
     * 聊天发起方
     */
    private Long userId;

    /**
     * 聊天被发起方
     */
    private Long receiveUserId;

    /**
     * 这个是有效期的时间，为点击同意沟通后的时间+7d
     * <p>
     * 聊天过期时间
     */
    private LocalDateTime expireTime;

}
