package com.hisun.kugga.duke.forum.service;

import com.hisun.kugga.duke.forum.vo.DistrictsReqVO;
import com.hisun.kugga.duke.forum.vo.DistrictsRespVO;
import com.hisun.kugga.duke.forum.vo.UpdateDistrictReqVO;

/**
 * @author zuocheng
 */
public interface DistrictService {
    /**
     * 创建分区
     *
     * @param reqVO
     */
    void updateDistrict(UpdateDistrictReqVO reqVO);

    /**
     * 根据论坛ID查询,论坛的分区列表
     *
     * @param reqVO
     * @return
     */
    DistrictsRespVO districts(DistrictsReqVO reqVO);
}
