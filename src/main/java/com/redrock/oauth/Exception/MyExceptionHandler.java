package com.redrock.oauth.Exception;

import com.redrock.oauth.entry.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(HttpServletRequest req, Exception ex) {
        Map map = new HashMap();
        map.put("code", 100);
        map.put("message", ex.getMessage());
        map.put("url", req.getRequestURL());
        map.put("params", req.getParameterMap());
        log.error("发生未处理的异常={}",ex.getMessage(),ex);
        return map;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CommonException.class)
    public Map myErrorHandler(HttpServletRequest req, CommonException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("message", ex.getMsg());
        map.put("url", req.getRequestURL());
        map.put("params", req.getParameterMap());
        return map;
    }


}
