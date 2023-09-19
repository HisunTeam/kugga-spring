package com.hisun.kugga.duke.forum.service;

import com.hisun.kugga.duke.forum.vo.MessageDetailsReqVO;
import com.hisun.kugga.duke.forum.vo.MessageDetailsVO;
import com.hisun.kugga.duke.forum.vo.MessageVO;
import com.hisun.kugga.duke.forum.vo.PageMessageReqVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * @author zuocheng
 */
public interface MessageService {
    /**
     * 查询论坛消息列表
     *
     * @param reqVo
     * @return
     */
    PageResult<MessageVO> pageList(PageMessageReqVO reqVo);

    /**
     * 获取未读消息条数
     *
     * @return
     */
    Long unreadNum();

    /**
     * 修改消息为已读
     */
    void read();

    /**
     * 单条信息已读
     */
    void singleRead(Long id);

    /**
     * 查看消息详情（查看楼层详情）
     *
     * @param reqVO
     * @return
     */
    MessageDetailsVO details(MessageDetailsReqVO reqVO);
}
