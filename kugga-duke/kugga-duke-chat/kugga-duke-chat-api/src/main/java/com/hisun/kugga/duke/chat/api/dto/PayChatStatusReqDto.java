package com.hisun.kugga.duke.chat.api.dto;

import com.hisun.kugga.duke.chat.api.enums.PayChatStatusApiEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 付费聊天状态更新
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayChatStatusReqDto {

    /**
     * 工会Id
     */
    private Long receiveLeagueId;

    /**
     * 聊天发起方的用户ID
     */
    private Long inviterUserId;

    private Long roomId;

    private PayChatStatusApiEnum payChatStatus;
}
