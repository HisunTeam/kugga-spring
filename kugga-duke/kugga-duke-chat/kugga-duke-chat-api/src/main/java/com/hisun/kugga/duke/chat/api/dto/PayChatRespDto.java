package com.hisun.kugga.duke.chat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 付费聊天
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayChatRespDto {

    /**
     * roomId
     */
    private Long roomId;

}
