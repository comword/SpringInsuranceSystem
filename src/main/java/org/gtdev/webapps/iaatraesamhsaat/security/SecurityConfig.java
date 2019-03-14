package org.gtdev.webapps.iaatraesamhsaat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static class JwtConfig {
        @Value("${security.jwt.uri:/auth/**}")
        String Uri;

        @Value("${security.jwt.header:Authorization}")
        String header;

        @Value("${security.jwt.prefix:}")
        String prefix;

        @Value("${security.jwt.expiration:#{24*60*60}}")
        int expiration;

        @Value("${security.jwt.secret:JwtSecretKey}")
        String secret;
    }

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and().addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
        .authorizeRequests()
        .antMatchers("**").permitAll()
        .antMatchers("/robots.txt").permitAll()
        .antMatchers( "/register/**", "/api/**").permitAll()
        .antMatchers("/login").permitAll()
        .anyRequest().authenticated()
        .and().formLogin()
        .loginPage("/login").defaultSuccessUrl("/").permitAll()
        .and()
        .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true).clearAuthentication(true).permitAll();

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**");
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

}