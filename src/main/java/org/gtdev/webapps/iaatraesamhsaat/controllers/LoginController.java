package org.gtdev.webapps.iaatraesamhsaat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppGroup;
import org.gtdev.webapps.iaatraesamhsaat.security.JWTSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class LoginController {
    private Logger Log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTSvc JWTSvc;

    private boolean isAdmin(Authentication auth) {
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (AppGroup.gpPrivilege.valueOf(grantedAuthority.getAuthority()).getPriv()<=60) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Authentication auth = JWTSvc.getAuthentication(request);
        if(auth!=null){ //already authenticated
            boolean isAdmin = isAdmin(auth);
            if(isAdmin)
                return "redirect:/employee/admin/dashboard";
            else
                return "redirect:/";
        }
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "login/login-cn";
        else
            return "login/login";
    }

    @Data
    @NoArgsConstructor
    private static class userCredentials {
        private String username, password;
    }

    @Data
    @NoArgsConstructor
    private static class loginRespond {
        private int rescode = -1000;
        private String errmsg;
        private String redirect;
    }

    @PostMapping("/login")
    public void login(HttpServletRequest req, HttpServletResponse response, @RequestBody userCredentials user) throws IOException {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.username, user.password);
        Authentication auth;
        loginRespond res = new loginRespond();
        try {
            auth = authManager.authenticate(authReq);
            if(auth.isAuthenticated()){
                Log.info("User "+user.username+" login successfully.");
                JWTSvc.successfulAuthentication(req, response, auth);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);
                HttpSession session = req.getSession(true);
                session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
                res.rescode = 0;
                if(isAdmin(auth))
                    res.redirect = "/employee/admin/dashboard";
            }
        } catch (BadCredentialsException e) {
            res.rescode = -1002;
            res.errmsg = e.getMessage();
        } catch (UsernameNotFoundException e) {
            res.rescode = -1003;
            res.errmsg = e.getMessage();
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(objectMapper.writeValueAsString(res));
        out.flush();
    }

    @ResponseBody
    @GetMapping(value = "/login/tokenId", produces = "application/json")
    public String getToken(HttpSession session, HttpServletRequest request) {
        return "{\"token\": \""+session.getId()+"\", \"lang\": \""
                +localeResolver.resolveLocale(request).toLanguageTag()+"\"}";
    }
}
