# RESTful

## REST란?
### REpresentational State Transfer의 약자
### 잘 표현된 HTTP URI로 리소스를 정의하고, HTTP 메서드로 리소스에 대한 행위를 정의한다. 
### 리소스는 JSON, XML과 같은 여러 가지 언어로 표현할 수 있다.
---
### 리소스
#### 서비스를 제공하는 시스템의 자원을 의미하는 것으로 URI(Uniform Resource Identifier)로 정의
#### REST API의 URI는 리소스의 자원을 표현해야 한다.
#### 규칙
- URI는 명사를 사용 ex) GET /member 
- 슬래시(/)로 계층 관계 나타냄 ex) GET /dogs/jindo
- URI의 마지막에는 슬래시(/)를 사용하지 않는다.
- URI는 소문자로만 작성한다.
- 가독성을 높이기 위해 하이픈(-)을 사용할 수 있지만, 언더바(_)를 사용하지 않는다.
### HTTP 메서드
#### REST 서비스에서는 CRUD에 해당하는 4개의 메서드를 사용한다. POST, GET, PUT, DELETE
