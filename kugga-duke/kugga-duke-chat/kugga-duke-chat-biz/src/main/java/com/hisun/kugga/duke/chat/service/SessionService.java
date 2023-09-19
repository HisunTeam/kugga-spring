package com.hisun.kugga.duke.chat.service;

import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionPageReqVO;
import com.hisun.kugga.duke.chat.controller.app.vo.session.SessionRespVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * 聊天会话列 Service 接口
 *
 * @author toi
 */
public interface SessionService {
    /**
     * 获得聊天会话列分页
     *
     * @param pageReqVO 分页查询
     * @return 聊天会话列分页
     */
    PageResult<SessionRespVO> getSessionPage(SessionPageReqVO pageReqVO);

    /**
     * 已读会话列表
     */
    void read(Long id, Long userId);

    /**
     *
     */
    Boolean messageRetDot(Long userId);
}
