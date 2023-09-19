package com.hisun.kugga.module.infra.api.logger;

import com.hisun.kugga.framework.common.api.logger.ApiErrorLogApi;
import com.hisun.kugga.framework.common.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.hisun.kugga.module.infra.service.logger.ApiErrorLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 访问日志的 API 接口
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
    }

}
