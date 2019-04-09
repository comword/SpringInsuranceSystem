package org.gtdev.webapps.iaatraesamhsaat.security;
//Reference:https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTSvc {

    @Getter
    @Configuration
    public static class JwtConfig {

        @Value("${security.jwt.header:Authorization}")
        private String header;

        @Value("${security.jwt.prefix:}")
        private String prefix;

        @Value("${security.jwt.expiration:#{24*60*60}}")
        private int expiration;

        @Value("${security.jwt.secret:JwtSecretKey}")
        private String secret;
    }

    @Autowired
    private JwtConfig jwtConfig;

    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("token", request.getSession().getId())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
        Cookie c = new Cookie(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
//        c.setSecure(true);
        c.setHttpOnly(true);
        response.addCookie(c);
    }

    public Cookie getAuthCookie(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if(cks!=null)
            for (Cookie c: cks) {
                if(c.getName().equals(jwtConfig.getHeader()))
                    if(c.getValue().startsWith(jwtConfig.getPrefix()))
                        return c;
            }
        return null;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        Cookie c = getAuthCookie(request);
        if(c==null)
            return null;
        String token = getAuthCookie(request).getValue().replace(jwtConfig.getPrefix(), "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            String username = claims.getSubject();
            if(username!=null) {
                @SuppressWarnings("unchecked")
                List<String> authorities = (List<String>) claims.get("authorities");
                String sessionToken = (String) claims.get("token");
                if(sessionToken.equals(request.getSession().getId())) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    return auth;
                }
            }
        } catch (Exception e) {
        }
        SecurityContextHolder.clearContext();
        return null;
    }

}
