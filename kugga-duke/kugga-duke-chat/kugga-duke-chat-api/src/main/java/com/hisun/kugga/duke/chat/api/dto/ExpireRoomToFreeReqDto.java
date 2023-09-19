package com.hisun.kugga.duke.chat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 过期付费聊天更新为免费聊天
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpireRoomToFreeReqDto {

    private Long roomId;

    private Long leagueId;
}
