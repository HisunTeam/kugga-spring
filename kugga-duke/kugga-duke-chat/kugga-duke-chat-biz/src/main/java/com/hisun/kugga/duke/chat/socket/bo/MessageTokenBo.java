package com.hisun.kugga.duke.chat.socket.bo;

import com.hisun.kugga.duke.chat.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTokenBo {

    private Long roomId;

    /**
     * @see PayTypeEnum
     */
    private Integer payType;

}
