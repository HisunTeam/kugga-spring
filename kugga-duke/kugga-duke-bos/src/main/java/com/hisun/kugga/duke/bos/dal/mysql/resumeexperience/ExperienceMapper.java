package com.hisun.kugga.duke.bos.dal.mysql.resumeexperience;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.dal.dataobject.resumeexperience.ResumeExperienceDO;
import com.hisun.kugga.duke.bos.dal.dataobject.resumeexperience.ResumeExperienceExDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 个人简历经历 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ExperienceMapper extends BaseMapperX<ResumeExperienceDO> {
    IPage<ResumeExperienceExDO> getResumeExperiencePage(Page page,
                                                        @Param("resumeName") String resumeName,
                                                        @Param("certFlag") String certFlag,
                                                        @Param("school") String school,
                                                        @Param("company") String company,
                                                        @Param("type") String type
    );


}
