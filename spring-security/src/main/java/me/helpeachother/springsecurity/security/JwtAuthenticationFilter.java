package me.helpeachother.springsecurity.security;

import lombok.extern.slf4j.Slf4j;
import me.helpeachother.springsecurity.account.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static me.helpeachother.springsecurity.util.LogMessageFormatter.format;


/**
 * <pre>
 * Class Name : JwtAuthenticationFilter
 * Description : Spring Security JWT Token Filter
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
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private boolean postOnly = true;

    private JwtTokenProvider jwtTokenProvider;

    private AccountService accountService;
    
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AccountService accountService) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountService = accountService;
    }
    
    /*
     * 해당 필터에서 인증 프로세스 이전에 요청에서 사용자 정보를 가져와서
     * Authentication 객체를 인증 프로세스 객체에게 전달하는 역할
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        log.debug(format("{}", "JwtAuthenticationFilter"),request.getRequestURI());                

        /*
         * POST로 넘어왔는지 체크
         */
        if(postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if(StringUtils.isEmpty(username)) {
            username = "";
        }
        if(StringUtils.isEmpty(password)) {
            password = "";
        }
        username = username.trim();
        
        //로그인 실패를 연속으로 5회 이상 한 경우, 5분간 차단
//        int lockedRemainSecond = accountService.getLoginFailCountLockedRemainSecond(username);
//        if(lockedRemainSecond > 0) {
//        	throw new LockedException(Integer.toString(lockedRemainSecond));
//        }
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
 
        setDetails(request, authRequest);
        
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 로그인이 성공 했음으로, Cookie 와 Header 에 Token 정보를 전송 한다.
        Object principal = authResult.getPrincipal();
        if (principal instanceof UserDetails) {
            CustomUser cu = (CustomUser) principal;
            String token;
			try {
				token = jwtTokenProvider.createToken(cu);
                // header set
                response.addHeader(jwtTokenProvider.getTokenName(), token);
                // cookie set
                Cookie cookie = new Cookie(jwtTokenProvider.getTokenName(), token);
                cookie.setPath("/");
                response.addCookie(cookie);
            } catch (Exception e) {
                log.error(format("{}", "인증 Token 생성 실패"),e.getMessage());
                throw new IllegalArgumentException("인증 Token 생성 실패. 관리자에게 문의 하시기 바랍니다.");
			}
		} 
        super.successfulAuthentication(request, response, chain, authResult);
	}
}