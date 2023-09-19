package com.hisun.kugga.duke.user.cv.service.resume;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.enums.GrowthType;
import com.hisun.kugga.duke.enums.NumberEnum;
import com.hisun.kugga.duke.enums.ResumeType;
import com.hisun.kugga.duke.growthrule.factory.GrowthRuleFactory;
import com.hisun.kugga.duke.league.api.LeagueApi;
import com.hisun.kugga.duke.user.cv.controller.app.resume.vo.*;
import com.hisun.kugga.duke.user.cv.dal.dataobject.resume.ResumeDO;
import com.hisun.kugga.duke.user.cv.dal.dataobject.resumecontent.ResumeContentDO;
import com.hisun.kugga.duke.user.cv.dal.dataobject.resumeexperience.ResumeExperienceDO;
import com.hisun.kugga.duke.user.cv.dal.mysql.resume.ResumeMapper;
import com.hisun.kugga.duke.user.cv.dal.mysql.resumeexperience.ResumeExperienceMapper;
import com.hisun.kugga.duke.user.cv.service.resumecontent.ResumeContentService;
import com.hisun.kugga.framework.lock.DistributedLocked;
import com.hisun.kugga.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.RESUME_EXISTS;
import static com.hisun.kugga.duke.common.BusinessErrorCodeConstants.RESUME_NOT_EXISTS;
import static com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hisun.kugga.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * 个人简历信息 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class ResumeServiceImpl implements ResumeService {

    @Resource
    private ResumeMapper resumeMapper;
    @Resource
    private ResumeContentService resumeContentService;

    @Resource
    private ResumeExperienceMapper resumeExperienceMapper;
    @Resource
    private LeagueApi leagueApi;
    @Resource
    private GrowthRuleFactory growthRuleFactory;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResumeRespVO createResume(ResumeCreateReqVO reqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        // 校验存在
        if (Boolean.TRUE.equals(resumeMapper.judgeExistByUserId(loginUserId))) {
            throw exception(RESUME_EXISTS);
        }
        // 插入个人简历主表
        ResumeDO resume = new ResumeDO();
        BeanUtils.copyProperties(reqVO, resume);
        resume.setResumeUserId(loginUserId);
        resumeMapper.insert(resume);
        //插入经历表
        Long id = resume.getId();

        return new ResumeRespVO().setId(id);
    }


    @DistributedLocked(lockName = "'Resume:updateResume:'+#reqVO.getUserId()", leaseTime = 15, waitTime = 0)
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateResume(ResumeUpdateReqVO reqVO) {
        Long loginUserId = reqVO.getUserId();
        Long resumeId;

        // 校验用户存在
        if (ObjectUtil.isNull(loginUserId)) {
            throw exception(RESUME_NOT_EXISTS);
        }

        //查询简历 获取简历id
        ResumeDO resumeDO = resumeMapper.selectByUserId(loginUserId);
        //判断有没有简历id，没有则创建简历
        if (ObjectUtil.isNull(resumeDO)) {
            resumeId = this.createResume(new ResumeCreateReqVO()).getId();
        } else {
            resumeId = resumeDO.getId();
        }

        reqVO.setId(resumeId);
        //更新简历表
        ResumeDO resume = new ResumeDO();
        BeanUtils.copyProperties(reqVO, resume);
        resumeMapper.updateById(resume);
        List<ResumeBaseVO.Experience> studyExperience = reqVO.getStudyExperience();
        if (ObjectUtil.isNotNull(studyExperience)) {
            //更新学习经历表 逻辑删除所有，再修改数据
            this.deleteExperienceByResumeId(resumeId, ResumeType.STUDY.getTypeCode());
            studyExperience.forEach(item -> {
                if (ObjectUtil.isNotNull(item.getId())) {
                    ResumeExperienceDO resumeExperienceDO = copyExperience(item);
                    resumeExperienceDO.setDeleted(false);
                    resumeExperienceMapper.updateExperienceById(resumeExperienceDO.setType(ResumeType.STUDY.getTypeCode()));
                } else {
                    ResumeExperienceDO workExperienceDO = copyExperience(item);
                    resumeExperienceMapper.insert(workExperienceDO.setResumeId(resumeId).setType(ResumeType.STUDY.getTypeCode()));
                }
            });
        }
        List<ResumeBaseVO.Experience> workExperience = reqVO.getWorkExperience();
        if (ObjectUtil.isNotNull(reqVO.getWorkExperience())) {
            //更新工作经历表
            this.deleteExperienceByResumeId(resumeId, ResumeType.WORK.getTypeCode());
            workExperience.forEach(item -> {
                if (ObjectUtil.isNotNull(item.getId())) {
                    ResumeExperienceDO resumeExperienceDO = copyExperience(item);
                    resumeExperienceDO.setDeleted(false);
                    resumeExperienceMapper.updateExperienceById(resumeExperienceDO.setType(ResumeType.WORK.getTypeCode()));
                } else {
                    ResumeExperienceDO workExperienceDO = copyExperience(item);
                    resumeExperienceMapper.insert(workExperienceDO.setResumeId(resumeId).setType(ResumeType.WORK.getTypeCode()));
                }
            });
        }
        //插入内容表
        //暂时注释编辑器内容
        /*if (ObjectUtil.isNotNull(reqVO.getContent())) {
            //更新简历内容表
            resumeContentService.deleteByResumeId(resumeId);
            resumeContentService.batchInsert(resumeId, reqVO.getContent());
        }*/

        ResumeDO resumeDO1 = resumeMapper.selectById(resumeId);
        // 还未完善简历时 判断是否完成全部简历信息，如果全部完善了增加积分
        if (!resumeDO1.getAllExperience()) {
            Boolean allExperience = getAllExperience(reqVO);
            if (!allExperience){
                return;
            }
            //简历全部完善后增加所有公会成长值
            addGrowthAfterResumeAllRecord(resumeDO1);
            //更新简历完善标志位
            ResumeDO updateDo = new ResumeDO().setId(resumeId).setAllExperience(true);
            resumeMapper.updateById(updateDo);
        }
    }

    /**
     * 判断简历信息是否全部完善
     *
     * @param reqVO
     * @return
     */
    @Nullable
    private Boolean getAllExperience(ResumeUpdateReqVO reqVO) {
        Boolean allExperience = false;
        // 工作、教育经历 有为空的必定为完善
        boolean b = ObjectUtil.isEmpty(reqVO.getWorkExperience()) || ObjectUtil.isEmpty(reqVO.getStudyExperience());
        if (b) {
            return false;
        }

        /* 下述信息不能为空
            介绍 introduce
            个人信息 resumeName sex birthday degree phoneNum email
            教育经历 school degree beginTime endTime major
            工作经历 company position beginTime endTime description
            个人技能 skills
         */
        ResumeBaseVO.Experience studyExp = reqVO.getStudyExperience().get(0);
        ResumeBaseVO.Experience workExp = reqVO.getWorkExperience().get(0);

        ResumeValidateVO baseInfoValidateVO = new ResumeValidateVO();
        ResumeValidateVO.StudyExperience studyExpValidateVO = new ResumeValidateVO.StudyExperience();
        ResumeValidateVO.WorkExperience workExpValidateVO = new ResumeValidateVO.WorkExperience();

        BeanUtils.copyProperties(reqVO, baseInfoValidateVO);
        BeanUtils.copyProperties(studyExp, studyExpValidateVO);
        BeanUtils.copyProperties(workExp, workExpValidateVO);


        boolean baseField = isFieldNotNull(baseInfoValidateVO);
        boolean studyExpField = isFieldNotNull(studyExpValidateVO);
        boolean workExpField = isFieldNotNull(workExpValidateVO);
        if (baseField && studyExpField && workExpField) {
            allExperience = true;
        }
        return allExperience;
    }

    /**
     * 反射判断对象属性是否为空
     * @param obj
     * @return
     */
    public static boolean isFieldNotNull(Object obj) {
        //拿到对象的所有字段
        Field[] fields = obj.getClass().getDeclaredFields();
        //标识
        boolean flag = true;
        //遍历所有字段
        for (Field field : fields) {
            try {
                //开启权限
                field.setAccessible(true);
                //判断是否有值
                if (ObjectUtil.isEmpty(field.get(obj))) {
                    flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭权限
                field.setAccessible(false);
            }
        }
        return flag;
    }


    /**
     * 简历全部完善后增加所有公会成长值
     *
     * @param resumeDO
     */
    private void addGrowthAfterResumeAllRecord(ResumeDO resumeDO) {
        //已完善了全部简历
        if (resumeDO.getAllExperience()) {
            return;
        }
        Long userId = getLoginUserId();
        List<Long> leagueIds = leagueApi.queryUserAllJoinLeagueId(userId);
        if (ObjectUtil.isNotEmpty(leagueIds)) {
            leagueIds.stream().forEach(leagueId -> {
                GrowthDTO growthDTO = new GrowthDTO(leagueId, userId);
                growthRuleFactory.getBy(GrowthType.PROFILE).growthValue(growthDTO, leagueApi::growthThenLevel);
            });
        }
    }


    @Override
    public ResumeRespVO getResume() {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        ResumeRespVO resumeRespVO = resumeMapper.selectOneByUserId(loginUserId);
        return setExperience(resumeRespVO);

    }

//    private Boolean isSameUser() {
//        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
//        ResumeRespVO resumeRespVO = resumeMapper.selectOneByUserId(loginUserId);
//        return true;
//
//    }

    private ResumeRespVO setExperience(ResumeRespVO resumeRespVO) {
        List<ResumeBaseVO.Experience> study = new ArrayList<>();
        List<ResumeBaseVO.Experience> work = new ArrayList<>();
        getExperienceByResumeId(resumeRespVO.getId(), ResumeType.STUDY.getTypeCode()).forEach(item -> {
            ResumeBaseVO.Experience experience = new ResumeBaseVO.Experience();
            BeanUtils.copyProperties(item, experience);
            if (ObjectUtil.isNotNull(item.getCertification())) {
                List<String> imageList = new ArrayList<>(Arrays.asList(item.getCertification().split("\\|")));
                experience.setCertification(imageList);
            } else {
                experience.setCertification(new ArrayList<>());
            }
            study.add(experience);
        });
        getExperienceByResumeId(resumeRespVO.getId(), ResumeType.WORK.getTypeCode()).forEach(item -> {
            ResumeBaseVO.Experience experience = new ResumeBaseVO.Experience();
            BeanUtils.copyProperties(item, experience);
            if (ObjectUtil.isNotNull(item.getCertification())) {
                List<String> imageList = new ArrayList<>(Arrays.asList(item.getCertification().split("\\|")));
                experience.setCertification(imageList);
            } else {
                experience.setCertification(new ArrayList<>());
            }
            work.add(experience);
        });
        //设置经历
        resumeRespVO.setStudyExperience(study);
        resumeRespVO.setWorkExperience(work);
        //设置简历内容
//        resumeRespVO.setContent(getContents(resumeRespVO.getId()));

        return resumeRespVO;
    }


    @Override
    public ResumeRespVO getResumeByMember(ResumeMemberReqVO reqVO) {
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        Long leagueId = reqVO.getLeagueId();
        Long resumeUserId = reqVO.getResumeUserId();

        // 如果查询者id和登陆者id一致，先校验公会id是否为空，校验是否在同一公会里
        if (Optional.ofNullable(loginUserId).map(i -> i.equals(resumeUserId)).orElse(false)
                || (!ObjectUtil.isNull(leagueId) && leagueApi.isLeagueMember(leagueId, loginUserId) && leagueApi.isLeagueMember(leagueId, resumeUserId))) {
            ResumeRespVO resumeRespVO = setExperience(resumeMapper.selectOneByUserId(resumeUserId));
            if (ObjectUtil.equal(resumeRespVO.getHide(), NumberEnum.ONE.getValue())) {
                resumeRespVO.setIsSameLeague(true)
                        .setResumeName(null)
                        .setSex(null)
                        .setBirthday(null)
                        .setDegree(null)
                        .setPhoneNum(null)
                        .setEmail(null);
            }
            return resumeRespVO;
        } else {
            ResumeRespVO resumeRespVO = setExperience(resumeMapper.selectOneByUserId(resumeUserId));
            if (ObjectUtil.equal(resumeRespVO.getHide(), NumberEnum.ONE.getValue())) {
                resumeRespVO.setIsSameLeague(false)
                        .setResumeName(null)
                        .setSex(null)
                        .setBirthday(null)
                        .setDegree(null)
                        .setPhoneNum(null)
                        .setEmail(null);
            }
            return resumeRespVO;
        }
    }

    private List<Content> getContents(Long resumeId) {
        List<ResumeContentDO> contentList = resumeContentService.getByResumeId(resumeId);
        List<Content> collect = contentList.stream()
                .map(ResumeContentDO::getContent)
                .map(ctn -> JSONUtil.toBean(ctn, Content.class))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        } else {
            return collect;
        }
    }

    private void validateResumeExists(Long id) {
        if (resumeMapper.selectById(id) == null) {
            throw exception(RESUME_NOT_EXISTS);
        }
    }

    private Boolean isResumeExperienceExists(Long id) {
        if (resumeExperienceMapper.selectById(id) == null) {
            return false;
        } else {
            return true;
        }
    }

    public List<ResumeExperienceDO> getExperienceByResumeId(Long resumeId, String type) {
        return resumeExperienceMapper.selectList(new LambdaQueryWrapper<ResumeExperienceDO>()
                .eq(ResumeExperienceDO::getResumeId, resumeId)
                .eq(ResumeExperienceDO::getType, type)
                .eq(ResumeExperienceDO::getDeleted, false)
                .orderByDesc(ResumeExperienceDO::getBeginTime));
    }

    public void deleteExperienceByResumeId(Long resumeId, String type) {
        resumeExperienceMapper.delete(new LambdaQueryWrapper<ResumeExperienceDO>()
                .eq(ResumeExperienceDO::getResumeId, resumeId)
                .eq(ResumeExperienceDO::getType, type));
    }

//    public void updateExperienceById(ResumeExperienceDO experienceDO) {
//        resumeExperienceMapper.updateById(experienceDO);
//    }

    private void batchInsertExperience(List<ResumeBaseVO.Experience> experiences, Long id, String type) {
        //插入经历信息
        experiences.forEach(experience -> {
            ResumeExperienceDO workExperienceDO = copyExperience(experience);
            resumeExperienceMapper.insert(workExperienceDO.setResumeId(id).setType(type));
        });
    }

    private ResumeExperienceDO copyExperience(ResumeBaseVO.Experience experience) {
        //插入经历信息

        ResumeExperienceDO workExperienceDO = new ResumeExperienceDO();
        BeanUtils.copyProperties(experience, workExperienceDO);
        if (ObjectUtil.isNotNull(experience.getCertification()) && experience.getCertification().size() != 0) {
            //图片数组转字符串
            List<String> detailImages = experience.getCertification();
            StringBuilder detailImagesBuilder = new StringBuilder();
            detailImages.forEach(n -> {
                detailImagesBuilder.append(n);
                if (!Objects.equals(detailImages.get(detailImages.size() - 1), n)) {
                    detailImagesBuilder.append('|');
                }
            });
            workExperienceDO.setCertification(detailImagesBuilder.toString());
        }
        return workExperienceDO;

    }


}
