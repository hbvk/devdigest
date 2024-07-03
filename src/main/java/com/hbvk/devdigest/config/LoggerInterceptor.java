package com.hbvk.devdigest.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(LoggerInterceptor.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod method
                && method.getMethod().getAnnotation(Deprecated.class) != null) {
            logger.warn("Method {} is deprecated", method.getMethod().getName());
        }

    }
}
