package org.gtdev.webapps.iaatraesamhsaat.claimfunc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class NewClaimController {

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/claim/newclaim")
    public String newClaimPage(HttpServletRequest request,@RequestParam(name="lang",required=false,defaultValue="en-US") String lan) {
        if(lan.equals("en-US"))
            return "claim/claimPage";
        if(lan.equals("cn-ZH"))
            return "claim/claimPage-cn";
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "claim/claimPage-cn";
        else
            return "claim/claimPage";
    }

    @GetMapping("/claim/track")
    public String TrackClaim(HttpServletRequest request,@RequestParam(name="lang",required=false,defaultValue="en-US") String lan) {
//        if(lan.equals("en-US"))
            return "claim/claimTrack";
//        if(lan.equals("cn-ZH"))
//            return "claim/claimPage-cn";
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "claim/claimPage-cn";
//        else
//            return "claim/claimPage";
    }

}
