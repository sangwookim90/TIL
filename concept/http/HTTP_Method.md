# HTTP Method

## HTTP API 설계 

#### URI 설계 

#### ex) 회원 정보 관리 API 개발

가장 중요한 것은 **리소스 식별** 이다. 리소스와 행위(method)를 분리한다.

- 리소스의 의미
    - 회원을 등록하고 수정하고 조회하는 것이 리소스가 아니다!
    - 회원을 등록해라 -> **회원** 이 리소스이다.
- 리소스 식별하는 법
    - 회원을 등록하고 수정하고 조회하는 것을 모두 배제하고 회원 리소스를 URI에 매핑한다.

#### URI 계층 구조 활용하여 설계
- 회원 목록 조회 /members
- 회원 조회 /members/{id}
- 회원 등록 /members/{id}
- 회원 수정 /members/{id}
- 회원 삭제 /members/{id}

## HTTP Method 종류

#### 주요 메서드

- GET: 리소스 조회
- POST: 요청 데이터 처리, 주로 등록에 사용
- PUT: 리소스를 대체, 해당 리소스가 없으면 생성
- PATCH: 리소스 부분 변경
- DELETE: 리소스 삭제

#### 기타 메서드

- HEAD: GET과 동일하지만 메시지 부분을 제외하고, 상태 줄과 헤더만 반환
- OPTIONS: 대상 리소스에 대한 통신 가능 옵션(메소드)을 설명(주로 CORS에서 사용)
- CONNECT: 대상 자원으로 식별되는 서버에 대한 터널을 설정
- TRACE: 대상 리소스에 대한 경로를 따라 메시지 루프백 테스트를 수행

---
### GET

- GET은 말 그대로, 리소스를 조회하는 메소드이다. 
- 서버에 전달하고 싶은 데이터는 query(쿼리 파라미터, 쿼리 스트링)를 통해서 전달
- 메시지 바디를 사용해서 데이터를 전달할 수 있지만, 지원하지 않는 곳이 많아서 권장하지 않음

> 리소스 조회

1) 메시지 전달 (Client)

```http
GET /members/100 HTTP/1.1
Host: localhost:8080
```

2. 서버 도착 (DB 조회)
3. 응답 데이터 (Server)

```http
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 34

{
  "username": "sangwoo",
  "age": 20
}
```

---

### Post

- 요청 데이터 처리
- **메시지 바디**를 통해 서버로 요청 데이터 전달
- 서버는 요청 데이터 처리
  - 메시지 바디를 통해 들어온 데이터를 처리하는 모든 기능을 수행한다.
- 주로 전달된 데이터로 신규 리소스 등록, 프로세스 처리에 사용

> 리소스 등록

1. 메시지 전달 (Client)

```http
POST /members HTTP/1.1
Content-Type: application/json

{
	"username": "sangwoo",
	"age": 20
}
```

2. 신규 리소스 생성 (DB Insert)
3. 응답 데이터 전달 (Server)

``` http
HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 34
Location: /members/100

{
	"username": "sangwoo",
	"age": 20
}
```

200 OK로 응답해도 상관 없다. 201 Created로 응답하는 경우, Location 정보를 함께 보내준다.

> 요청 데이터를 어떻게 처리한다는 뜻일까?

- 스펙: POST 메서드는 **대상 리소스가 리소스의 고유한 의미 체계에 따라 요청에 포함된 표현을 처리하도록 요청**한다.
- 예시
  - HTML 양식에 입력된 필드와 같은 데이터 블록을 데이터 처리 프로세스에 제공
    - ex) HTML form 에 입력한 정보로 회원 가입, 주문 등에서 사용
  - 게시판, 뉴스 그룹, 메일링 리스트, 블로그 또는 유사한 기사 그룹에 메시지 게시
    - ex) 게시판 글쓰기, 댓글 달기
  - 서버가 아직 식별하지 않은 새 리소스 생성
    - ex) 신규 주문 생성
  - 기존 자원에 데이터 추가
    - ex) 한 문서 끝에 내용 추가하기
- **정리: 이 리소스 URI에 POST 요청이 오면 데이터를 어떻게 처리할지 리소스마다 따로 정해야 함 

### 정리

1. 새 리소스 생성 (등록)
   - 서버가 아직 식별하지 않은 새 리소스 생성
2. 요청 데이터 처리
   - 단순히 데이터를 생성하거나, 변경하는 것을 넘어서 프로세스를 처리해야 하는 경우
   - ex) 주문에서 (결제완료 -> 배달 시작 -> 배달 완료) 처럼 단순히 값 변경을 넘어 프로세스의 상태가 변경되는 경우
   - POST의 결과로 새로운 리소스가 생성되지 않을 수도 있음
   - ex) POST /orders/{orderId}/start-delivery (컨트롤 URI, 리소스만을 사용하여 URI를 설계할 수 없다. 동사 사용)
