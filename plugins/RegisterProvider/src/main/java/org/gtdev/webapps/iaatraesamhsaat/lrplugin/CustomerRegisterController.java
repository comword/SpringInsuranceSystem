package org.gtdev.webapps.iaatraesamhsaat.lrplugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.ProvincesRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.Provinces;
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

@Controller
public class CustomerRegisterController {
    private Logger Log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private ProvincesRepository provincesRepo;

    @GetMapping("/register/customer/**")
    public String registerCustomer(HttpServletRequest request) {
        return "register/customer";
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

    public static String loadHTMLFile(Locale lang, String path) throws IOException {
        File nextPage = null;
        if(lang.equals(Locale.SIMPLIFIED_CHINESE))
            nextPage = ResourceUtils.getFile("file:"+path+"-cn.html");
        else
            nextPage = ResourceUtils.getFile("file:"+path+".html");
        FileInputStream is = new FileInputStream(nextPage);
        String res = convertStreamToString(is);
        is.close();
        return res;
    }

    @RequestMapping(value = "/register/basicInfo" , method = RequestMethod.GET)
    public ResponseEntity<String> basicInfoGet(HttpServletRequest request) throws IOException {
        RegisterReply rpl = new RegisterReply();
        Locale l = localeResolver.resolveLocale(request);
        rpl.nextPage.put("name", "basic");
        rpl.nextPage.put("template", loadHTMLFile(l, "templates/register/customer-basic"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/register/basicInfo" , method = RequestMethod.POST)
    public ResponseEntity<String> basicInfoPost(HttpSession session, @RequestBody basicInfoPost post) throws IOException {
        Log.info(post.toString());
        if(!post.token.equals(session.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"errcode\": -1000 ,\"errmsg\": \"Session token expired, please refresh the page.\"}");
        session.setAttribute("basicInfo", post); //save the post to session
        RegisterReply rpl = new RegisterReply();
        Locale l;
        if(post.lang == null)
            l = Locale.US;
        else
            l = Locale.forLanguageTag(post.lang);
        rpl.nextPage.put("name", "detailsForm");
        rpl.nextPage.put("template", loadHTMLFile(l, "templates/register/customer-details"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

    @Data
    @NoArgsConstructor
    static class ProvincesReply {
        private int errcode = 0;
        private List<Provinces> area = new ArrayList<>();
    }

    @RequestMapping(value = "/register/provinces" , method = RequestMethod.GET)
    public @ResponseBody ProvincesReply getProvinces(String query) {
        ProvincesReply rpl = new ProvincesReply();
        if(query == null){
            rpl.errcode = -1000; //Bad query
            return rpl;
        }
        Log.info("Querying provinces for "+query);
        if(query.equals("Ireland")){
            rpl.area = provincesRepo.findIrelandCountries();
        } else if (query.equals("China")) {
            rpl.area = provincesRepo.findChineseCountries();
        } else {
            rpl.errcode = -400;
        }
        return rpl;
    }

    @Data
    @NoArgsConstructor
    static class detailsInfoPost {
        private String lang, token;
        private String phone, ID, address, address2, country, state, zip,
                payment, card_name, card_num, card_expr, card_ccv;
    }

    @RequestMapping(value = "/register/detailsInfo" , method = RequestMethod.POST)
    public @ResponseBody RegisterReply detailsInfoPost(HttpSession session, @RequestBody detailsInfoPost post) {
        Log.info(post.toString());
        RegisterReply rpl = new RegisterReply();
        return rpl;
    }
}
