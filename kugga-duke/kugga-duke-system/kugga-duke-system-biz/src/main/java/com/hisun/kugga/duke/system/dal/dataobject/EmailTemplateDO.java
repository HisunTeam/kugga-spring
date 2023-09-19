package com.hisun.kugga.duke.system.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 邮件模板参数 DO
 *
 * @author zhou_xiong
 */
@TableName("email_template")
@KeySequence("email_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateDO extends BaseDO {

    /**
     * 邮件模板ID
     */
    @TableId
    private Long id;
    /**
     * 邮件场景，对应com.hisun.kugga.duke.system.enums.EmailScene
     */
    private String emailScene;
    /**
     * 邮件类型【SIMPLE_MAIL：简单邮件、SIMPLE_HTML_MAIL：简单HTML邮件、ATTACHMENTS_MAIL：附件邮件，暂不支持、HTML_AND_IMAGE_MAIL：带图片的邮件，暂不支持】
     */
    private String emailType;
    /**
     * 国际化标识
     */
    private String locale;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件模板，替换值以{}标识
     */
    private String template;
    /**
     * 发送间隔，单位：秒
     */
    private Long sendInterval;
    /**
     * 每日单个发送对象发送次数限制
     */
    private Integer sendLimit;

}
