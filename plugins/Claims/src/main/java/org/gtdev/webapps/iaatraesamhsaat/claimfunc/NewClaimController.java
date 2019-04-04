package org.gtdev.webapps.iaatraesamhsaat.claimfunc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NewClaimController {
    @GetMapping("/claim/newclaim")
    public String newClaimPage(HttpServletRequest request) {
        return "claim/newClaim";
    }
}
