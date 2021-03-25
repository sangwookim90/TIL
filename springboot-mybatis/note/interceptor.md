# Interceptor
### 어떤 URI를 호출했을 때 해당 요청의 컨트롤러가 처리되기 전 또는 후에 작업을 하기 위해서 사용됨.
#### -> 필터와 인터셉터로 수행 가능
## Interceptor & Filter
- Filter는 DispatcherServlet 앞 단에서 동작하지만, Interceptor는 DispatcherServletdㅔ서 Controller로 가기 전에 동작한다.
- 필터는 J2EE 표준 스펙에 있는 서블릿의 기능 중 일부이지만 Interceptor는 스프링 프레임워케엇 제공되는 기능 --> Bean 사용 가능
- 문자열 인코딩과 같은 웹 전반에서 사용되는 기능 --> Filter
- 클라이언트의 요청과 관련이 있는 여러 가지 처리(로그인, 인증 ,권한 등) --> Interceptor