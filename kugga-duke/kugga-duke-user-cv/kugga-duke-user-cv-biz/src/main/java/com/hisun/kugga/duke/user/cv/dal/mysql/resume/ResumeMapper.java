package com.hisun.kugga.duke.user.cv.dal.mysql.resume;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumeExportReqVO;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumePageReqVO;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.ResumeRespVO;
import com.hisun.kugga.duke.user.cv.dal.dataobject.resume.ResumeDO;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 个人简历信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ResumeMapper extends BaseMapperX<ResumeDO> {

    default ResumeDO selectByUserId(Long userId) {
        return selectOne(new LambdaQueryWrapperX<ResumeDO>()
                .eq(ResumeDO::getResumeUserId, userId)
                .orderByDesc(ResumeDO::getId).last("limit 1"));
    }

    default PageResult<ResumeDO> selectPage(ResumePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ResumeDO>()
                .eq(ResumeDO::getResumeUserId, reqVO.getResumeUserId())
                .orderByDesc(ResumeDO::getUpdateTime));
    }

    default List<ResumeDO> selectList(ResumeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ResumeDO>()
                .eqIfPresent(ResumeDO::getResumeUserId, reqVO.getResumeUserId())
                .eqIfPresent(ResumeDO::getContent, reqVO.getContent())
                .betweenIfPresent(ResumeDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ResumeDO::getId));
    }

    ResumeRespVO selectOneByUserId(@Param("userId") Long id);

    default Boolean judgeExistByUserId(@Param("userId") Long userId) {
        boolean exists = exists(new LambdaQueryWrapperX<ResumeDO>()
                .eq(ResumeDO::getResumeUserId, userId)
        );
        return exists;
    }


    IPage<ResumeRespVO> selectPageList(Page<ResumeRespVO> page, @Param("userId") Long id);

    /**
     * 根据简历id统计 简历类型数量(教育经历、工作经历)
     * @param resumeId
     * @return
     */
    Integer getResumeTypeCount(@Param("resumeId") Long resumeId);
}
