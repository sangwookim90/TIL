package me.helpeachother.springsecurity.config;

import me.helpeachother.springsecurity.account.AccountService;
import me.helpeachother.springsecurity.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import javax.servlet.Filter;
import javax.sql.DataSource;

/**
 * <pre>
 * Class Name : SecurityConfig
 * Description : Spring Security Config
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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String rememberMeTokenName = "X-AUTH-REMENBER-ME";

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    DataSource datasource;

    @Autowired
    AccountService accountService;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // ?????? ????????? ???????????? Security ????????? ???????????? ??????.
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/","/account/**","/loginFail").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN") //ADMIN, USER
                .anyRequest().authenticated()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/dashboard")
                .deleteCookies(rememberMeTokenName)
                .deleteCookies(jwtTokenProvider.getTokenName())
                .and()
            .rememberMe() // remember-me ????????? ?????? ????????? cookie ??? ?????? ??????.
                .rememberMeServices(persistentTokenBasedRememberMeServices())
                .and()
            // JWT Token ????????? login ????????? ????????? ??? ????????? ?????? ??????.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthenticationFilter())
                .exceptionHandling()
                .accessDeniedPage("/error/403.html")
                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint());
    }

    /**
     * PersistentLogins ????????? table ????????????, ?????? ????????? jdbcTokenRepository ?????? ?????? ??????.
     * @return jdbcTokenRepository
     */
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(datasource);
        return jdbcTokenRepository;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(new RememberMeAuthenticationProvider("rememberMeKey"));
    }

    @Bean
    public RememberMeAuthenticationFilter rememberMeAuthenticationFilter()
            throws Exception {
        RememberMeAuthenticationFilter filter =
                new RememberMeAuthenticationFilter(authenticationManager(), persistentTokenBasedRememberMeServices());
        return filter;
    }

    @Bean
    public CustomPersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
        CustomPersistentTokenBasedRememberMeServices service = new CustomPersistentTokenBasedRememberMeServices("rememberMeKey", customUserDetailsService, tokenRepository());
        service.setCookieName(rememberMeTokenName);
        // Token ????????? ????????? ?????? ????????? ????????? ?????????????????? ????????? ????????? ?????? ???????????? ?????????
        service.setTokenValiditySeconds(60 * 60 * 24 * 90);
        service.setAlwaysRemember(false);
        return service;
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Bean
    public CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler() {
        CustomSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler = new CustomSavedRequestAwareAuthenticationSuccessHandler();
        authenticationSuccessHandler.setDefaultTargetUrl("/loginSuccess");
        return authenticationSuccessHandler;
    }

    @Bean
    public CustomSimpleUrlAuthenticationFailureHandler customSimpleUrlAuthenticationFailureHandler() {
        CustomSimpleUrlAuthenticationFailureHandler authenticationFailureHandler = new CustomSimpleUrlAuthenticationFailureHandler();
//        authenticationFailureHandler.setDefaultFailureUrl("/?error=e");
        authenticationFailureHandler.setDefaultFailureUrl("/loginFail");
        return authenticationFailureHandler;
    }

    /*
     * Login Process Page
     * @Override UsernamePasswordAuthenticationFilter
     */
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(),jwtTokenProvider, accountService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        jwtAuthenticationFilter.setUsernameParameter("username");
        jwtAuthenticationFilter.setPasswordParameter("password");

        jwtAuthenticationFilter.setAuthenticationSuccessHandler(
                customSavedRequestAwareAuthenticationSuccessHandler()
        );

        jwtAuthenticationFilter.setAuthenticationFailureHandler(
                customSimpleUrlAuthenticationFailureHandler()
        );

        jwtAuthenticationFilter.setRememberMeServices(persistentTokenBasedRememberMeServices());

        jwtAuthenticationFilter.afterPropertiesSet();
        return jwtAuthenticationFilter;
    }

    /**
     * Login Authorization
     * @return
     * @throws Exception
     */
    public Filter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(jwtTokenProvider);
    }

    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint("/");
    }


}
