package com.xctech.log.filter;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import com.xctech.log.core.constant.RequestMappingConstant;
import org.slf4j.MDC;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.UUID;

/**
 *  MDC拦截器
 */
public class CustomMDCInsertingServletFilter extends MDCInsertingServletFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            insertIntoMDC(request, response);
            chain.doFilter(request, response);
        } finally {
            clearMDC();
        }
    }

    void insertIntoMDC(ServletRequest servletRequest, ServletResponse servletResponse) throws UnsupportedEncodingException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            MDC.put(RequestMappingConstant.REQUEST_UNIQUE_CODE, UUID.randomUUID().toString());
            MDC.put(RequestMappingConstant.HTTP_ACCEPT, request.getHeader("Accept"));
            MDC.put(RequestMappingConstant.HTTP_CONTENT_LENGTH, String.valueOf(request.getContentLength()));
            MDC.put(RequestMappingConstant.HTTP_CONTENT_TYPE, request.getContentType());
            MDC.put(RequestMappingConstant.HTTP_ELAPSED_TIME, String.valueOf(request.getDateHeader("If-Modified-Since")));
            MDC.put(RequestMappingConstant.HTTP_LOCATION, String.valueOf(request.getLocale()));
            MDC.put(RequestMappingConstant.HTTP_METHOD, request.getMethod());
            MDC.put(RequestMappingConstant.HTTP_PATH, request.getRequestURI());
            if (Objects.nonNull(request.getQueryString()))
                MDC.put(RequestMappingConstant.HTTP_QUERY, URLDecoder.decode(request.getQueryString(), "utf-8"));
            MDC.put(RequestMappingConstant.HTTP_REMOTE_HOST, request.getRemoteHost());
            MDC.put(RequestMappingConstant.HTTP_SESSION, request.getSession().getId());
            MDC.put(RequestMappingConstant.HTTP_STATUS_CODE, String.valueOf(response.getStatus()));
            MDC.put(RequestMappingConstant.HTTP_USER_AGENT, request.getHeader("User-Agent"));
            MDC.put(RequestMappingConstant.HTTP_VERSION, request.getProtocol());
        }
    }

    void clearMDC() {
        MDC.remove(RequestMappingConstant.REQUEST_UNIQUE_CODE);
        MDC.remove(RequestMappingConstant.HTTP_ACCEPT);
        MDC.remove(RequestMappingConstant.HTTP_CONTENT_LENGTH);
        MDC.remove(RequestMappingConstant.HTTP_CONTENT_TYPE);
        MDC.remove(RequestMappingConstant.HTTP_ELAPSED_TIME);
        MDC.remove(RequestMappingConstant.HTTP_LOCATION);
        MDC.remove(RequestMappingConstant.HTTP_METHOD);
        MDC.remove(RequestMappingConstant.HTTP_PATH);
        MDC.remove(RequestMappingConstant.HTTP_QUERY);
        MDC.remove(RequestMappingConstant.HTTP_REMOTE_HOST);
        MDC.remove(RequestMappingConstant.HTTP_SESSION);
        MDC.remove(RequestMappingConstant.HTTP_STATUS_CODE);
        MDC.remove(RequestMappingConstant.HTTP_USER_AGENT);
        MDC.remove(RequestMappingConstant.HTTP_VERSION);
    }
}
