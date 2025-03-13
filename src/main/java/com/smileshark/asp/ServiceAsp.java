package com.smileshark.asp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class ServiceAsp {

    @Pointcut("execution(String com.smileshark.service..*.*(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public String around(ProceedingJoinPoint pjp) {
        String proceed = JSONObject.toJSONString(Result.error());
        try {
            proceed = (String)pjp.proceed();
            log.info("返回数据：{}", proceed);
        } catch (Throwable e) {
            log.error(e.getMessage());
        }
        return proceed;
    }
}
