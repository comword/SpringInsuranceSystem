package org.gtdev.webapps.iaatraesamhsaat.lrplugin;

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
public class EmployeeLoginController implements ExtensionPoint {
    private Logger Log = LoggerFactory.getLogger(EmployeeLoginController.class);

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/employee/login")
    public String login(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/login/login-cn";
        else
            return "employee/login/login";
    }
}
