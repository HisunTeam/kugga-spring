package com.hisun.kugga.duke.chat.api;

import com.hisun.kugga.duke.chat.api.dto.*;

/**
 * 聊天Api
 */
public interface ChatApi {

    /**
     * 判断两个成员是否能聊天、或是否存在已存在的聊天、存在已存在的聊天返回聊天信息
     */
    ChatCheckRespDto chatCheck(ChatCheckReqDto reqDto);


    /**
     * 聊天付费情况，更新聊天付费状态
     * <p>
     * 1、如果是支付成功，更新状态为：TO_BE_CONFIRMED
     * <p>
     * 2、如果是支付过期，更新状态为：EXPIRE
     */
    void payChatStatus(PayChatStatusReqDto reqDto);

    /**
     * 付费聊天，请求通过后，调用
     * <p>
     * 如果没有房间创建房间，如果付费房间存在更新房间有效期
     * <p>
     * 用户确认聊天，更新状态为：CONFIRMED
     */
    PayChatRespDto payChat(PayChatReqDto reqDto);

    /**
     * 如果两个人的过期房间
     */
    void expireRoomToFree(ExpireRoomToFreeReqDto reqDto);

    Boolean messageRetDot(Long userId);
}
