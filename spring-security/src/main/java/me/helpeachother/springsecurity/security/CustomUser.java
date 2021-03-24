package me.helpeachother.springsecurity.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.helpeachother.springsecurity.account.AccountVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * <pre>
 * Class Name : CustomUser
 * Description : Customized User
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


@Getter
@Setter
@Slf4j
public class CustomUser extends User {

	private static final long serialVersionUID = 2207087831266506757L;

	private AccountVo accountVo;
    
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities , AccountVo accountVo) {
        super(username, password, authorities);
        this.accountVo = accountVo;
    }
}