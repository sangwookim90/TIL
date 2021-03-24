package me.helpeachother.springsecurity.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <pre>
 * Class Name : SecurityUtil
 * Description :
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

public class SecurityUtil {
	
	private SecurityUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static CustomUser getUserInfo() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (CustomUser) principal;
		} else {
			return null;
		}
	}

	public static Boolean isLoggedin() {
		Boolean result = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			result = true;
		}
		return result;
	}
}
