package com.hisun.kugga.duke.system.dal.dataobject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 邮件发送流水 DO
 *
 * @author 芋道源码
 */
@TableName("email_send_journal")
@KeySequence("email_send_journal_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendJournalDO extends BaseDO {

    /**
     * 发送邮件流水ID
     */
    @TableId
    private Long id;
    /**
     * 邮件模板ID
     */
    private Long templateId;
    /**
     * 发送对象
     */
    private String receiver;
    /**
     * 内容
     */
    private String content;
    /**
     * 发送成功与否【success、failed】
     */
    private String result;

}
