package com.hisun.kugga.duke.chat.service;

import com.hisun.kugga.duke.chat.controller.app.vo.room.RequestChatReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.room.RequestChatRespVO;

import javax.validation.Valid;

/**
 * 聊天室 Service 接口
 *
 * @author toi
 */
public interface RoomService {
    /**
     * 创建聊天室
     */
    RequestChatRespVO createRoom(@Valid RequestChatReqVO requestChatReqVO);
}
