# Spring Framework, SpringBoot

> Spring Framework 란?

- 가장 대중적인 Java web application 개발 프레임워크

### 의존성 주입(DI, Dependency Injection), 제어의 역전(IOC, Inversion Of Control)
- 결합도를 낮추는 방식으로 개발
- 단위테스트가 용이하여 높은 퀄리티의 개발 가능
### 관점 지향 프로그래밍 (AOP, Aspect Oriented Programming)

ex) security나 logging을 추가하고 싶을 때 AOP를 활용할 수 있다.

메소드가 끝날 때, 호출 직전, 메소드 리턴 직후, 또는 예외가 발생했을 때 등 활용 가능
Spring은 웹 어플리케이션을 개발하는데 결합도를 낮추는 방향의 개발방법을 제공한다.
웹 어플리케이션 개발은 Spring의 이러한 컨셉(Dispatcher Servlet, ModelAndView, View Resolver) 덕분에 쉬운 개발 가능 

> SpringBoot는 왜?

SpringBoot는 Spring Framework를 사용하는 프로젝트를 아주 간편하게 셋업할 수 있는 sub project이다. 독립 컨테이너에서 동작할 수 있어, Java만 설치되어 있으면 된다. 빌드 후 jar 파일이 생성되고, 별도의 서버 설치 없이 embeded tomcat이 자동 실행된다. Spring은 Transaction Manager, Hibernate Datasource, Entity Manager, Session Factory와 같은 설정을 하는데에 어려움이 많다.

### SpringBoot는 Spring의 이러한 설정에 관한 이슈를 어떻게 풀었을까?
- SpringBoot는 자동설정(AutoConfiguration)을 이용하였고 어플리케이션 개발에 필요한 모든 내부 dependancy를 관리한다.
- Spring의 jar파일이 classpath내에 있는 경우, Dispatcher Servlet으로 자동 구성한다.
- Hibernate의 jar파일이 classpath내에 있는 경우, datasource로 자동 구성한다.
  
- 사용하는 라이브러리의 의존성을 설정할 때, 서로 호환되는 버전을 따로 명시했어야하지만, SpringBoot Starter를 도입하여 복잡도를 줄임 

> SpringBoot가 정답인가?

#### 비교적 규모가 작은 형태의 어플리케이션을 실행시키기 위해 그보다 큰 WAS를 따로 설치하기엔 효율적이지 않다. --> SpringBoot
#### 비교적 규모가 큰 웹사이트 같은 경우, Spring MVC 형태로 만들어, WAS에서 관리되는 데이터 소스나 메시지 서비스를 이용할 수 있다.
