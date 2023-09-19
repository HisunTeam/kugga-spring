package com.hisun.kugga.duke.bos.dal.dataobject.resumeexperience;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.duke.bos.enums.CertFlagEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.util.Date;

/**
 * 个人简历经历 DO
 *
 * @author 芋道源码
 */
@TableName("duke_resume_experience")
@KeySequence("duke_resume_experience_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeExperienceDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 所属简历id
     */
    private Long resumeId;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 学校
     */
    private String school;
    /**
     * 学历
     */
    private String degree;
    /**
     * 主修
     */
    private String major;
    /**
     * 公司
     */
    private String company;
    /**
     * 职位
     */
    private String position;
    /**
     * 经历描述
     */
    private String description;
    /**
     * 0-教育经历 1-工作经历
     */
    private String type;
    /**
     * 认证标识
     */
    private CertFlagEnum certFlag;
    /**
     * 认证图片
     */
    private String certification;
    /**
     * 经历备注
     */
    private String remark;
    /**
     * 审核意见
     */
    private String suggestion;

    private Boolean deleted;

}
