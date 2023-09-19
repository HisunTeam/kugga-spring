package com.hisun.kugga.duke.system.service.impl;

import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalCreateReqVO;
import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalUpdateReqVO;
import com.hisun.kugga.duke.system.convert.EmailSendJournalConvert;
import com.hisun.kugga.duke.system.dal.dataobject.EmailSendJournalDO;
import com.hisun.kugga.duke.system.dal.mysql.EmailSendJournalMapper;
import com.hisun.kugga.duke.system.service.EmailSendJournalService;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.SEND_JOURNAL_NOT_EXISTS;

/**
 * 邮件发送流水 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class EmailSendJournalServiceImpl implements EmailSendJournalService {

    @Resource
    private EmailSendJournalMapper sendJournalMapper;

    @Override
    public Long createSendJournal(EmailSendJournalCreateReqVO createReqVO) {
        // 插入
        EmailSendJournalDO sendJournal = EmailSendJournalConvert.INSTANCE.convert(createReqVO);
        sendJournalMapper.insert(sendJournal);
        // 返回
        return sendJournal.getId();
    }

    @Override
    public void updateSendJournal(EmailSendJournalUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateSendJournalExists(updateReqVO.getId());
        // 更新
        EmailSendJournalDO updateObj = EmailSendJournalConvert.INSTANCE.convert(updateReqVO);
        sendJournalMapper.updateById(updateObj);
    }

    @Override
    public void deleteSendJournal(Long id) {
        // 校验存在
        this.validateSendJournalExists(id);
        // 删除
        sendJournalMapper.deleteById(id);
    }

    private void validateSendJournalExists(Long id) {
        if (sendJournalMapper.selectById(id) == null) {
            ServiceException.throwServiceException(SEND_JOURNAL_NOT_EXISTS);
        }
    }

    @Override
    public EmailSendJournalDO getSendJournal(Long id) {
        return sendJournalMapper.selectById(id);
    }

    @Override
    public List<EmailSendJournalDO> getSendJournalList(Collection<Long> ids) {
        return sendJournalMapper.selectBatchIds(ids);
    }

}
