package com.hisun.kugga.module.infra.dal.mysql.logger;

import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.framework.mybatis.core.mapper.BaseMapperX;
import com.hisun.kugga.framework.mybatis.core.query.QueryWrapperX;
import com.hisun.kugga.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExportReqVO;
import com.hisun.kugga.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import com.hisun.kugga.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * API 错误日志 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiErrorLogMapper extends BaseMapperX<ApiErrorLogDO> {

    default PageResult<ApiErrorLogDO> selectPage(ApiErrorLogPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<ApiErrorLogDO>()
                .eqIfPresent("user_id", reqVO.getUserId())
                .eqIfPresent("user_type", reqVO.getUserType())
                .eqIfPresent("application_name", reqVO.getApplicationName())
                .likeIfPresent("request_url", reqVO.getRequestUrl())
                .betweenIfPresent("exception_time", reqVO.getBeginExceptionTime(), reqVO.getEndExceptionTime())
                .eqIfPresent("process_status", reqVO.getProcessStatus())
                .orderByDesc("id")
        );
    }

    default List<ApiErrorLogDO> selectList(ApiErrorLogExportReqVO reqVO) {
        return selectList(new QueryWrapperX<ApiErrorLogDO>()
                .eqIfPresent("user_id", reqVO.getUserId())
                .eqIfPresent("user_type", reqVO.getUserType())
                .eqIfPresent("application_name", reqVO.getApplicationName())
                .likeIfPresent("request_url", reqVO.getRequestUrl())
                .betweenIfPresent("exception_time", reqVO.getBeginExceptionTime(), reqVO.getEndExceptionTime())
                .eqIfPresent("process_status", reqVO.getProcessStatus())
                .orderByDesc("id")
        );
    }

}
