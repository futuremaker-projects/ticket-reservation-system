package com.reservation.ticket.infrastructure.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        try {
            log.info("REQUEST  [{}][{}]", request.getRequestURI(), request.getContentAsString());
            filterChain.doFilter(request, servletResponse);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}]", response.getStatus(), new String(response.getContentAsByteArray()));
            response.copyBodyToResponse();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
