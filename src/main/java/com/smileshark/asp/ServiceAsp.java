package com.smileshark.asp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.SQLException;


@Slf4j
@Aspect
@Component
public class ServiceAsp {

    @Pointcut("execution(String com.smileshark.service..*.*(..))")
    public void pointcut() {}
    @Pointcut("execution(String com.smileshark.service..*.insert*(..))")
    public void pointcutInsert() {}
    @Pointcut("execution(String com.smileshark.service..*.update*(..))")
    public void pointcutUpdate() {}
    @Pointcut("execution(String com.smileshark.service..*.delete*(..))")
    public void pointcutDelete() {}

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

    // 增删改查 要先手动关闭自动提交，如果没有报错，则提交事务，否则回滚事务
    @Around("pointcutInsert() || pointcutUpdate() || pointcutDelete()")
    public String aroundInsert(ProceedingJoinPoint pjp) throws SQLException {
        String proceed = JSONObject.toJSONString(Result.error());
        try {
            // 手动关闭自动提交
            if (pjp.getTarget() instanceof org.apache.ibatis.session.defaults.DefaultSqlSession) {
                ((org.apache.ibatis.session.defaults.DefaultSqlSession) pjp.getTarget()).getConnection().getAutoCommit();
                ((org.apache.ibatis.session.defaults.DefaultSqlSession) pjp.getTarget()).getConnection().setAutoCommit(false);
            }
            proceed = (String) pjp.proceed();
            // 提交事务
            if (pjp.getTarget() instanceof org.apache.ibatis.session.defaults.DefaultSqlSession) {
                ((org.apache.ibatis.session.defaults.DefaultSqlSession) pjp.getTarget()).commit();
            }
        } catch (Throwable e) {
            // 回滚事务
            if (pjp.getTarget() instanceof org.apache.ibatis.session.defaults.DefaultSqlSession) {
                ((org.apache.ibatis.session.defaults.DefaultSqlSession) pjp.getTarget()).rollback();
            }
            log.error(e.getMessage());
        } finally {
            // 还原自动提交
            if (pjp.getTarget() instanceof org.apache.ibatis.session.defaults.DefaultSqlSession) {
                ((org.apache.ibatis.session.defaults.DefaultSqlSession) pjp.getTarget()).getConnection().setAutoCommit(true);
            }
        }
        return proceed;
    }
}
