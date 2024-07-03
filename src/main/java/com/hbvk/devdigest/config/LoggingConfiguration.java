package com.hbvk.devdigest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfiguration {

    @Bean
    public LogFilter logFilter() {
        LogFilter logFilter = new LogFilter();
        logFilter.setBeforeMessagePrefix("BEFORE: ");
        logFilter.setAfterMessagePrefix("AFTER: ");
        logFilter.setIncludeClientInfo(true);
        logFilter.setIncludeHeaders(true);
        logFilter.setIncludeQueryString(true);
        return logFilter;
    }

    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoggerInterceptor());
            }
        };
    }
}
