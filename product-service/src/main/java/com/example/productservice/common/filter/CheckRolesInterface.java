package com.example.productservice.common.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public interface CheckRolesInterface {

    @Around("@annotation(com.example.productservice.common.filter.annotation.CheckRoles)")
    Object checkRoles(ProceedingJoinPoint joinPoint) throws Throwable;


}