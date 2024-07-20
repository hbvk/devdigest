# P4C developer digest sample application
Quick demo to illustrate use of `RequestLoggingFilter` to log API usage, `HandlerInterceptor` to log usage of deprecated API usage, centralized `@ExceptionHandler` to centralize exception handling. 
See the logging of the [test cases](src/test/java/com/hbvk/devdigest/controller/ControllerTest.java) for the results.

Inspired by a real world project, where the goal was to better understand usage of the (huge) REST API. Logging was sketchy, we wanted to improve that. 

Basic requirements:
- log all API calls as INFO,
- log deprecated API calls as WARN,
- log exceptions as ERROR
- and more, but that's beyond the scope of this short demo.

## Logging API usage
We used a ```LogFilter``` extending ```AbstractRequestLoggingFilter```:
```
public class LogFilter extends AbstractRequestLoggingFilter {
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        logger.info(message);
    }
	// same for afterRequest
}
```
Configure the ```LogFilter``` by adding a ```@Bean``` to a ```Configuration``` class:
```
    @Bean
    public LogFilter logFilter() {
        LogFilter logFilter = new LogFilter();
        logFilter.setBeforeMessagePrefix("BEFORE: ");
        logFilter.setAfterMessagePrefix("AFTER: ");
		// more config
        return logFilter;
    }

```

## Exception logging
We used a ```@ControllerAdvice``` annotation on a class that extends ```ResponseEntityExceptionHandler``` as a central repository for our ```@ExceptionHandler```s:
```
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRuntimeException(Exception e) {
        exceptionLogger.error("error: {}", e.getClass().getSimpleName(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	// more handlers
}
```

## Logging deprecated API usage
We log deprecated API calls using a ```HandlerInterceptor```:
```
public class LoggerInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        if (handler instanceof HandlerMethod method
                && method.getMethod().getAnnotation(Deprecated.class) != null) {
            logger.warn("Method {} is deprecated", method.getMethod().getName());
        }
    }
}
```
Actually, we could have done our API logging here too.

Register the ```LoggerInterceptor``` in a ```@Configuration``` class:
```
    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoggerInterceptor());
            }
        };
    }
```
