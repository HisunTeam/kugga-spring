package com.hisun.kugga.framework.email.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.framework.common.exception.ServiceException;
import com.hisun.kugga.framework.email.core.property.EmailProperties;
import com.hisun.kugga.framework.email.core.roundrobin.RoundRobin;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static com.hisun.kugga.framework.email.core.enums.EmailErrorCodeConstants.MAIL_SEND_FAILED;

/**
 * @author: zhou_xiong
 */
@Slf4j
public class MailExecutor implements InitializingBean {

    private RoundRobin roundRobin;

    private EmailProperties emailProperties;

    private Map<String, JavaMailSenderImpl> mailSender = new ConcurrentHashMap<>(16);

    public MailExecutor(RoundRobin roundRobin, EmailProperties emailProperties) {
        this.roundRobin = roundRobin;
        this.emailProperties = emailProperties;
    }

    private JavaMailSenderImpl applyProperties(MailProperties properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(properties.getHost());
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }
        if (!properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(properties.getProperties()));
        }
        return sender;
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    /**
     * 发送附件邮件
     *
     * @param subject
     * @param content
     * @param imageFileMap
     * @param files
     * @param to
     */
    public void sendAttachmentsMail(String subject, String content, Map<String, File> imageFileMap, List<File> files, String... to) {
        if (StrUtil.hasBlank(subject, content) || ArrayUtil.isEmpty(to)) {
            return;
        }
        try {
            JavaMailSenderImpl mailSender = getJavaMailSender();
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(mailSender.getUsername());
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            if (CollUtil.isNotEmpty(imageFileMap)) {
                for (Map.Entry<String, File> entry : imageFileMap.entrySet()) {
                    messageHelper.addInline(entry.getKey(), new FileSystemResource(entry.getValue()));
                }
            }
            if (CollUtil.isNotEmpty(files)) {
                for (File file : files) {
                    String fileName = FilenameUtils.getName(file.getName());
                    messageHelper.addAttachment(fileName, file);
                }
            }
            mailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送异常", e);
            throw new ServiceException(MAIL_SEND_FAILED);
        }

    }

    /**
     * 发送 Html 和图像邮件
     *
     * @param subject
     * @param content
     * @param imageFileMap
     * @param to
     */
    public void sendHtmlAndImageMail(String subject, String content, Map<String, File> imageFileMap, String... to) {
        if (StrUtil.hasBlank(subject, content) || ArrayUtil.isEmpty(to)) {
            return;
        }
        sendAttachmentsMail(subject, content, imageFileMap, null, to);
    }

    /**
     * 发送简单的 HTML 邮件
     *
     * @param subject
     * @param content
     * @param to
     */
    public void sendSimpleHtmlMail(String subject, String content, String... to) {
        if (StrUtil.hasBlank(subject, content) || ArrayUtil.isEmpty(to)) {
            return;
        }
        sendHtmlAndImageMail(subject, content, null, to);
    }

    public void sendSimpleMail(String subject, String content, String... to) {
        if (StrUtil.hasBlank(subject, content) || ArrayUtil.isEmpty(to)) {
            return;
        }
        try {
            JavaMailSenderImpl mailSender = getJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSender.getUsername());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("邮件发送异常", e);
            throw new ServiceException(MAIL_SEND_FAILED);
        }
    }


    private JavaMailSenderImpl getJavaMailSender() {
        return mailSender.get(roundRobin.select().id());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        emailProperties.getProperties().forEach(e ->
                mailSender.put(e.getUsername(), applyProperties(e))
        );
    }
}
