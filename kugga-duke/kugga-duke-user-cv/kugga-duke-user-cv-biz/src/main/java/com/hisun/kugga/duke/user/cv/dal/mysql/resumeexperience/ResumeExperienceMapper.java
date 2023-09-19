package com.hisun.kugga.duke.user.cv.dal.mysql.resumeexperience;


import com.hisun.kugga.duke.user.cv.dal.dataobject.resumeexperience.ResumeExperienceDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 个人简历经历 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ResumeExperienceMapper extends BaseMapperX<ResumeExperienceDO> {

    int updateExperienceById(@Param("experienceDO") ResumeExperienceDO experienceDO);


}
