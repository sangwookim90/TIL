# AOP (Aspect Oriented Programming)
##### 애플리케이션 전반에서 사용되는 기능을 여러 코드에 쉽게 적용할 수 있도록 함
##### ex) 로그, 권한 체크, 인증, 예외처리 등
--> 공통된 기능 재사

## 용어 정리
- 관점(Aspect) : 공통적으로 적용될 기능 의미, 한 개 이상의 포인트컷과 어드바이스의 조합으로 만들어진다.
- 어드바이스(Advice) : 관점의 구현체로 조인포인트에 삽입되어 동작하는 것을 의미, 동작하는 시점에 따라 다섯 종류로 구분
- 조인포인트(Joinpoint) : 어드바이스를 적용하는 지점 의미, 항상 메서드 실행 단계만 가능
- 포인트컷(Pointcut) : 어드바이스를 적용할 조인포인트를 선별하는 과정이나 그 기능을 정의한 모듈을 의미, 정규표현식이나 AspectJ의 문법을 이용해서 어떤 조인포인트를 사용할 것인지 결정.
- 타깃(Target) : 어드바이스를 받을 대상을 의미
- 위빙(Weaving) : 어드바이스를 적용하는 것을 의미. 즉, 공통 코드를 원하는 대상에 삽입하는 것을 뜻함.

## AOP의 주요 개념
### 어드바이스
- Before Advice / @Before / 대상 메서드가 실행되기 전에 적용할 어드바이스를 정의
- After returning Advice / @AfterReturning / 대상 메서드가 성공적으로 실해되고 결과값을 반환한 후 적용할 어드바이스를 정의
- After throwing Advice / @AfterThrowing / 대상 메서드에서 예외가 발생했을 때 적용할 어드바이스를 정의. try/catch의 catch와 비슷한 역할
- After Advice / @After / 대상 메서드의 정상적인 수행 부와 상관없이 무조건 실행되는 어드바이스를 정의. 즉, 예외가 발생하더라도 실행되기 때문에 finally와 비슷한 역할
- Around Advice / @Around / 대상 메서드의 호출 전후, 예외 발생 등 모든 시점에 적용할 수 있는 어드바이스. 가장 범용적으로 사용 가능

### 포인트컷
- execution / 접근 제어자, 리턴 타입, 타입 패턴, 메서드, 파라미터 타입, 예외 타입 등 조합해서 가장 정교한 포인트컷 생성 가능
#### 포인트컷 표현식 예시
- execution(void select*(..)) : 리턴 타입이 void이고 메서드 이름이 select로 시작하며, 파라미터가 0개 이상인 모든 메서드가 호출 될 때
- execution(* board.controller.*()) : board.controller 패키지 밑에 파라미터가 없는 모든 메서드가 호출 될 때
- execution(* board.controller.*(..)) : board.controller 패키지 밑에 파라미터 0개 이상인 모든 메서드가 호출 될 때 
- execution(* board..select*(*)) : board 패키지의 모든 하위 패키지에 있는 select로 시작하고 파라미터가 1개인 모든 메서드가 호출 될 때
- execution(* board..select*(\*,\*)) : board 패키지의 모든 하위 패키지에 있는 select로 시작하고 파라미터가 2개인 모든 메서드가 호출 될 때

- within(board.service.boardServiceImpl) : board.service 패키지 밑에 있는 는 boardServiceImpl 클래스의 메서드가 호출될 때
- within(board.service.*ServiceImpl) : board.service 패키지 밑에 있는 ServiceImpl이라는 이름으로 끝나는 메서드가 호출될 때 
