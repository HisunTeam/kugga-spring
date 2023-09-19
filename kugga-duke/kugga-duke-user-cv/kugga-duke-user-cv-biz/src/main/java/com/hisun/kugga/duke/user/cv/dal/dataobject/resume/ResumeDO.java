package com.hisun.kugga.duke.user.cv.dal.dataobject.resume;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.util.Date;

/**
 * 个人简历信息 DO
 *
 * @author 芋道源码
 */
@TableName("duke_resume")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long resumeUserId;
    /**
     * 个人简历内容
     */
    private String content;
    /**
     * 个人介绍
     */
    private String introduce;

    /**
     * 姓名
     */
    private String resumeName;
    /**
     * 出生年月
     */
    private Date birthday;
    /**
     * 学历
     */
    private String degree;
    /**
     * 电话号码
     */
    private String phoneNum;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 职业技能
     */
    private String skills;
    /**
     * 性别
     */
    private String sex;
    /**
     * 头像
     */
    private String resumeAvatar;
    /**
     * 隐藏标识 1-隐藏 0-展示
     */
    private String hide;
    /**
     * 是否完善所有经验
     * true 完善 false不完善
     * 辅助进行成长值积分
     */
    private Boolean allExperience;

}
