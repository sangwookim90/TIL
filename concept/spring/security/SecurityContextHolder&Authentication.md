## SecurityContextHolder와 Authentication

### SecurityContextHolder
* SecurityContext 제공, 기본적으로 ThreadLocal을 사용
* ThreadLocal : 한 쓰레드내에서 공유하는 저장소
### SecurityContext 
* Authentication 제공
### Authentication
* Pricipal과 GrantAuthority 제공 
### Principal
* "누구"에 해당하는 정보
* UserDetailsService에서 리턴한 그 객체, 객체는 UserDetail 타입.
### GrantAuthority:
* "ROLE_USER", "ROLE_ADMIN" 등 Principal이 가지고 있는 "권한"을 나타낸다.
* 인증 이후, 인가 및 권한 확인할 때 이 정보 참조한다.
### UserDetail
* 애플리케이션이 가지고 있는 유저 정보와 스프링 시큐리티가 사용하는 Authentication 객체 사이의 어댑터
### UserDetailService
* 유저 정보를 UserDetails 타입으로 가져오는 DAO 인터페이스