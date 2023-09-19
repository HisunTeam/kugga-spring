package com.hisun.kugga.duke.system.logger;

import com.hisun.kugga.framework.common.api.logger.ApiErrorLogApi;
import com.hisun.kugga.framework.common.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志的 API 接口
 * <p>
 * 错误的访问日志
 *
 * @author toi
 */
@Service
@Validated
public class DukeApiErrorLogApiImpl implements ApiErrorLogApi {

    private static final Logger logger = LoggerFactory.getLogger("errorPrinterLogger");

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        //不打印堆栈信息，堆栈信息可以根据日志号去其他日志查询
        createDTO.setExceptionStackTrace(null);
        logger.info(JsonUtils.toJsonString(createDTO));
    }

}
