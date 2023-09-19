package com.hisun.kugga.duke.bos.service.resumeexperience;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceBatchUpdateReqVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperiencePageReqVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceRespVO;
import com.hisun.kugga.duke.bos.controller.admin.resumeexperience.vo.ResumeExperienceUpdateReqVO;
import com.hisun.kugga.duke.bos.dal.dataobject.resumeexperience.ResumeExperienceDO;
import com.hisun.kugga.duke.bos.dal.dataobject.resumeexperience.ResumeExperienceExDO;
import com.hisun.kugga.duke.bos.dal.mysql.resumeexperience.ExperienceMapper;
import com.hisun.kugga.duke.bos.enums.CertFlagEnum;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.framework.common.exception.util.ServiceExceptionUtil;
import com.hisun.kugga.framework.common.pojo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 个人简历经历 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ResumeExperienceServiceImpl implements ResumeExperienceService {

    @Resource
    private ExperienceMapper experienceMapper;


    @Override
    public void updateResumeExperience(ResumeExperienceUpdateReqVO updateReqVO) {
        // 校验存在或是否已审核
        this.validateResumeExperience(updateReqVO.getId());

        // 更新

        experienceMapper.updateById(new ResumeExperienceDO()
                .setId(updateReqVO.getId())
                .setCertFlag(updateReqVO.getCertFlag())
                .setSuggestion(updateReqVO.getSuggestion())

        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateResumeExperienceBatch(ResumeExperienceBatchUpdateReqVO updateReqVOs) {
        updateReqVOs.getMultipleSelection().forEach(this::updateResumeExperience);

    }


    private void validateResumeExperience(Long id) {
        ResumeExperienceDO resumeExperienceDO = experienceMapper.selectById(id);

        if (ObjectUtil.isNull(resumeExperienceDO)
                || StrUtil.equals(resumeExperienceDO.getCertFlag().getCode(), CertFlagEnum.cert_pass.getCode())
                || StrUtil.equals(resumeExperienceDO.getCertFlag().getCode(), CertFlagEnum.cert_reject.getCode())
        ) {
            throw ServiceExceptionUtil.exception(BusinessErrorCodeConstants.DB_UPDATE_FAILED);
        }
    }
//
//    @Override
//    public PageResult<ResumeExperienceDO> getResumeExperiencePage(ResumeExperiencePageReqVO pageReqVO) {
//        return resumeExperienceMapper.selectPage(pageReqVO);
//    }

    public List<ResumeExperienceDO> getExperienceByResumeId(Long resumeId, String type) {
        return experienceMapper.selectList(new LambdaQueryWrapper<ResumeExperienceDO>()
                .eq(ResumeExperienceDO::getResumeId, resumeId)
                .eq(ResumeExperienceDO::getType, type)
                .orderByDesc(ResumeExperienceDO::getBeginTime));
    }

    @Override
    public Map<String, String> selectAllCertType() {

        Map<String, String> certTypeMap = new HashMap<>();

        for (CertFlagEnum orderType : CertFlagEnum.values()) {
            certTypeMap.put(orderType.getCode(), orderType.getMsg());
        }
        return certTypeMap;

    }

    @Override
    public PageResult<ResumeExperienceRespVO> getResumeExperiencePage(ResumeExperiencePageReqVO reqVO) {

        Page page = new Page<>(reqVO.getPageNo(),
                reqVO.getPageSize());
        IPage<ResumeExperienceExDO> pageLeagues = experienceMapper.getResumeExperiencePage(page,
                reqVO.getResumeName(),
                reqVO.getCertFlag(),
                reqVO.getSchool(),
                reqVO.getCompany(),
                reqVO.getType());
        List<ResumeExperienceRespVO> respVOS = pageLeagues.getRecords().stream().map(item -> {
            ResumeExperienceRespVO experience = new ResumeExperienceRespVO();
            BeanUtils.copyProperties(item, experience);
            if (ObjectUtil.isNotNull(item.getCertification())) {
                List<String> imageList = new ArrayList<>(Arrays.asList(item.getCertification().split("\\|")));
                experience.setCertification(imageList);
            } else {
                experience.setCertification(new ArrayList<>());
            }
            return experience;
        }).collect(Collectors.toList());
        return new PageResult<>(respVOS, pageLeagues.getTotal());
    }

}
