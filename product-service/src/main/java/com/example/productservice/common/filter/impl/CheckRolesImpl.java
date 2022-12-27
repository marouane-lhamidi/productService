package com.example.productservice.common.filter.impl;

import com.example.productservice.common.config.Config;
import com.example.productservice.common.filter.CheckRolesInterface;
import com.example.productservice.common.filter.annotation.CheckRoles;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect

public class CheckRolesImpl implements CheckRolesInterface {

    @Autowired
    Config config;

    @Override
    public Object checkRoles(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        var checkRoles = signature.getMethod().getAnnotation(CheckRoles.class);

        config.checkroles(signature.getParameterNames(), joinPoint.getArgs(), checkRoles.roles());
        return joinPoint.proceed();
    }
}
