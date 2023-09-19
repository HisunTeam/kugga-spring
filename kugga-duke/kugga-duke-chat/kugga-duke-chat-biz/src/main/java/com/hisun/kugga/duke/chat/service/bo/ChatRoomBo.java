package com.hisun.kugga.duke.chat.service.bo;

import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomBo {

    private Long leagueId;

    private Long userId;

    private Long receiveUserId;

    PayTypeEnum payTypeEnum;

    private LocalDateTime expireTime;
}
