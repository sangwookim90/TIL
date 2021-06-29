# JWT (Json Web Token)
    서버가 클라이언트의 권한 및 인증정보가 표현된 JSON을 암호화 후 Token으로 발급하여, 클라이언트가 서버에 통신할 때 Session 없이 상태를 표현할 수 있도록 해주는 인증방식.

## Stateful vs. Stateless
- Stateful 서버
    -   서버가 클라이언트의 상태정보(Session)를 보유 및 유지하며, 상태정보를 바탕으로 클라이언트에게 서비스를 제공한다. 상태정보는 서버 컴퓨터의 메모리 혹은 DB에서 관리된다.
- Stateless 서버
    -   서버가 클라이언트의 상태정보를 보유하지 않으며 클라이언트측의 요청만으로 서비스를 제공한다. 상태정보라는 공유점이 없기 때문에 확장성이 높다.
    
    
## Token 인증 방식
    근래의 웹 API 서비스는 대부분 Stateless + Token 인증기반으로 운영된다.
    
#### 서버가 클라이언트에 권한 및 인증정보를 암호화하여 발급해주고, 클라이언트는 이후에 서비스를 이용할 때 Token을 요청에 담아서 함께 보낸다. 
#### 서버에서는 해당 Token이 변조된 것인지 검증한 후 서비스를 제공한다. 즉, Token 인증방식은 Token이라는 인증정보를 클라이언트가 보유한다.


## JWT 구조
-   Header : Token의 암호화 종류(alg)와 타입(typ)이 정의된 JSON을 Base64

```
{ 
    "alg" : "HS256", 
    "typ" : "JWT" 
}
```

-   Payload : 해당 토큰이 포함할 몇가지 필드(Key-Value)가 담긴 JSON을 Base64로 인코딩한다. 토큰에 포함될 데이터(claim) 서술

```
{
    "iat" : 1554076800000, 
    "exp" : 1554681600000, 
    "uuid" : "09226aac-5f8c-11e9-8647-d663bd873d93"
}
```
##### (발급시간, 만료시간 포함 권장)
-   Signature : 위변조되지 않은 JWT를 검증하는데 쓰이며, 검증은 비밀키를 가지고 있는쪽에서만 가능
    - 완성된 JWT : 최종 형태는 Header.Payload.Signature와 같다.
```
var encodedHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"; 
var encodedPayload = "eyJpYXQiOjE1NTQwNzY4MDAwMDAsImV4cCI6MTU1NDY4MTYwMDAwMCwidXVpZCI6IjA5MjI2YWFjLTVmOGMtMTFlOS04NjQ3LWQ2NjNiZDg3M2Q5MyJ9"; 

var secret = "moon"; 
var signature = HS256.encode(encodedHeader + "." + encodedPayload, secret).toBase64String(); 

// signature 
// 0mgNdgQZNw_C2QfloguymnakqIpDizuvo0uFRuxDWQo

"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NTQwNzY4MDAwMDAsImV4cCI6MTU1NDY4MTYwMDAwMCwidXVpZCI6IjA5MjI2YWFjLTVmOGMtMTFlOS04NjQ3LWQ2NjNiZDg3M2Q5MyJ9.0mgNdgQZNw_C2QfloguymnakqIpDizuvo0uFRuxDWQo"
```

    
  