3. 다른 메서드로 처리하기 애매한 경우
   - ex) JSON 으로 조회 데이터를 넘겨야 하는데, GET 메서드를 사용하기 어려운 경우
   - 애매하면 POST

---

### PUT

- 리소스를 대체 (덮어쓰기, 수정)
  - 리소스가 있으면 대체
  - 리소스가 없으면 생성
- **클라이언트가 리소스를 식별**
  - 클라이언트가 리소스 위치를 알고 URI 지정
  - POST와의 차이점

> 리소스가 있는 경우 (모든 필드 전달)

1. 메시지 전달 (Client)

```http
PUT /members/100 HTTP/1.1
Content-Type: application/json

{
	"username": "sangwooKim",
	"age": 32
}
```

2. 리소스 대체 (GET members/100)
```json
{
	"username": "sangwooKim"
  "age": 32
}
```

> 리소스가 있는 경우 (일부 필드만 전달)

- 리소스를 완전히 대체하기 때문에 전달한 필드만 저장된다.

1. 메시지 전달 (Client)

```http
PUT /members/100 HTTP/1.1
Content-Type: application/json

{
	"age": 32
}
```

2. 결과 (GET members/100)

```json
{
  "age": 32
}
```

---

### PATCH

- 리소스 부분 변경

> 리소스 부분 변경

1. 메시지 전달 (Client)

```http
PATCH /members/100 HTTP/1.1
Content-Type: application/json

{
	"age": 32
}
```
2. 결과 (GET members/100)

```json
{
	"username": "sangwoo"
  "age": 32
}
```

---

### DELETE

- 리소스 제거

> 리소스 제거
1. 메시지 전달 (Client)

```http
DELETE /members/100 HTTP/1.1
Host: localhost:8080
```

2. 리소스 삭제 (Server)

## HTTP 메서드의 속성

- 안전 (Safe Methods)
- 멱등 (Idempotent Methods)
- 캐시가능 (Cacheable Methods)

### 안전 (Safe Methods)

- 호출해도 리소스를 변경하지 않는다.

### 멱등 (Indempotent Methods)

- f(f(x)) = f(x)
- 한 번 호출하든, 두 번 호출하든 100번 호출하든 결과가 똑같다.
- 멱등 메서드
  - GET: 매번 같은 결과가 조회된다.
  - PUT: 결과를 대체한다. 따라서 같은 요청을 여러번해도 최종 결과는 같다.
  - DELETE: 결과를 삭제한다. 같은 요청을 여러번해도 삭제된 결과는 같다.
  - ~~POST~~: 두 번 호출하면 같은 결제가 중복해서 발생할 수 있다.
- 활용
  - 자동 복구 메커니즘
  - 서버가 TIMEOUT 등으로 정상 응답을 못주었을 때, 클라이언트가 같은 요청을 다시해도 되는가? 의 판단 근거
  - 재요청 중간에 다른 곳에서 리소스를 변경해버리면?
    - 사용자1: GET -> username:A, age:20
    - 사용자2: PUT -> username:A, age:30
    - 사용자1: GET -> username:A, age:30 -> 사용자2의 영향으로 바뀐 데이터 조회
    - **멱등은 외부 요인으로 중간에 리소스가 변경되는 것까지 고려하지 않는다.**

### 캐시가능 (Cacheable Methods)

- 응답 결과 리소스를 캐시해서 사용해도 되는가?
- GET, HEAD, POST, PATCH 캐시 가능
- 실제로는 GET, HEAD 정도만 캐시로 사용
  - POST, PATCH는 본문 내용까지 캐시 키로 고려해야하는데, 구현이 쉽지 않음

## HTTP Method 활용

- 클라이언트에서 서버로 데이터 전송
- HTTP API 설계 예시

### 클라이언트에서 서버로 데이터 전송

- 쿼리 파라미터를 통한 데이터 전송
  - GET
  - 주로 정렬 필터 (검색어)
- 메시지 바디를 통한 데이터 전송
  - POST, PUT, PATCH
  - 회원 가입, 상품 주문, 리소스 등록, 리소스 변경

> 4가지 상황

1. 정적 데이터 조회

   - 이미지, 정적 텍스트 문서
   - 쿼리 파라미터 없이 리소스 경로로 단순하게 조회 가능

