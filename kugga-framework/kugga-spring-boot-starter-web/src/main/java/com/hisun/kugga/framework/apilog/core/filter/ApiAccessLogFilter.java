package com.hisun.kugga.framework.apilog.core.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.hisun.kugga.framework.apilog.core.service.ApiAccessLog;
import com.hisun.kugga.framework.apilog.core.service.ApiAccessLogFrameworkService;
import com.hisun.kugga.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hisun.kugga.framework.common.pojo.CommonResult;
import com.hisun.kugga.framework.common.util.date.DateUtils;
import com.hisun.kugga.framework.common.util.monitor.TracerUtils;
import com.hisun.kugga.framework.common.util.servlet.ServletUtils;
import com.hisun.kugga.framework.web.config.WebProperties;
import com.hisun.kugga.framework.web.core.filter.ApiRequestFilter;
import com.hisun.kugga.framework.web.core.filter.CacheResponseBodyWrapper;
import com.hisun.kugga.framework.web.core.util.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.hisun.kugga.framework.common.util.json.JsonUtils.toJsonString;

/**
 * API 访问日志 Filter
 *
 * @author 芋道源码
 */
@Slf4j
public class ApiAccessLogFilter extends ApiRequestFilter {

    private final String applicationName;

    private final ApiAccessLogFrameworkService apiAccessLogFrameworkService;

    public ApiAccessLogFilter(WebProperties webProperties, String applicationName, ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        super(webProperties);
        this.applicationName = applicationName;
        this.apiAccessLogFrameworkService = apiAccessLogFrameworkService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获得开始时间
        Date beginTim = new Date();
        // 提前获得参数，避免 XssFilter 过滤处理
        Map<String, String> queryString = ServletUtil.getParamMap(request);
        boolean isJsonRequest = ServletUtils.isJsonRequest(request);
        String requestBody = isJsonRequest ? ServletUtil.getBody(request) : null;
        try {
            // 继续过滤器
            filterChain.doFilter(request, response);
            // 正常执行，记录日志
            String responseBody = null;
            if (isJsonRequest && response instanceof CacheResponseBodyWrapper) {
                CacheResponseBodyWrapper cacheResponseBodyWrapper = (CacheResponseBodyWrapper) response;
                responseBody = new String(cacheResponseBodyWrapper.toByteArray());
            }
            createApiAccessLog(request, response, beginTim, queryString, requestBody, responseBody, null);
        } catch (Exception ex) {
            // 异常执行，记录日志
            createApiAccessLog(request, response, beginTim, queryString, requestBody, null, ex);
            throw ex;
        }
    }

    private void createApiAccessLog(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Date beginTime,
                                    Map<String, String> queryString,
                                    String requestBody,
                                    String responseBody,
                                    Exception ex) {
        ApiAccessLog accessLog = new ApiAccessLog();
        try {
            this.buildApiAccessLogDTO(accessLog, request, response, beginTime, queryString, requestBody, responseBody, ex);
            apiAccessLogFrameworkService.createApiAccessLog(accessLog);
        } catch (Throwable th) {
            log.error("[createApiAccessLog][url({}) log({}) 发生异常]", request.getRequestURI(), toJsonString(accessLog), th);
        }
    }

    private void buildApiAccessLogDTO(ApiAccessLog accessLog,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      Date beginTime,
                                      Map<String, String> queryString,
                                      String requestBody,
                                      String responseBody,
                                      Exception ex) {
        // 处理用户信息
        accessLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
        accessLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
        // 设置访问结果
        CommonResult<?> result = WebFrameworkUtils.getCommonResult(request);
        if (result != null) {
            accessLog.setResultCode(result.getCode());
            accessLog.setResultMsg(result.getMsg());
        } else if (ex != null) {
            accessLog.setResultCode(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode());
            accessLog.setResultMsg(ExceptionUtil.getRootCauseMessage(ex));
        } else {
            accessLog.setResultCode(0);
            accessLog.setResultMsg("");
        }

        // 设置其它字段
        accessLog.setTraceId(TracerUtils.getTraceId());
        accessLog.setApplicationName(applicationName);
        accessLog.setRequestUrl(request.getRequestURI());
        accessLog.setRequestParams(toJsonString(queryString));
        accessLog.setRequestBody(requestBody);
        accessLog.setResponseBody(responseBody);
        accessLog.setRequestMethod(request.getMethod());
        accessLog.setUserAgent(ServletUtils.getUserAgent(request));
        accessLog.setUserIp(ServletUtil.getClientIP(request));
        // 持续时间
        accessLog.setBeginTime(beginTime);
        accessLog.setEndTime(new Date());
        accessLog.setDuration((int) DateUtils.diff(accessLog.getEndTime(), accessLog.getBeginTime()));
    }

}
