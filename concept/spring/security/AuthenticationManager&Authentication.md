## AuthenticationManager와 Authentication
스프링 시큐리티에서 인증(Authentication)은 AuthenticationManager가 한다.

### Authentication authentication(Authentication authentication) throws AuthenticationException;
* 인자로 받은 Authentication이 유효한 인증인지 확인하고 Authentication 객체를 리턴한다.
* 인증을 확인하는 과정에서 비활성 계정, 잘못된 비번, 잠긴 계정 등 에러를 던질 수 있다.

### 인자로 받은 Authentication
* 사용자가 입력한 인증에 필요한 정보(username, password)로 만든 객체 (폼 인증의 경우)
* Authentication
    - Principal: "sangwoo"
    - Credentials: "123"
    
### 유효한 인증인지 확인
* 사용자가 입력한 password가 UserDetailService를 통해 읽어온 UserDetails 객체에 들어있는 password와 일치하는지 확인
* 해당 사용자 계정이 잠겨 있진 않은지, 비활성 계정은 아닌지 등 확인

### Authentication 객체를 리턴
* Authentication
    - Principal: UserDetailsService에서 리턴한 그 객체 (User)
    - Credentials: "123"