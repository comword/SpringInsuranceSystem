package org.gtdev.webapps.iaatraesamhsaat.claimfunc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NewClaimController {

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/claim/newclaim")
    public String newClaimPage(HttpServletRequest request) {
        return "claim/claimPage";
    }

}
