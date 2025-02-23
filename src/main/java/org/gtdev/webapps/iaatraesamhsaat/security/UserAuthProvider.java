package org.gtdev.webapps.iaatraesamhsaat.security;

import org.gtdev.webapps.iaatraesamhsaat.database.dao.AppUserRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppGroup;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserAuthProvider implements AuthenticationProvider {
    @Autowired
    private AppUserRepository userRepo;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String s;
        String password;
        try{
            s = authentication.getName();
            password = authentication.getCredentials().toString().toUpperCase();
        } catch (Exception e) {
            throw new BadCredentialsException("Empty username or password.");
        }
        AppUser u;
        if(validateEmail(s)) //check if email address
            u = userRepo.findAppUserByEmail(s);
        else //try as username
            u = userRepo.findAppUserByUserName(s);
        if(u == null) //all failed
            throw new UsernameNotFoundException("User not found.");
        else {
            String pwd = getPassword(password, u.getSalt());
            if(pwd!=null && pwd.equals(u.getPassword())) {
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(getRolesByGroups(u.getGroups()));
                return new UsernamePasswordAuthenticationToken(String.valueOf(u.getId()), u.getPassword(), grantedAuthorities);
            } else
                throw new BadCredentialsException("Incorrect password or username.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    boolean validateEmail(String emailStr) {
        Matcher matcher = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(emailStr);
        if(matcher.find())
            return true;
        return false;
    }

    public String[] getRolesByGroups(List<AppGroup> gp) {
        Set<String> res = new HashSet<>();
        for(AppGroup g : gp)
            res.add(g.getGroupPrivilege().name());
        return res.toArray(new String[0]);
    }

    public static String getRandomSalt() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(array);
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPassword(String pwd, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();
            pwd = pwd.toUpperCase();
            salt = salt.toUpperCase();
            for (int i = 0; i < salt.length(); i++) {
                sb.append(pwd.charAt(i));
                sb.append(salt.charAt(i));
            }
            md.update(sb.toString().getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
