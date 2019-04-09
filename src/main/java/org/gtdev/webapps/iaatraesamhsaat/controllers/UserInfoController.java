package org.gtdev.webapps.iaatraesamhsaat.controllers;

import lombok.Getter;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.AppUserRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserInfoController {

    @Autowired
    private AppUserRepository userRepository;

    @Getter
    static class statusReply {
        int status;
        String userName;
        String displayName;
        String email;
    }

    @GetMapping("userinfo/username")
    public @ResponseBody statusReply userStatus(HttpSession session) {
        statusReply rpl = new statusReply();
        AppUser user = (AppUser) session.getAttribute("user");
        if (user == null) {
            //Get authentication principal
            String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (uid.equals("anonymousUser"))
                rpl.status = -1010; //Not login
            else
                user = userRepository.findAppUserById(Long.valueOf(uid));
        }
        if (user != null) {
            session.setAttribute("user", user);
            rpl.status = user.getStateId();
            rpl.userName = user.getUserName();
            rpl.displayName = user.getDisplayName();
            rpl.email = user.getEmail();
        }
        return rpl;
    }
}

