package org.gtdev.webapps.iaatraesamhsaat.empadmport;

import lombok.Data;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.AppUserRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.InsuranceClaimRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.AppUser;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsuranceClaim;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {
    private Logger Log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private LocaleResolver localeResolver;

    private Logger log= LoggerFactory.getLogger(DashboardController.class);
    @Data
    private static class adminRequest {
        private String pageNo;
    }

    @GetMapping("/employee/admin/dashboard")
    public String funcDashboard(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else


            return "employee/admin/dashboard";
    }


    @RequestMapping(value="/employee/admin/customer/request",  method = RequestMethod.POST)
    @ResponseBody
    public String claimOrderNum(@RequestBody adminRequest req) throws JSONException {
//        req
//        AppUser appUser = appUserRepository.findAppUserById(Long.parseLong(req.pageNo));
        List<AppUser> appUsers =  appUserRepository.findAll();
        log.info("是否为空"+appUsers.isEmpty());
        String json = parseJson(appUsers);
        log.info("JSON"+json);
//        JSONObject result = new JSONObject();
//        result.put("resCode", 0);
//        log.info("Claim: " + req.toString());
//        if(!ic.isPresent()){//如果没有找到索赔单号
//            result.put("resCode","-2002");
//            result.put("message","The claim order record was not found, please try again.");
//        }
//        else{//找到索赔单号
//            result.put("resCode","0");
//            result.put("claimOrderNum",ic.get().getId());//索赔单号
////            result.put("step","2");             //目前所在的阶段 审核中....
////            result.put("step","3");             //目前所在的阶段 需要额外的信息
//            result.put("step",ic.get().getClaimStep());             //目前所在的阶段 成功或者失败
//            if(result.getString("step").equals("4")){
//                result.put("result",ic.get().getResult());
////                result.put("result","fail");
//            }
//        }
//        return "Fxc";
        return json;
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


    public String parseJson(List<AppUser> items) throws JSONException {
        if (items == null) return "";
        JSONArray array = new JSONArray();
        JSONObject jsonObject = null;
        AppUser info = null;
        for (int i = 0; i < items.size(); i++) {
            info = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("CustomerId", info.getId());
            jsonObject.put("CustomerName", info.getUserName());
            jsonObject.put("Email", info.getEmail());

//            if(info.getDetails().getPhoneNumber()){
//                jsonObject.put("Phone", "null");
//            }else {
            jsonObject.put("Phone", info.getDetails().getPhoneNumber());
//            }
            array.put(jsonObject);
        }
        return array.toString();
    }

}
