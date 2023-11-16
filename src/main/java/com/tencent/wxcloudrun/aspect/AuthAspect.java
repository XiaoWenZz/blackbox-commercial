package com.tencent.wxcloudrun.aspect;

import com.tencent.wxcloudrun.annotation.Auth;
import com.tencent.wxcloudrun.common.CommonErrorCode;
import com.tencent.wxcloudrun.dto.SessionData;
import com.tencent.wxcloudrun.util.AssertUtil;
import com.tencent.wxcloudrun.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 权限控制
 * @author yannis
 * @version 2020/8/01 17:06
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    SessionUtils sessionUtil;

    @Around("@annotation(com.tencent.wxcloudrun.annotation.Auth)")
    public Object doAroundAuth(ProceedingJoinPoint joinPoint) throws Throwable {

        SessionData sessionData = sessionUtil.getSessionData();

        AssertUtil.isNotNull(sessionData, CommonErrorCode.INVALID_SESSION);

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        Auth annotation = method.getAnnotation(Auth.class);

        //log
        log.error("------------");
//        log.error("operator: " + sessionData.getId());
        log.error("operation: " + method.getName());

        return joinPoint.proceed();
    }


}
