package com.reservation.ticket.infrastructure.config.interceptor;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class TokenVerificationInterceptor implements HandlerInterceptor {

    private final QueueService queueService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            // throw 시 500 에러남
//             throw new ApplicationException(ErrorCode.UNAUTHORIZED, "token is required");
            return false;
        }
        Queue queue = queueService.getQueueByToken(token);
        queue.verifyQueueStatus();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

}
