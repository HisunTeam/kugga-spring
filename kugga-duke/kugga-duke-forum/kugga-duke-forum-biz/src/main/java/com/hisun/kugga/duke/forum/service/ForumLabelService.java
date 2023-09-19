package com.hisun.kugga.duke.forum.service;

import com.hisun.kugga.duke.forum.vo.HotLabelsRespVO;
import com.hisun.kugga.duke.forum.vo.LabelVO;
import com.hisun.kugga.duke.forum.vo.VagueLabelsReqVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

public interface ForumLabelService {
    /**
     * 查询热闹标签列表
     * @return
     */
    HotLabelsRespVO hotLabels();

    /**
     * 模糊匹配标签
     * @param reqVO
     * @return
     */
    PageResult<LabelVO> vagueLabels(VagueLabelsReqVO reqVO);
}
