package com.hisun.kugga.duke.system.logger;

import com.hisun.kugga.duke.system.utils.LogStringUtils;
import com.hisun.kugga.framework.common.api.logger.ApiAccessLogApi;
import com.hisun.kugga.framework.common.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.hisun.kugga.framework.common.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志的 API 实现类
 * <p>
 * 打印请求的日志信息
 *
 * @author toi
 */
@Service
@Validated
public class DukeApiAccessLogApiImpl implements ApiAccessLogApi {

    private static final Logger logger = LoggerFactory.getLogger("accessPrinterLogger");

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        // 暂时只打印2500长度body大小，防止报文过大的问题
        createDTO.setRequestBody(LogStringUtils.shortBody(createDTO.getRequestBody()));
        createDTO.setResponseBody(LogStringUtils.shortBody(createDTO.getResponseBody()));
        logger.info(JsonUtils.toJsonString(createDTO));
    }

}
