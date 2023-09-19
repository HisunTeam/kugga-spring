package com.hisun.kugga.duke.user.cv.dal.dataobject.resumecontent;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 个人简历内容 DO
 *
 * @author 芋道源码
 */
@TableName("duke_resume_content")
@KeySequence("duke_resume_content_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeContentDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 个人简历id
     */
    private Long resumeId;
    /**
     * 个人简历一段内容
     */
    private String content;
    /**
     * 原始文本--方便做搜索
     */
    private String originalText;
    private Boolean deleted;

}
