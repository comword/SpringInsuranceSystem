package org.gtdev.webapps.iaatraesamhsaat.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
public class homePageController {
    private Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "homePage/home";
        else
            return "homePage/home";
    }

}
