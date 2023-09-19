package com.hisun.kugga.duke.system.api.email;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.duke.system.api.email.dto.GeneralEmailReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.SendCodeReqDTO;
import com.hisun.kugga.duke.system.api.email.dto.VerifyCodeReqDTO;
import com.hisun.kugga.duke.system.controller.app.email.vo.EmailSendJournalCreateReqVO;
import com.hisun.kugga.duke.system.dal.dataobject.EmailTemplateDO;
import com.hisun.kugga.duke.system.service.EmailSendJournalService;
import com.hisun.kugga.duke.system.service.EmailTemplateService;
import com.hisun.kugga.duke.utils.RedisUtils;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.common.util.date.DateTimeUtils;
import com.hisun.kugga.framework.email.core.MailExecutor;
import com.hisun.kugga.framework.idempotent.core.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.*;
import static com.hisun.kugga.duke.enums.email.EmailType.getByValue;

/**
 * @author: zhou_xiong
 */
@Slf4j
@Service
public class SendEmailApiImpl implements SendEmailApi {
    public static final Long ONE_DAY_SECOND = 86400L;
    public static final String COUNT_LOCK = "_lock_";
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private EmailTemplateService emailTemplateService;
    @Resource
    private EmailSendJournalService emailSendJournalService;
    @Resource
    private MailExecutor mailExecutor;

    @Override
    @Idempotent(timeout = 10, message = "Too many emails being sent. please try again later")
    public void sendCode(SendCodeReqDTO sendCodeReqDTO) {
        if (ObjectUtil.isNull(sendCodeReqDTO.getEmailScene()) || StrUtil.isBlank(sendCodeReqDTO.getTo())) {
            ServiceException.throwServiceException(ILLEGAL_PARAM);
        }
        // 6位随机验证码
        String code = RandomUtil.randomNumbers(6);
        GeneralEmailReqDTO generalEmailReqDTO = new GeneralEmailReqDTO();
        generalEmailReqDTO.setEmailScene(sendCodeReqDTO.getEmailScene());
        generalEmailReqDTO.setTo(CollUtil.newArrayList(sendCodeReqDTO.getTo()));
        generalEmailReqDTO.setReplaceValues(CollUtil.newArrayList(code));
        generalEmailReqDTO.setLocale(sendCodeReqDTO.getLocale());
        this.sendEmail(generalEmailReqDTO);
        String redisKey = redisUtils.buildRedisKey(sendCodeReqDTO.getEmailScene().getPrefix(), sendCodeReqDTO.getTo());
        redisUtils.setForTimeSec(redisKey, code, 1800L);
    }

    @Override
    @Idempotent(timeout = 10, message = "Too many emails being sent. please try again later")
    public void sendEmail(GeneralEmailReqDTO generalEmailReqDTO) {
        // 发送频率控制
        List<String> lockTimeKeys = generalEmailReqDTO.getTo().stream().map(to -> {
            String lockTimeKey = redisUtils.buildRedisKey(generalEmailReqDTO.getEmailScene().getPrefix(), COUNT_LOCK, to);
            if (redisUtils.exist(lockTimeKey)) {
                ServiceException.throwServiceException(EMAIL_IS_LOCK);
            }
            return lockTimeKey;
        }).collect(Collectors.toList());
        // 查询邮件模板配置
        EmailTemplateDO emailTemplateDO = emailTemplateService.selectOneByEmailSceneAndLocale(generalEmailReqDTO.getEmailScene(), generalEmailReqDTO.getLocale());
        // 邮件发送次数判断
        List<String> sendCountKeys = generalEmailReqDTO.getTo().stream().map(to -> {
            String sendCountKey = redisUtils.buildRedisKey(generalEmailReqDTO.getEmailScene().getPrefix(), to, DateTimeUtils.getCurrentDateStr());
            Integer count = redisUtils.getInteger(sendCountKey);
            if (ObjectUtil.isNull(count)) {
                redisUtils.setForTimeSec(sendCountKey, 0, ONE_DAY_SECOND);
            }
            // 校验当日邮件发送次数
            if (ObjectUtil.isNotNull(count) && count >= emailTemplateDO.getSendLimit()) {
                log.error("邮件发送对象{}，日发送邮件数量已超过阈值{}", to, emailTemplateDO.getSendLimit());
                ServiceException.throwServiceException(EMAIL_SEND_COUNT_LIMIT);
            }
            return sendCountKey;
        }).collect(Collectors.toList());
        // 模板参数替换
        String content = StrUtil.format(emailTemplateDO.getTemplate(), ArrayUtil.toArray(generalEmailReqDTO.getReplaceValues(), String.class));
        // 发邮件
        String[] to = ArrayUtil.toArray(generalEmailReqDTO.getTo(), String.class);
        if (StrUtil.hasEmpty(to)) {
            throw new ServiceException(ILLEGAL_PARAMS, "email");
        }
        EmailSendJournalCreateReqVO emailSendJournalCreateReqVO = new EmailSendJournalCreateReqVO();
        emailSendJournalCreateReqVO.setResult(SUCCESS);
        try {
            switch (getByValue(emailTemplateDO.getEmailType())) {
                case SIMPLE_MAIL:
                    mailExecutor.sendSimpleMail(emailTemplateDO.getSubject(), content, to);
                    break;
                case SIMPLE_HTML_MAIL:
                    mailExecutor.sendSimpleHtmlMail(emailTemplateDO.getSubject(), content, to);
                    break;
                default:
                    ServiceException.throwServiceException(UNSUPPORTED_EMAIL_TYPE);
            }
        } catch (Exception e) {
            emailSendJournalCreateReqVO.setResult(FAILED);
            throw e;
        } finally {
            // 邮件发送结果存库
            emailSendJournalCreateReqVO.setTemplateId(emailTemplateDO.getId());
            emailSendJournalCreateReqVO.setContent(content);
            emailSendJournalCreateReqVO.setReceiver(StrUtil.join("|", generalEmailReqDTO.getTo()));
            emailSendJournalService.createSendJournal(emailSendJournalCreateReqVO);
        }
        // 发送次数累加
        sendCountKeys.forEach(redisUtils::increment);
        // 设置下发频率标识
        lockTimeKeys.forEach(key -> {
                    if (emailTemplateDO.getSendInterval() != 0L) {
                        redisUtils.setForTimeSec(key, "1", emailTemplateDO.getSendInterval());
                    }
                }
        );
    }

    @Override
    public boolean verifyCode(VerifyCodeReqDTO verifyCodeReqDTO) {
        String redisKey = redisUtils.buildRedisKey(verifyCodeReqDTO.getEmailScene().getPrefix(), verifyCodeReqDTO.getEmail());
        String redisCode = redisUtils.get(redisKey);
        if (StrUtil.isBlank(redisCode)) {
            ServiceException.throwServiceException(CODE_EXPIRED);
        }
        if (!StrUtil.equals(redisCode, verifyCodeReqDTO.getVerifyCode())) {
            ServiceException.throwServiceException(CODE_WRONG);
        }
        // 校验通过，删除验证码
        redisUtils.delete(redisKey);
        return true;
    }
}
