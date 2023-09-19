package com.hisun.kugga.framework.web.core.filter;

import com.hisun.kugga.framework.web.core.util.WebFrameworkUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * web 请求logger日志打印 过滤器
 *
 * @author toi
 */
@AllArgsConstructor
public class LoggerFilter extends OncePerRequestFilter {

    protected static Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Long loginUserId = WebFrameworkUtils.getLoginUserId(request);
        logger.info("[u:{}][{}]URI:{}", loginUserId, method, uri);
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }

}
