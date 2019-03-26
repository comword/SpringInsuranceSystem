package org.gtdev.webapps.iaatraesamhsaat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletResponse;

@Configuration
@Order(1)
public class EmployeeSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthProvider uap;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/employee**")
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")

                .and()
                .formLogin()
                .loginPage("/loginEmployee")
                .failureUrl("/loginEmployee?error=loginError")
                .defaultSuccessUrl("/employee/admin")

                .and()
                .logout()
                .logoutUrl("/logoutEmployee")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .cors().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(uap);
    }

}