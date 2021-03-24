package me.helpeachother.springsecurity.security;

import lombok.extern.slf4j.Slf4j;
import me.helpeachother.springsecurity.account.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * Class Name : CustomSimpleUrlAuthenticationFailureHandler
 * Description : Customized SimpleUrlAuthenticationFailureHandler
 * 				 로그인 실패 시 발동되는 핸들러
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
public class CustomSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private AccountService accountService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
				
		if(exception.getClass().isAssignableFrom(InternalAuthenticationServiceException.class)) {
			//비밀번호가 틀렸을때와 없는 계정을 호출했을때는 동일한 메시지로 처리해서 보안 위험성을 제거해야함
//			response.sendRedirect("/?error=wrongidpw");
			response.sendRedirect("/loginFail");
			return;
		} else if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			//비밀번호가 틀렸을때와 없는 계정을 호출했을때는 동일한 메시지로 처리해서 보안 위험성을 제거해야함
//			response.sendRedirect("/?error=wrongidpw");
			response.sendRedirect("/loginFail");
			return;
		} else if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			if(StringUtils.isNotEmpty(request.getParameter("username"))) {
				//accountService.updateLoginFailCount(request.getParameter("username"));
			}	
			//비밀번호가 틀렸을때와 없는 계정을 호출했을때는 동일한 메시지로 처리해서 보안 위험성을 제거해야함
//			response.sendRedirect("/?error=wrongidpw");
			response.sendRedirect("/loginFail");
			return;
		} else if(exception.getClass().isAssignableFrom(LockedException.class)) {
			// 비밀번호 연속 5회 틀렸을 경우 차단페이지 이동
			// RequestDispatcher 사용 시에 405 에러가 발생하며, 이는 본 페이지 내에서 처리하는 중에 POST가 허용되지 않아서임. 
			// get 이 허용되는 페이지로 이동처리. 및 security config 에서 무시하는 페이지로 추가
//			response.sendRedirect("/loginLocked?lockedRemainSec="+exception.getMessage());
			// request.getRequestDispatcher("/loginLocked?lockedRemainSec="+exception.getMessage()).forward(request, response);
			return;
		}
		super.onAuthenticationFailure(request, response, exception);  		
	}
}