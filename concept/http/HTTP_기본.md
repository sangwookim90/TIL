# HTTP (HyperText Transfer Protocol)
    -   HTML, Text
    -   Image, 음성, 영상, 파일
    -   JSON, XML (API)
    -   거의 모든 형태의 데이터 전송 가능
    -   서버간에 데이터를 주고 받을 때도 HTTP 사용
    
- 기반 프로토콜
    -   TCP: HTTP/1.1, HTTP/2
    -   UDP: HTTP/3
    -   현재 HTTP/1.1 주로 사용
        -   HTTP/2, HTTP/3도 점점 증가
        
### HTTP 특징
    -   클라이언트 서버 구조
    -   Stateless protocol, 비연결성
    -   HTTP 메시지
    -   단순함, 확장 가능

클라이언트-서버 구조
---
-   Request / Response 구조
-   클라이언트는 서버에 요청을 보내고, 응답을 대기
-   서버가 요청에 대한 결과를 만들어서 응답

무상태 프로토콜(Stateless)
---
-   서버가 클라이언트의 상태를 보존 X
-   장점: 서버 확장성 높음(스케일 아웃)
-   단점: 클라이언트가 추가 데이터 전송
    
```
Stateful, Stateless 차이

e.g. Stateful (
- 고객: 이 노트북 얼마인가요?
- 점원: 100만원입니다. (노트북 구매 의사 상태 정보 필요)

- 고객: 2개 구매하겠습니다.
- 점원: 200만원입니다. 카드? 현금? (노트북, 2개 구매 의사 상태 정보 필요)

- 고객: 신용카드
- 점원: 200만원 결제 완료 (노트북, 2개, 신용카드 구매 의사 상태 정보 필요)

e.g. Stateless
- 고객: 이 노트북 얼마인가요?
- 점원: 100만원입니다.

- 고객: '노트북' 2개 구매하겠습니다.
- 점원: 200만원입니다. 카드? 현금? 

- 고객: '노트북' '2개' 신용카드
- 점원: 200만원 결제 완료
```

-   Stateful, Stateless 차이
    -   Stateful: 중간에 점원이 바뀌면 안된다.
    -   Stateless: 중간에 점원이 바뀌어도 된다.
        -   갑자기 고객이 증가해도 점원을 대거 투입할 수 있다.
        -   갑자기 클라이언트 요청이 증가해도 서버를 대거 투입할 수 있다.
    -   Stateless는 응답 서버를 쉽게 바꿀 수 있다. --> 무한한 서버 증설 가능 
    
-   Stateless 실무 한계
    -   모든 것을 무상태로 설계할 수 있는 경우도 있고 없는 경우도 있다.
    -   Stateless
        - 예) 로그인이 필요 없는 단순한 서비스 소개 화면
    -   Stateful
        - 예) 로그인
    -   로그인한 사용자의 경우 로그인 했다는 상태를 서버에 유지
    -   일반적으로 브라우저 쿠키와 서버 세션등을 사용해서 상태 유지
    -   상태 유지는 최소한만 사용
    
## 비연결성
- HTTP는 기본이 연결을 유지하지 않는 모델
- 일반적으로 초 단위 이하의 빠른 속도로 응답
- 1시간동안 수천명이 서비스를 사용해도 실제 서버에서 동시에 처리하는 요청은 수십개 이하로 매우 작음
    - 예) 웹 브라우저에서 계속 연속해서 검색 버튼을 누르지 않는다.
- 서버 자원을 매우 효율적으로 사용할 수 있음

- 한계와 극복
    - TCP/IP 연결을 새로 맺어야 함 - 3 way handshake 시간 추가
    - 브라우저로 사이트를 요청하면 HTML뿐만 아니라 자바스크립트, css, 추가 이미지 등 수많은 자원이 함께 다운로드
    - 지금은 HTTP 지속 연결(Persistent Connections)로 문제 해결
    - HTTP/2, HTTP/3에서 더 많은 최적화
    
### 스테이스리스를 기억하자 (서버 개발자들이 어려워하는 업무)
- 정각에 딱 맞추어 발생하는 대용량 트래픽
- 예) 선착순 이벤트, 명절 KTX 예약, 학과 수업 등록
- 예) 저녁 6시 선착순 1000명 치킨 할인 이벤트 -> 수만명 동시 요청
##### -> 첫페이지는 정적페이지 순수 HTML을 뿌리고, 이벤트 참여버튼을 누르게 함.

## HTTP 메시지
<img src='https://github.com/sangwookim90/study/blob/main/concept/http/img/msg.png?raw=true' width="50%" height="70%">
<img src='https://github.com/sangwookim90/study/blob/main/concept/http/img/msg_form.png?raw=true' width="50%" height="70%">

### 시작라인
- 요청 메시지
```
ex)
GET /search?q=hello&hl=ko HTTP/1.1
Host: www.google.com
-----------------------------------------

- request-line = method SP request-target SP HTTP-version CRLF      (SP: 공백, CRLF: 줄바꿈)

- HTTP 메서드 (GET: 조회)
  - 종류: GET, POST, PUT, DELETE
  - 서버가 수행해야 할 동작 지정
    - GET: 조회
    - POST: 요청 내역 처리

- 요청 대상(/search?q=hello&hl=ko)
  - 절대경로[?query]
- HTTP Version


```

- 응답 메시지
```
ex)
HTTP/1.1 200 OK
Content-Type: text/html;charset=UTF-8
Content-Length: 3423

<html>
  <body>...</body>
</html>
-----------------------------------------

- status-line = HTTP-version SP status-code SP reason-phrase CRLF

- HTTP-version
- HTTP status-code: 요청 성공, 실패를 나타냄
  - 200: 성공
  - 400: 클라이언트 요청 오류
  - 500: 서버 내부 오류
- reason-phrase: 사람이 이해할 수 있는 짧은 상태 코드 설명 글


```

### HTTP 헤더
```
header-field = filed-name ":" OWS field-value OWS         (OWS: 띄어쓰기 허용)

용도
- HTTP 전송에 필요한 모든 부가 정보
ex) 메시지 바디의 내용, 메시지 바디의 크기, 압축, 인증, 요청 클라이언트(브라우저) 정보, 서버 애플리케이션 정보, 캐시 관리 정보
- 다양한 표준 헤더
- 필요시 임의의 헤더 추가 가능
ex) key: QETJ24nFvj29
```

### HTTP 바디
```
용도
- 실제 전송할 데이터
- HTML 문서, 이미지, 영상, JSON 등 byte로 표현할 수 있는 모든 데이터 전송 가능
```

<hr/>
HTTP는 단순하지만 확장 가능한 기술이다.



