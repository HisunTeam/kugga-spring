package com.hisun.kugga.module.system.dal.mysql.errorcode;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.QueryWrapperX;
import com.hisun.kugga.module.system.controller.admin.errorcode.vo.ErrorCodeExportReqVO;
import com.hisun.kugga.module.system.controller.admin.errorcode.vo.ErrorCodePageReqVO;
import com.hisun.kugga.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface ErrorCodeMapper extends BaseMapperX<ErrorCodeDO> {

    default PageResult<ErrorCodeDO> selectPage(ErrorCodePageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<ErrorCodeDO>()
                .eqIfPresent("type", reqVO.getType())
                .likeIfPresent("application_name", reqVO.getApplicationName())
                .eqIfPresent("code", reqVO.getCode())
                .likeIfPresent("message", reqVO.getMessage())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("code"));
    }

    default List<ErrorCodeDO> selectList(ErrorCodeExportReqVO reqVO) {
        return selectList(new QueryWrapperX<ErrorCodeDO>()
                .eqIfPresent("type", reqVO.getType())
                .likeIfPresent("application_name", reqVO.getApplicationName())
                .eqIfPresent("code", reqVO.getCode())
                .likeIfPresent("message", reqVO.getMessage())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByAsc("application_name", "code"));
    }

    default List<ErrorCodeDO> selectListByCodes(Collection<Integer> codes) {
        return selectList(new QueryWrapper<ErrorCodeDO>().in("code", codes));
    }

    default ErrorCodeDO selectByCode(Integer code) {
        return selectOne(new QueryWrapper<ErrorCodeDO>().eq("code", code));
    }

    default List<ErrorCodeDO> selectListByApplicationNameAndUpdateTimeGt(String applicationName, Date minUpdateTime) {
        return selectList(new QueryWrapperX<ErrorCodeDO>().eq("application_name", applicationName)
                .gtIfPresent("update_time", minUpdateTime));
    }

}
