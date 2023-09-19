package com.hisun.kugga.duke.system.service;

import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalCreateReqVO;
import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalUpdateReqVO;
import com.hisun.kugga.duke.system.dal.dataobject.EmailSendJournalDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 邮件发送流水 Service 接口
 *
 * @author 芋道源码
 */
public interface EmailSendJournalService {

    /**
     * 创建邮件发送流水
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSendJournal(@Valid EmailSendJournalCreateReqVO createReqVO);

    /**
     * 更新邮件发送流水
     *
     * @param updateReqVO 更新信息
     */
    void updateSendJournal(@Valid EmailSendJournalUpdateReqVO updateReqVO);

    /**
     * 删除邮件发送流水
     *
     * @param id 编号
     */
    void deleteSendJournal(Long id);

    /**
     * 获得邮件发送流水
     *
     * @param id 编号
     * @return 邮件发送流水
     */
    EmailSendJournalDO getSendJournal(Long id);

    /**
     * 获得邮件发送流水列表
     *
     * @param ids 编号
     * @return 邮件发送流水列表
     */
    List<EmailSendJournalDO> getSendJournalList(Collection<Long> ids);

}
