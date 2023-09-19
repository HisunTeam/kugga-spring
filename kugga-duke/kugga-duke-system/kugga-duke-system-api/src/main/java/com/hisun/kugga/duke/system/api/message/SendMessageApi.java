package com.hisun.kugga.duke.system.api.message;

import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.dto.SendMessageReqDTO;

/**
 * @Description:
 * @author： Lin
 * @Date 2022/7/29 14:58
 */
public interface SendMessageApi {

    /**
     * 根据不同场景发送邮件验证码
     *
     * @param messageReqDTO
     */
    void sendMessage(SendMessageReqDTO messageReqDTO);

    /**
     * 消息处理接口
     * 传入一个任务id，找到任务id 修改其消息状态
     *
     * @param reqDTO
     */
    void messageDeal(MessagesUpdateReqDTO reqDTO);

    /**
     * 是否有未读消息
     *
     * @return
     */
    boolean unreadFlag(Long userId);
}
