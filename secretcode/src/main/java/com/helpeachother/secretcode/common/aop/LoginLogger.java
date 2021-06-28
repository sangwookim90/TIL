package com.helpeachother.secretcode.common.aop;

import com.helpeachother.secretcode.log.service.LogService;
import com.helpeachother.secretcode.user.entity.User;
import com.helpeachother.secretcode.user.model.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LoginLogger {

    private final LogService logService;

    @Around("execution(* com.helpeachother.secretcode..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint)  throws Throwable {

        log.info("#############################");
        log.info("#############################");
        log.info("서비스 호출 전!!!!");

        Object result = joinPoint.proceed();

        if("login".equals(joinPoint.getSignature().getName())) {
            StringBuilder sb = new StringBuilder();

            sb.append("\n");
            sb.append("함수명:" + joinPoint.getSignature().getDeclaringType() +", " + joinPoint.getSignature().getName());
            sb.append("\n");
            sb.append("매개변수:");

            Object[] args = joinPoint.getArgs();
            if(args != null && args.length > 0) {
                for(Object x : args) {
                    // x가 UserLogin class인 경우만..
                    if( x instanceof UserLogin) {
                        sb.append(((UserLogin)x).toString());

                        sb.append("\n");
                        sb.append("리턴값: " + ((User)result).toString());
                    }
                }
            }

            log.info(sb.toString());
        }





        return result;
    }
}
