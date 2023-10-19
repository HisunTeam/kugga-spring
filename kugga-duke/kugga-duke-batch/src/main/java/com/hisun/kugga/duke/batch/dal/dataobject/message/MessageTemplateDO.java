package com.hisun.kugga.duke.batch.dal.dataobject.message;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * Message Template Parameters DO
 *
 * @author 芋道源码
 */
@TableName("duke_message_template")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplateDO extends BaseDO {

    /**
     * Message Template ID
     */
    @TableId
    private Long id;
    /**
     * Sorting distinction
     */
    private String messageCode;

    /**
     * Message Scene, corresponds to {@link MessageTemplateEnum}
     */
    private String messageKey;
    /**
     * Message Scene
     */
    private String messageScene;
    /**
     * Message Type
     */
    private String messageType;
    /**
     * Language
     */
    private String language;
    /**
     * Message Subject
     */
    private String subject;
    /**
     * Message Template with replacement values enclosed in {}
     */
    private String template;
    /**
     * Original Template
     */
    private String originalTemplate;
    /**
     * Sending Interval, in seconds
     */
    private Long sendInterval;
    /**
     * Daily Send Limit per individual recipient
     */
    private Integer sendLimit;

}
