package com.hisun.kugga.duke.bos.service.resumeexperience;


import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceBatchUpdateReqVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperiencePageReqVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceRespVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceUpdateReqVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Map;


/**
 * 个人简历经历 Service 接口
 *
 * @author 芋道源码
 */
public interface ResumeExperienceService {

    /**
     * 获取审核状态
     */
    Map<String, String> selectAllCertType();


    /**
     * 获得个人简历经历分页
     *
     * @param pageReqVO 分页查询
     * @return 个人简历经历分页
     */
    PageResult<ResumeExperienceRespVO> getResumeExperiencePage(ResumeExperiencePageReqVO reqVO);


    /**
     * 更新个人简历经历
     *
     * @param updateReqVO 更新信息
     */
    void updateResumeExperience(@Valid ResumeExperienceUpdateReqVO updateReqVO);


    /**
     * 更新个人简历经历
     *
     * @param updateReqVO 更新信息
     */
    void updateResumeExperienceBatch(@Valid ResumeExperienceBatchUpdateReqVO updateReqVO);


    /**
     * 获得个人简历经历分页
     *
     * @param pageReqVO 分页查询
     * @return 个人简历经历分页
     */
//    PageResult<ResumeExperienceDO> getResumeExperiencePage(ResumeExperiencePageReqVO pageReqVO);


}
