package com.hisun.kugga.module.system.dal.mysql.dict;

import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hisun.kugga.module.system.controller.admin.dict.vo.type.DictTypeExportReqVO;
import com.hisun.kugga.module.system.controller.admin.dict.vo.type.DictTypePageReqVO;
import com.hisun.kugga.module.system.dal.dataobject.dict.DictTypeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictTypeMapper extends BaseMapperX<DictTypeDO> {

    default PageResult<DictTypeDO> selectPage(DictTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictTypeDO>()
                .likeIfPresent(DictTypeDO::getName, reqVO.getName())
                .likeIfPresent(DictTypeDO::getType, reqVO.getType())
                .eqIfPresent(DictTypeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DictTypeDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(DictTypeDO::getId));
    }

    default List<DictTypeDO> selectList(DictTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DictTypeDO>()
                .likeIfPresent(DictTypeDO::getName, reqVO.getName())
                .likeIfPresent(DictTypeDO::getType, reqVO.getType())
                .eqIfPresent(DictTypeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DictTypeDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime()));
    }

    default DictTypeDO selectByType(String type) {
        return selectOne(DictTypeDO::getType, type);
    }

    default DictTypeDO selectByName(String name) {
        return selectOne(DictTypeDO::getName, name);
    }

}
