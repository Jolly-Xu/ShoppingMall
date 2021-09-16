package com.xujialin.Config;

import com.xujialin.Handler.*;
import com.xujialin.SafetyVerification.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author XuJiaLin
 * @date 2021/9/13 21:51
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomizeAccessDeniedHandler deniedHandler;

    @Autowired
    private CustomizeAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomizeAuthenticationFailureHandler failureHandler;

    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

    @Autowired
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder Encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginProcessingUrl("/login").permitAll()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and().exceptionHandling().accessDeniedHandler(deniedHandler)
                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
                .and().authorizeRequests().anyRequest().hasRole("User");


        http.logout().logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll();

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/orderinfo/placeorder").antMatchers("/orderinfo/buy");
    }
}
