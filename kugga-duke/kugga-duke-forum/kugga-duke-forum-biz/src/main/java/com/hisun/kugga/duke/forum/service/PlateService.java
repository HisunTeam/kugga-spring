package com.hisun.kugga.duke.forum.service;

import com.hisun.kugga.duke.forum.vo.PagePlateReqVO;
import com.hisun.kugga.duke.forum.vo.PlateReqVO;
import com.hisun.kugga.duke.forum.vo.PlateVO;
import com.hisun.kugga.duke.forum.vo.SimplePlateVO;
import com.hisun.kugga.framework.common.pojo.PageResult;

/**
 * 论坛板块服务
 *
 * @author zuocheng
 */
public interface PlateService {
    /**
     * 分页查询板块列表
     *
     * @param reqVO
     * @return
     */
    PageResult<PlateVO> pageList(PagePlateReqVO reqVO);

    /**
     * 查询指定版块信息(目前只有匿名版块,无需传参)
     *
     * @param reqVO
     * @return
     */
    SimplePlateVO simple(PlateReqVO reqVO);
}
