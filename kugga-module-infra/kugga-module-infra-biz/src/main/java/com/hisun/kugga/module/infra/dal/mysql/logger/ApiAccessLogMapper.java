package com.hisun.kugga.module.infra.dal.mysql.logger;

import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.QueryWrapperX;
import com.hisun.kugga.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogExportReqVO;
import com.hisun.kugga.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import com.hisun.kugga.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * API 访问日志 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiAccessLogMapper extends BaseMapperX<ApiAccessLogDO> {

    default PageResult<ApiAccessLogDO> selectPage(ApiAccessLogPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<ApiAccessLogDO>()
                .eqIfPresent("user_id", reqVO.getUserId())
                .eqIfPresent("user_type", reqVO.getUserType())
                .eqIfPresent("application_name", reqVO.getApplicationName())
                .likeIfPresent("request_url", reqVO.getRequestUrl())
                .betweenIfPresent("begin_time", reqVO.getBeginBeginTime(), reqVO.getEndBeginTime())
                .geIfPresent("duration", reqVO.getDuration())
                .eqIfPresent("result_code", reqVO.getResultCode())
                .orderByDesc("id")
        );
    }

    default List<ApiAccessLogDO> selectList(ApiAccessLogExportReqVO reqVO) {
        return selectList(new QueryWrapperX<ApiAccessLogDO>()
                .eqIfPresent("user_id", reqVO.getUserId())
                .eqIfPresent("user_type", reqVO.getUserType())
                .eqIfPresent("application_name", reqVO.getApplicationName())
                .likeIfPresent("request_url", reqVO.getRequestUrl())
                .betweenIfPresent("begin_time", reqVO.getBeginBeginTime(), reqVO.getEndBeginTime())
                .geIfPresent("duration", reqVO.getDuration())
                .eqIfPresent("result_code", reqVO.getResultCode())
                .orderByDesc("id")
        );
    }

}
