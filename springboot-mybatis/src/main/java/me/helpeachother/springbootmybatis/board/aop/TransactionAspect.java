package me.helpeachother.springbootmybatis.board.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

/**
 *  AOP를 이용해서 트랜잭션을 설정하면, 새로운 클래스나 메서드가 추가될 때 따로 어노테이션을 붙이지 않아도 자동적으로 트랜잭션 처리가 됨.
 *  어노테이션의 누락이나 잘못된 사용에 따른 문제 미연의 방지 가능
 *
 *  트랜잭션이 필요 없는 곳까지 적용되어서 성능에 영향을 미침
 */
@Configuration
public class TransactionAspect {
    private static final String AOP_TRANSACTION_METHOD_NAME = "*";
        private static final String AOP_TRANSACTION_EXPRESSION = "execution(* board..service.*Impl.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor transactionAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        // 트랜잭션 이름 설정
        transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
        // 트랜잭션 롤백 룰 설정, 예외가 일어나면 롤백이 수행되도록 지정함.
        transactionAttribute.setRollbackRules(Collections.singletonList(
                (new RollbackRuleAttribute(Exception.class))));
        source.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor transactionAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // AOP의 포인트컷 설정, 비즈니스 로직이 수행되는 모든 ServiceImpl 클래스의 모든 메서드 지정
        pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }

}
