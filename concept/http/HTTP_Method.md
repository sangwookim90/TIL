# HTTP Method

### HTTP API 설계
ex) 회원 정보 관리 API 개발

#### URI 설계
> 가장 중요한 것은 **리소스 식별** 이다. 리소스와 행위(method)를 분리한다.

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