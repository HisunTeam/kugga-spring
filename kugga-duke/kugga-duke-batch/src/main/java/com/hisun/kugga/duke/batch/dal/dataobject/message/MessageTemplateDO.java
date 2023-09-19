package com.hisun.kugga.duke.batch.dal.dataobject.message;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.enums.message.MessageTemplateEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 消息模板参数 DO
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
     * 消息模板ID
     */
    @TableId
    private Long id;
    /**
     * 排序区分作用
     */
    private String messageCode;

    /**
     * 消息场景，对应{@link MessageTemplateEnum}
     */
    private String messageKey;
    /**
     * 消息场景，对应
     */
    private String messageScene;
    /**
     * 消息类型 对应
     */
    private String messageType;
    /**
     * 语言
     */
    private String language;
    /**
     * 消息主题
     */
    private String subject;
    /**
     * 消息模板，替换值以{}标识
     */
    private String template;
    /**
     * 原始模板
     */
    private String originalTemplate;
    /**
     * 发送间隔，单位：秒
     */
    private Long sendInterval;
    /**
     * 每日单个发送对象发送次数限制
     */
    private Integer sendLimit;

}
