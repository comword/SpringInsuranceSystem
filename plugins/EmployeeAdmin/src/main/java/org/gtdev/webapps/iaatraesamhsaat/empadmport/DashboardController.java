package org.gtdev.webapps.iaatraesamhsaat.empadmport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {
    private Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/employee/admin/dashboard")
    public String funcDashboard(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
            return "employee/admin/dashboard";
    }

    @GetMapping("/employee/admin/claims")
    public String funcClaims(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/claims";
    }

    @GetMapping("/employee/admin")
    public String login(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/admin";
    }


    @GetMapping("/employee/admin/detail")
    public String detail(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/detail";
    }

    @GetMapping("/employee/admin/product")
    public String product(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/product";
    }

    @GetMapping("/employee/admin/customer")
    public String customer(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/customer";
    }

    @GetMapping("/employee/login/login")
    public String sign(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/login/login";
    }

    @GetMapping("/claim/claim_attachment")
    public String feedback(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/claim/claim_attachment";
    }

    @GetMapping("/claim/claim_feedback")
    public String atachment(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/claim/claim_feedback";
    }

    @GetMapping("/employee/admin/product_detail")
    public String product_detail(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
        return "employee/admin/product_detail";
    }
}
