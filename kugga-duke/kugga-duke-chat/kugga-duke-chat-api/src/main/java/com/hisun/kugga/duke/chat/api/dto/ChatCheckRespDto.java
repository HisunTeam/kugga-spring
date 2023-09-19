package com.hisun.kugga.duke.chat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCheckRespDto {


    /**
     * 判断是否两人有可用的聊天房间
     * <p>
     * 存在两种情况：true
     * 1、免费聊天
     * 2、未过期的付费聊天
     */
    private Boolean isOnChat;

    /*
     * 双方用户是否在私人聊天室
     *
     * True：已存在房间
     * False：不存在
     */
    private Boolean isInPrivateRoom;

    private Long roomId;

    /*
     * 如果房间存在， isInPrivateRoom = true
     * 返回对应房间类型，付费和非付费
     *
     * @see PayTypeEnum
     * 聊天室支付类型 工会内部免费聊天:0,工会外部付费聊天:1
     */
//    @Deprecated
//    private PayTypeEnum payType;

}
