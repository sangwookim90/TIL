package me.helpeachother.springsecurity.security;

import me.helpeachother.springsecurity.account.Account;
import me.helpeachother.springsecurity.account.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * Class Name : CustomUserDetailsService
 * Description : Customized UserDetailsService
 *
 *
 *  Modification Information
 *  Modify Date 	Modifier		Comment
 *  -----------------------------------------------
 *  2021.03.22      swkim  		     New
 *
 * </pre>
 *
 * @author swkim
 * @since 2021.03.22
 */

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    ModelMapper mapper;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // account info
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + account.getRole()));

        return new CustomUser(account.getUsername(), account.getPassword(), grantedAuthorities, account.getVo(mapper));
    }
}