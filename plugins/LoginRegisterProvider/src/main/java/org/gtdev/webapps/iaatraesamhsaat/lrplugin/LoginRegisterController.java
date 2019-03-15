package org.gtdev.webapps.iaatraesamhsaat.lrplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Extension
@Controller
public class LoginRegisterController implements ExtensionPoint {
    private Logger Log = LoggerFactory.getLogger(LoginRegisterController.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "login/login-cn";
        else
            return "login/login";
    }

    @GetMapping("/register/customer/**")
    public String registerCustomer(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "register/customer-cn";
//        else
        return "register/customer";
    }

    @ResponseBody
    @GetMapping(value = "/register/tokenId", produces = "application/json")
    public String customerToken(HttpSession session) {
        return "{\"token\": \""+session.getId()+"\"}";
    }

    @Data
    @NoArgsConstructor
    static class RegisterReply {
        Map<String, String> nextPage = new HashMap<>();
        List<Map<String, String>> checkRes = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    static class basicInfoPost {
        private String firstName, lastName, email, password, lang, token;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    @RequestMapping(value = "/register/basicInfo" , method = RequestMethod.GET)
    public ResponseEntity<String> basicInfoGet(HttpServletRequest request) throws IOException {
        RegisterReply rpl = new RegisterReply();
        File nextPage = null;
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            nextPage = ResourceUtils.getFile("file:templates/register/customer-basic-cn.html");
        else
            nextPage = ResourceUtils.getFile("file:templates/register/customer-basic.html");
        FileInputStream is = new FileInputStream(nextPage);
        rpl.nextPage.put("name", "basic");
        rpl.nextPage.put("template", convertStreamToString(is));
        is.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/register/basicInfo" , method = RequestMethod.POST)
    public ResponseEntity<String> basicInfoPost(HttpSession session, @RequestBody basicInfoPost post) throws IOException {
        Log.info(post.toString());
        if(!post.token.equals(session.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"errcode\": -1000 ,\"errmsg\": \"Session token expired, please refresh the page.\"}");
        session.setAttribute("basicInfo", post);
        RegisterReply rpl = new RegisterReply();
        File nextPage = null;
        if(post.lang != null)
            if(post.lang.equals("zh_CN")) //chinese
                nextPage = ResourceUtils.getFile("file:templates/register/customer-details-cn.html");
        if(nextPage==null)
            nextPage = ResourceUtils.getFile("file:templates/register/customer-details.html");
        FileInputStream is = new FileInputStream(nextPage);
        rpl.nextPage.put("name", "detailsForm");
        rpl.nextPage.put("template", convertStreamToString(is));
        is.close();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

}