2. 동적 데이터 조회

   - 검색, 게시판 목록에서 정렬 필터(검색어)
   - 조회 조건을 줄여주는 **필터**, 조회 결과를 정렬하는 **조건**에 주로 사용
   - 쿼리 파라미터 사용해서 데이터 전달

3. HTML Form 을 통한 데이터 전송
   - HTML Form submit 시 POST 전송
   
     - 회원 가입, 상품 주문, 데이터 변경
   
   - Content-Type: application/x-www-form-urlencoded 사용
     
     - Form 의 내용을 메시지 바디를 통해서 전송 (key=value, 쿼리 파라미터 형식)
     - Form 태그의 method가 POST인 경우
     
     ```http
     POST /save HTTP/1.1
     Host: localhost:8080
     Content-Type: application/x-www-form-urlencoded
     
   	username=kim&age=20
     ```
     
     - Form 태그의 method가 GET인 경우 (GET은 조회에서만 사용!)
     ```http
     GET /save?username=kim&age=20 HTTP/1.1
     Host: localhost:8080
     Content-Type: application/x-www-form-urlencoded
     ```
     
   - Content-Type: multipart/form-data
     - 파일 업로드 같은 바이너리 데이터 전송 시 사용
     - 다른 종류의 여러 파일과 폼의 내용 함께 전송 가능
     
   - HTML Form 전송은 **GET, POST만 지원**
   
4. HTTP API 를 통한 데이터 전송

   - 회원 가입, 상품 주문, 데이터 변경
   - 서버 to 서버, 앱 클라이언트, 웹 클라이언트(Ajax)

## HTTP API 설계 예시

- HTTP API - 컬렉션
  - POST 기반 등록
  - 예) 회원 관리 API 제공
- HTTP API - 스토어
  - PUT 기반 등록
  - 예) 정적 컨텐츠 관리, 원격 파일 관리
- HTML FORM 사용
  - 웹 페이지 회원 관리
  - GET, POST 만 지원

> 회원 관리 시스템

### API 설계 - POST 기반 등록

- 회원 목록 - GET /members 
- 회원 등록 - POST /members
- 회원 조회 - GET /members/{id}
- 회원 수정 - PATCH, PUT, POST /members/{id}
- 회원 삭제 - DELETE /members/{id}

### POST - 신규 자원 등록 특징

- 클라이언트는 등록될 리소스의 URI를 모른다.
  - 회원 등록: POST /members 
- 서버가 새로 등록된 리소스 URI를 생성해준다.
  - HTTP/1.1 201 Created
    Location: /members/100
- 컬렉션 (Collection)
  - 서버가 관리하는 리소스 디렉토리
  - 서버가 리소스의 URI를 생성하고 관리
  - 여기서 컬렉션은 /members

> 파일 관리 시스템

### API 설계 - PUT 기반 등록

- 파일 목록 - GET /files

- 파일 조회 - GET /files/{filename}

- 파일 등록 - PUT /files/{filename}

- 파일 삭제 - DELETE /files/{filename}

- 파일 대량 등록 - POST /files

  PUT으로 파일을 등록하기 때문에 POST에 다른 의미를 둘 수 있다.

### PUT - 신규 자원 특징

- 클라이언트가 리소스 URI를 알고 있어야 한다.
  - 파일 등록 PUT /files/{filename}
  - PUT /files/star.jpg
- 클라이언트가 직접 리소스의 URI를 지정한다.
- 스토어(Store)
  - 클라이언트가 관리하는 리소스 저장소
  - 클라이언트가 리소스의 URI를 알고 관리
  - 여기서 스토어는 /files

> HTML FORM 사용

- HTML FORM 은 GET, POST 만 지원
- Ajax 같은 기술을 사용해서 해결 가능
- 컨트롤 URI
  - GET, POST만 지원하므로 제약이 있음
  - 동사로된 리소스 경로 사용
  - POST의 /new, /edit, /delete 등 사용
  - HTTP Method로 해결하기 애매한 경우 사용 (HTTP API 포함)



---

### 참고하면 좋은 URI 설계 개념

- 문서 (document)
  - 단일 개념 (파일 하나, 객체 인스턴스, 데이터베이스 row)
  - 예) /members/100, files/star.jpg
- 컬렉션 (collection)
  - 서버가 관리하는 리소스 디렉토리
  - 서버가 리소스의 URI를 생성하고 관리
  - ex) /members
- 스토어 (store)
  - 클라이언트가 관리하는 자원 저장소
  - 클라이언트가 리소스의 URI를 알고 관리
  - ex) files
- 컨트롤러(controller), 컨트롤 URI
  - 문서, 컬렉션, 스토어로 해결하기 어려운 추가 프로세스 실행
  - 동사를 직접 사용
  - 예) /members/{id}/delete