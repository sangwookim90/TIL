package me.helpeachother.springsecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static me.helpeachother.springsecurity.util.LogMessageFormatter.format;


/**
 * <pre>
 * Class Name : JwtAuthorizationFilter
 * Description : Token 정보를 읽어 인증을 진행하고, 인증 정보를 Spring Security Context Holder 에 저장한다.
 *               인증 성공 후에는 Token 을 refresh 하여 인증을 연장한다.
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
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.debug(format("{}", "JwtAuthorizationFilter"),((HttpServletRequest)request).getRequestURI());

        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication;
			try {
                // 인증 정보 생성 
                authentication = jwtTokenProvider.getAuthentication(token);
                // 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // token refresh (임시 처리 - refresh token 개발 전까지는 인증되면 재생성 한다.)
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof UserDetails) {
                    CustomUser cu = (CustomUser) principal;
                    try {
                        token = jwtTokenProvider.createToken(cu);
                        // header set
                        ((HttpServletResponse)response).addHeader(jwtTokenProvider.getTokenName(), token);
                        // cookie set
                        Cookie cookie = new Cookie(jwtTokenProvider.getTokenName(), token);
                        cookie.setPath("/");
                        ((HttpServletResponse)response).addCookie(cookie);
                       
                    } catch (Exception e) {
                        log.error(format("{}", "Token 생성 실패."),e.getMessage());                        
                        throw new IllegalArgumentException("인증 Token 생성 실패. 관리자에게 문의 하시기 바랍니다.");
                    }
                } 
            } catch (Exception e) {
                log.error(format("{}", "Token 인증 실패."),e.getMessage());
                throw new IllegalArgumentException("token 인증 실패. 관리자에게 문의 하시기 바랍니다.");
			}
        }
        chain.doFilter(request, response);
    }
}