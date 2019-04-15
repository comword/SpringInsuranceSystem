package org.gtdev.webapps.iaatraesamhsaat.lrplugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.AppGroupRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.AppUserRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.CustomerDetailsRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.ProvincesRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.*;
import org.gtdev.webapps.iaatraesamhsaat.security.UserAuthProvider;
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

    @Autowired
    private AppUserRepository userRepo;

    @Autowired
    private AppGroupRepository groupRepo;

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @GetMapping("/register/customer/**")
    public String registerCustomer() {
        return "register/customer";
    }

    @Data
    @NoArgsConstructor
    static class RegisterReply {
        Map<String, String> nextPage = new HashMap<>();
        Map<Integer, String> checkRes = new HashMap<>();
    }

    @Data
    @NoArgsConstructor
    static class ErrorReply {
        private int errcode;
        private String errmsg;
    }

    @Data
    @NoArgsConstructor
    static class basicInfoPost {
        private String firstName, lastName, username, email, password, lang, token;
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

    @RequestMapping(value = "/register/customer/basicInfo" , method = RequestMethod.GET)
    public ResponseEntity<String> basicInfoGet(String token, String lang) throws IOException {
        RegisterReply rpl = new RegisterReply();
        Locale l = getLocale(lang);
        rpl.nextPage.put("name", "basicForm");
        rpl.nextPage.put("template", loadHTMLFile(l, "templates/register/customer-basic"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

    private Locale getLocale(String lang){
        Locale l;
        if(lang == null)
            l = Locale.US;
        else
            l = Locale.forLanguageTag(lang);
        return l;
    }

    @RequestMapping(value = "/register/customer/basicInfo" , method = RequestMethod.POST)
    public ResponseEntity<String> basicInfoPost(HttpSession session, @RequestBody basicInfoPost post) throws IOException {
        Log.info(post.toString());
        if(!post.token.equals(session.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"errcode\": -1000 ,\"errmsg\": \"Session token expired, please refresh the page.\"}");
        RegisterReply rpl = new RegisterReply();
        //Check data
        if(userRepo.existsAppUserByUserName(post.getUsername()))
            rpl.checkRes.put(-2001, "Username already exists.");
        if(userRepo.existsAppUserByEmail(post.getEmail()))
            rpl.checkRes.put(-2002, "Email already exists.");
        if(rpl.checkRes.size()==0){
            session.setAttribute("basicInfo", post); //save the post to session
            rpl.nextPage.put("name", "detailsForm");
            rpl.nextPage.put("template", loadHTMLFile(getLocale(post.getLang()), "templates/register/customer-details"));
        }
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
        private String phone, id_num, address, address2, country, province, zip,
                payment, card_name, card_num, card_expr, card_cvv;
    }

    private ResponseEntity<String> errorBuilder(int errcode, String errmsg, HttpStatus status) throws JsonProcessingException {
        ErrorReply erp = new ErrorReply();
        erp.errcode = errcode;
        erp.errmsg = errmsg;
        return ResponseEntity.status(status).body(objectMapper.writeValueAsString(erp));
    }

    @RequestMapping(value = "/register/customer/detailsInfo" , method = RequestMethod.GET)
    public ResponseEntity<String> detailsInfoGet(HttpSession session, String token, String lang) throws IOException {
        if(token==null || !token.equals(session.getId()))
            return errorBuilder(-1000, "Session token expired, please refresh the page.", HttpStatus.BAD_REQUEST);
        basicInfoPost basicInfo = (basicInfoPost) session.getAttribute("basicInfo");
        if(basicInfo == null) //return to basic page
            return errorBuilder(-2003, "Could not obtain basic info from session, please restart registration.", HttpStatus.PRECONDITION_FAILED);
        RegisterReply rpl = new RegisterReply();
        rpl.nextPage.put("name", "detailsForm");
        rpl.nextPage.put("template", loadHTMLFile(getLocale(lang), "templates/register/customer-details"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/register/customer/detailsInfo" , method = RequestMethod.POST)
    public ResponseEntity<String> detailsInfoPost(HttpSession session, @RequestBody detailsInfoPost post) throws IOException {
        Log.info(post.toString());
        //Get stored basic info from session
        ErrorReply erp = new ErrorReply();
        if(!post.token.equals(session.getId()))
            return errorBuilder(-1000, "Session token expired, please refresh the page.", HttpStatus.BAD_REQUEST);
        basicInfoPost basicInfo = (basicInfoPost) session.getAttribute("basicInfo");
        if(basicInfo == null) //return to basic page
            return errorBuilder(-2003, "Could not obtain basic info from session, please restart registration.", HttpStatus.PRECONDITION_FAILED);
        RegisterReply rpl = new RegisterReply();
        if(customerDetailsRepository.existsByIdNumberOrPhoneNumber(post.getId_num(), post.getPhone()))
            return errorBuilder(-2010, "Your ID number or phone number is already been registered, please recover your previous account or contact us.", HttpStatus.CONFLICT);
        //Save the new user to database if not existing
        if(!userRepo.existsAppUserByUserName(basicInfo.getUsername()) && !userRepo.existsAppUserByEmail(basicInfo.getEmail()))
            saveUserInfo(basicInfo, post);
        //In case of resubmitting, just return success page.
        rpl.nextPage.put("name", "successPage");
        rpl.nextPage.put("template", loadHTMLFile(getLocale(post.getLang()), "templates/register/success"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(objectMapper.writeValueAsString(rpl), headers, HttpStatus.OK);
    }

    private void saveUserInfo(basicInfoPost basic, detailsInfoPost details) {
        AppUser newUser = new AppUser();
        CustomerDetails userDetails = new CustomerDetails();
        userDetails.setUser(newUser);
        newUser.setUserState(AppUser.userState.initial);
        List<AppGroup> groups = new ArrayList<>();
        groups.add(groupRepo.findAppGroupByGroupName("Customers"));
        newUser.setGroups(groups);
        //First page
        userDetails.setFirstName(basic.getFirstName());
        userDetails.setLastName(basic.getLastName());
        newUser.setDisplayName(basic.getFirstName()+" "+basic.getLastName());
        newUser.setUserName(basic.getUsername());
        newUser.setEmail(basic.getEmail());
        String salt = UserAuthProvider.getRandomSalt();
        newUser.setSalt(salt);
        newUser.setPassword(UserAuthProvider.getPassword(basic.getPassword(), salt));
        //Second page
        userDetails.setPhoneNumber(details.getPhone());
        userDetails.setIdNumber(details.getId_num());
        userDetails.setAddress(details.getAddress());
        userDetails.setAddress2(details.getAddress2());
        userDetails.setProvince(details.getProvince());
        userDetails.setZipCode(details.getZip());
        newUser.setDetails(userDetails);
        //If the payment is valid, add a new payment method.
        if(!details.getPayment().isEmpty() && !details.getCard_name().isEmpty() && !details.getCard_num().isEmpty()
                && !details.getCard_expr().isEmpty() && !details.getCard_cvv().isEmpty()) {
            CustomerPayment newPay = new CustomerPayment();
            newPay.setUser(newUser);
            newPay.setPayMethod(details.getPayment());
            newPay.setCardName(details.getCard_name());
            newPay.setCardNumber(details.getCard_num());
            newPay.setCardExpiration(details.getCard_expr());
            newPay.setCardCvv(details.getCard_cvv());
            List<CustomerPayment> payments = new ArrayList<>();
            payments.add(newPay);
            newUser.setPayments(payments);
        }
        userRepo.save(newUser);
    }
}
