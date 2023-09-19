package com.hisun.kugga.duke.system.service.messages;

import com.hisun.kugga.duke.system.api.message.dto.MessagesUpdateReqDTO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesPageReqVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesRespVO;
import com.hisun.kugga.duke.system.controller.app.message.vo.MessagesUpdateReqVO;
import com.hisun.kugga.duke.system.dal.dataobject.MessageTemplateDO;
import com.hisun.kugga.duke.system.dal.dataobject.MessagesDO;
import com.hisun.kugga.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.List;


/**
 * 消息 Service 接口
 *
 * @author 芋道源码
 */
public interface MessagesService {

    /**
     * 初始化消息模板
     *
     * @return
     */
    List<MessageTemplateDO> initMessageTemplate();

    /**
     * 更新消息
     *
     * @param updateReqVO 更新信息
     */
    void updateMessages(@Valid MessagesUpdateReqVO updateReqVO);

    /**
     * 获得消息
     *
     * @param id 编号
     * @return 消息
     */
    MessagesDO getMessages(Long id);


    /**
     * 获得消息分页
     *
     * @param pageReqVO 分页查询
     * @return 消息分页
     */
    PageResult<MessagesDO> getMessagesPage(MessagesPageReqVO pageReqVO);

    /**
     * 消息已读
     *
     * @param updateReqVO
     */
    void updateReadMessages(MessagesUpdateReqVO updateReqVO);

    /**
     * 内部消息处理
     * 推荐报告、认证等内部消息处理，此时一次任务下所有消息已处理
     *
     * @param reqDTO
     */
    void dealMessagesInner(MessagesUpdateReqDTO reqDTO);

    /**
     * 消息已处理
     *
     * @param updateReqVO
     */
    void updateDealMessages(MessagesUpdateReqVO updateReqVO);

    /**
     * 清除小红点一键已读
     *
     * @param userId
     */
    void cleanRedSpot(Long userId);

    /**
     * 获取用户未读消息数
     *
     * @param loginUserId
     * @return
     */
    Long getUserUnReadCount(Long loginUserId);

    /**
     * 判断是否有未读消息
     *
     * @param userId
     * @return
     */
    boolean getIsUserUnRead(Long userId);

    /**
     * 判断是否有消息未读数
     * 消息 帖子消息、未认证公会
     *
     * @return
     */
    boolean getAllUnRead(Long userId);

    /**
     * 查询消息分页列表
     *
     * @param pageVO
     * @return
     */
    PageResult<MessagesRespVO> getPageMessages(MessagesPageReqVO pageVO);
}
