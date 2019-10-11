/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.gmail.bishoybasily.yolo.aspects.aspect;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class TraceAspect {

//    @Pointcut("execution(@com.gmail.bishoybasily.yolo.aspects.annotation.DebugTrace * *(..))")
//    public void methodAnnotatedWithDebugTrace() {
//    }
//
//    @Pointcut("execution(@com.gmail.bishoybasily.yolo.aspects.annotation.DebugTrace *.new(..))")
//    public void constructorAnnotatedDebugTrace() {
//    }

    @Around("execution(@com.gmail.bishoybasily.yolo.aspects.annotation.DebugTrace * *(..))")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {


//        Reporter reporter = AsyncReporter.builder(OkHttpSender.create("127.0.0.1:9411/api/v1/spans")).build();
//        Brave brave = Brave.Builder("example").reporter(reporter).build();


        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        Long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();

        Log.d("@@", String.format("took %d millis ", (end - start)));

        Log.d("@@", "class: " + className + ", method: " + methodName);

        return result;
    }

}
