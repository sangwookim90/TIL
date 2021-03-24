package me.helpeachother.springsecurity.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * Class Name : CustomAuthenticationEntryPoint
 * Description : Custemized LoginUrlAuthenticationEntryPoint
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

public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {


	public CustomAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		super.commence(request, response, authException);
	}
    
}