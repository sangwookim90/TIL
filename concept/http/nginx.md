# nginx

#### nginx는 Apache보다 동작이 단순하고, 전달자 역할만 하기 때문에 동시접속 처리에 특화되어 있다.
#### 동시접속자(약 700명) 이상이라면 서버를 증설하거나 nginx 환경을 권장한다고 한다. 

## nginx의 역할
1. 정적 파일을 처리하는 HTTP 서버로서의 역할
    - 웹서버의 역할은 HTML, CSS, Javascript, 이미지와 같은 정보를 웹브라우에 전송하는 역할
2. application 서버에 요청을 보내는 Reverse Proxy로서의 역할
    - 클라이언트는 가짜 서버에 요청하면, 프록시 서버(nginx)가 reverse 서버(application server)로부터 데이터를 가져오는 역할
    - 웹어플리케이션 서버에 리버스 프록시를 두는 이유
        - 요청에 대한 버퍼링이 존재하기 때문
        - 클라이언트가 직접 어플리케이션 서버에 요청하는 경우, 프로세스 1개가 응답 대기 상태가 되어야만한다.
        - 따라서 프록시 서버를 둠으로써 요청을 배분하는 역할을 한다.
            - nginx.conf 파일에서 *location* 지시어를 사용하여 요청을 배분 
        
## 특징
- 비동기 Event-Driven 기반 구조. 
    - 동기(Synchronous) : A가 B에게 데이터를 요청했을 때, 이 요청에 따른 응답을 주어야만 A가 다시 작업 처리가 가능 (하나의 요청, 하나의 작업에 충실)
    - 비동기(Asynchronous) : A의 요청을 B가 즉시 주지 않아도, A의 유휴시간으로 또 다른 작업 처리가 가능한 방식
- 다수의 연결을 효과적으로 처리가능. 
- 대부분의 코어 모듈이 Apache보다 적은 리소스로 더 빠르게 동작가능
- 더 작은 쓰레드로 클라이언트의 요청들을 처리가능

​ 