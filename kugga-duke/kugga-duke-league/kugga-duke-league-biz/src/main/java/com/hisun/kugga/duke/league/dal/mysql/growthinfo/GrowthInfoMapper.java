package com.hisun.kugga.duke.league.dal.mysql.growthinfo;

import com.hisun.kugga.duke.dto.GrowthDTO;
import com.hisun.kugga.duke.league.dal.dataobject.growthinfo.GrowthInfoDO;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公会成员成长详情表 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface GrowthInfoMapper extends BaseMapperX<GrowthInfoDO> {

    /**
     * 保存成长详情
     * @param growthDTO
     */
    void save(@Param("growthDTO") GrowthDTO growthDTO);
}
