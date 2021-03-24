package me.helpeachother.springsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.helpeachother.springsecurity.account.AccountVo;
import me.helpeachother.springsecurity.util.CryptoAES;
import me.helpeachother.springsecurity.util.ObjectJsonUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static me.helpeachother.springsecurity.util.LogMessageFormatter.format;

/**
 * <pre>
 * Class Name : JwtTokenProvider
 * Description : Spring Security 기반의 JWT Token Provider
 *
 *
 *  Modification Information
 *  Modify Date 	Modifier		Comment
 *  -----------------------------------------------
 *  2021.03.24      swkim  		     New
 *
 * </pre>
 *
 * @author swkim
 * @since 2021.03.24
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈
    
    private final String tokenName = "X-AUTH-TOKEN";

    private String secretKey = "secret";

    //private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효
    private long tokenValidMilisecond = 1000L * 60 * 30; // 30분만 토큰 유효

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(CustomUser cu) throws IOException, Exception {
        Claims claims = Jwts.claims().setSubject(cu.getUsername());
        claims.put("roles", cu.getAuthorities());
        claims.put("auth",  new CryptoAES().encrypt(ObjectJsonUtil.getJsonStringByObject(cu.getAccountVo())));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) throws Exception {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        String json = new CryptoAES().decrypt((String) claims.getBody().get("auth"));
        AccountVo accountVo = (AccountVo) ObjectJsonUtil.getObjectByJsonString(json, AccountVo.class);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(CustomUserDetailsService.ROLE_PREFIX + accountVo.getRole()));

        CustomUser cu = new CustomUser(accountVo.getUsername(), "", grantedAuthorities, accountVo);
        return new UsernamePasswordAuthenticationToken(cu, "", grantedAuthorities);
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // token parsing
    public String resolveToken(HttpServletRequest req) {
        String token = req.getHeader(tokenName);
        // Header 에 없을 경우 Cooke 를 통해서 load 한다. 
        if(StringUtils.isEmpty(token) && ArrayUtils.isNotEmpty(req.getCookies())){
            for(Cookie c : req.getCookies()){
                if(c.getName().equals(tokenName)){
                    token = c.getValue();
                }
            }
        }
        log.debug(format("{}", "request token "),token);
        return token;
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenName(){
        return tokenName;
    }
}