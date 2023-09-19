package com.hisun.kugga.duke.user.cv.service.resumecontent;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hisun.kugga.duke.common.BusinessErrorCodeConstants;
import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.user.cv.dal.dataobject.resumecontent.ResumeContentDO;
import com.hisun.kugga.duke.user.cv.dal.mysql.resumecontent.ResumeContentMapper;
import com.hisun.kugga.duke.user.cv.utils.EditorUtils;
import com.hisun.kugga.framework.common.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * 个人简历内容 Service 实现类
 *
 * @author lzt
 */
@Service
@Validated
public class ResumeContentServiceImpl implements ResumeContentService {

    @Resource
    private ResumeContentMapper resumeContentMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchInsert(Long resumeId, List<Content> content) {
        if (CollUtil.isEmpty(content)) {
            ServiceException.throwServiceException(BusinessErrorCodeConstants.CONTENT_NOT_EXISTS);
        }
        content.stream().forEach(ctn -> {
            ResumeContentDO resumeContentDO = new ResumeContentDO();
            resumeContentDO.setResumeId(resumeId);
            resumeContentDO.setContent(JSONUtil.toJsonStr(ctn));
            resumeContentDO.setOriginalText(EditorUtils.parseContent(ctn));
            resumeContentMapper.insert(resumeContentDO);
        });
    }

    @Override
    public List<ResumeContentDO> getByResumeId(Long resumeId) {
        return resumeContentMapper.selectList(new LambdaQueryWrapper<ResumeContentDO>()
                .select(ResumeContentDO::getId, ResumeContentDO::getContent)
                .eq(ResumeContentDO::getResumeId, resumeId)
                .orderByAsc(ResumeContentDO::getId));
    }

    @Override
    public void deleteByResumeId(Long resumeId) {
        resumeContentMapper.delete(new LambdaQueryWrapper<ResumeContentDO>()
                .eq(ResumeContentDO::getResumeId, resumeId));
    }

}
