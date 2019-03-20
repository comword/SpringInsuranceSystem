package org.gtdev.webapps.iaatraesamhsaat.empadmport;

import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Extension
@Controller
public class DashboardController implements ExtensionPoint {
    private Logger Log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/employee/admin")
    public String login(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
            return "employee/admin/admin";
    }
}
