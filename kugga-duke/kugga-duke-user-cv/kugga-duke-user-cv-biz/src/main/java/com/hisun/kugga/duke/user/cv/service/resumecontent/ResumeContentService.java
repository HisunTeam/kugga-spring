package com.hisun.kugga.duke.user.cv.service.resumecontent;

import com.hisun.kugga.duke.entity.Content;
import com.hisun.kugga.duke.user.cv.dal.dataobject.resumecontent.ResumeContentDO;

import java.util.List;


/**
 * 个人简历内容 Service 接口
 *
 * @author lzt
 */
public interface ResumeContentService {

    /**
     * 批量保存
     *
     * @param resumeId
     * @param content
     */
    void batchInsert(Long resumeId, List<Content> content);

    /**
     * 批量更新
     *
     * @param resumeId
     */
    List<ResumeContentDO> getByResumeId(Long resumeId);

    /**
     * 通过id删除
     *
     * @param resumeId
     */
    void deleteByResumeId(Long resumeId);
}
