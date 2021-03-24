package me.helpeachother.springsecurity.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * Class Name : CustomSavedRequestAwareAuthenticationSuccessHandler
 * Description : Customized SavedRequestAwareAuthenticationSuccessHandler
 * 				 로그인 성공 시, 발동되는 핸들러
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
public class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void  onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
	
		super.onAuthenticationSuccess(request, response, authentication);

		// 사용자의 정보를 꺼낼 경우, SecurityContextHolder의 context에서 조회
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if(StringUtils.isNotEmpty(request.getParameter("username"))) {
			// login 성공 시, 처리할 로직 추가
		}		
	}


	// 로그인 기록남길 때 사용할 수도 있음
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}