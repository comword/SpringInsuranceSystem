package org.gtdev.webapps.iaatraesamhsaat.claimfunc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class CustomerPageController {

    @Autowired
    private LocaleResolver localeResolver;



    @GetMapping("/customer/page")
    public String claimTrack(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "customer/customerPage-cn";
        else
            return "customer/customerPage";
    }
    @GetMapping("/customer/insurance")
    public String insurance(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "customer/customerInsurance-cn";
        else
        return "customer/customerInsurance";
    }

    @GetMapping("/customer/Claim")
    public String claim(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "customer/customerClaim-cn";
        else
        return "customer/customerClaim";
    }
    @GetMapping("/customer/Profile")
    public String profile(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "customer/customerProfile-cn";
        else
            return "customer/customerProfile";
    }
    @GetMapping("/customer/Renew")
    public String RenewList(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "customer/customerRenewClaim-cn";
        else
            return "customer/customerRenewClaim";
    }
}
