package com.hisun.kugga.duke.user.cv.service.resume;

import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumeCreateReqVO;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumeMemberReqVO;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumeRespVO;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumeUpdateReqVO;

import javax.validation.Valid;


/**
 * 个人简历信息 Service 接口
 *
 * @author 芋道源码
 */
public interface ResumeService {

    /**
     * 创建个人简历信息
     *
     * @param createReqVO 创建信息
     * @return
     */
    ResumeRespVO createResume(@Valid ResumeCreateReqVO createReqVO);

    /**
     * 更新个人简历信息
     *
     * @param updateReqVO 更新信息
     */
    void updateResume(@Valid ResumeUpdateReqVO updateReqVO);


    /**
     * 获得个人简历信息
     *
     * @param resumeUserId 用户id
     * @return 个人简历信息
     */
    ResumeRespVO getResume();

    ResumeRespVO getResumeByMember(@Valid ResumeMemberReqVO reqVO);
}
