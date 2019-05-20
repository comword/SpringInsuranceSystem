package org.gtdev.webapps.iaatraesamhsaat.claimfunc;


import lombok.Data;
import org.gtdev.webapps.iaatraesamhsaat.configs.AppConfig;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.*;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CustomerResponseController {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;
    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;
    @Autowired
    private InsurancePolicyRecordRepository insurancePolicyRecordRepository;
    @Autowired
    private LostItemRepository lostItemRepository;
    @Autowired
    private AppConfig.DataPathConfig dataPathConfig;
    @Autowired
    private LocaleResolver localeResolver;

    private Logger log= LoggerFactory.getLogger(CustomerResponseController.class);

    @Data
    private static class insuranceRequest {
        private String customer;
    }
    @Data
    private static class claimRequest {
        private String claimOrderNum;
    }
    @Data
    private static class insuranceNumRequest {
        private String insuranceNum;
    }
    @Data
    private static class claimSubmitRequest {
        private String itemType,itemName,itemPrice,itemDescription,contactEmail,policyNum,username;
    }
    @Data
    private static class profileRequest{
        private String displayName;
    }
    @Data
    private static class listRequest{
        private String username;
    }
    @Data
    private static class modifyProfileRequest{
        private String displayName,firstName,lastName,email,phone,user;
    }
    @Data
    private static class RenewRequest{
        private String Id,endDate,days;
    }



    @RequestMapping(value={"/customer/claimTrack/claimOrderNum"},  method = RequestMethod.POST)
    @ResponseBody
    public String claimOrderNum(@RequestBody claimRequest req) throws JSONException {

        Optional<InsuranceClaim> ic = insuranceClaimRepository.findById(Long.parseLong(req.claimOrderNum));
        JSONObject result = new JSONObject();
        //        result.put("resCode", 0);
        log.info("Claim: " + req.toString());
        result.put("resCode","0");
        result.put("claimOrderNum",ic.get().getId());//索赔单号
        //            result.put("step","2");             //目前所在的阶段 审核中....
        //            result.put("step","3");             //目前所在的阶段 需要额外的信息
        result.put("step",ic.get().getClaimStep());
        //目前所在的阶段 成功或者失败
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.put("date",df.format(ic.get().getTime()));
            if(result.getString("step").equals("4")){
                result.put("result",ic.get().getResult());
                //                result.put("result","fail");
            }
        return result.toString();
    }


    @RequestMapping(value="/customer/page/uploadClaimInfo",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String uploadClaimInfo(@RequestBody insuranceRequest req,HttpServletRequest request) throws JSONException {
        AppUser appUser = userRepository.findAppUserByDisplayName(req.getCustomer());
//        AppUser appUser = userRepository.findAppUserByUserName("customer0");
        Locale l = localeResolver.resolveLocale(request);
        List<InsuranceClaim> claimList = insuranceClaimRepository.findAllByUser(appUser);
        return parseJson(claimList,l);
    }




    @RequestMapping(value={"/customer/insucranceTrack/insuranceNum"},  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String insuranceNum(@RequestBody insuranceNumRequest req,HttpServletRequest request) throws JSONException {
        Optional<InsurancePolicyRecord> ipr = insurancePolicyRecordRepository.findById(req.getInsuranceNum());
        CustomerDetails details = ipr.get().getCustomer();
        Locale l = localeResolver.resolveLocale(request);
        JSONObject result = new JSONObject();
        if(l.equals(Locale.SIMPLIFIED_CHINESE)) {
            result.put("InsuranceName", ipr.get().getInsuranceProduct().getInsuranceChineseName());
            result.put("Country", details.getCountryStr());
            result.put("Province", details.getProvince());
        }
        else {
            result.put("InsuranceName", ipr.get().getInsuranceProduct().getInsuranceName());
            result.put("Country", details.getCountryStr());
            result.put("Province", details.getProvince());
        }
        result.put("InsuranceNum",ipr.get().getId());
        result.put("TotalPaid",ipr.get().getTotalPaid());
        result.put("StartTime", ipr.get().getStartDatetime());
        result.put("EndTime", ipr.get().getEndDatetime());
        result.put("Destination", ipr.get().getDestination());
        result.put("CustomerName", details.getFirstName()+";"+details.getLastName());
        result.put("idNum", details.getIdNumber());
        result.put("Zip",details.getZipCode());
        result.put("Email", details.getUser().getEmail());
        result.put("Phone", details.getPhoneNumber());
        return result.toString();
    }








    @RequestMapping(value="/customer/page/uploadInsuranceInfo",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
//    public String uploadInsuranceInfo() throws JSONException {
        public String uploadInsuranceInfo(@RequestBody insuranceRequest req,HttpServletRequest request) throws JSONException {
        AppUser appUser = userRepository.findAppUserByDisplayName(req.getCustomer());
//        AppUser appUser = userRepository.findAppUserByUserName("customer0");
        Locale l = localeResolver.resolveLocale(request);
        List<InsurancePolicyRecord> insuranceList = insurancePolicyRecordRepository.findAllByCustomer(appUser.getDetails());
        return parseJsonInsurance(insuranceList,l);
    }



    @RequestMapping(value="/customer/Renew/listRequest",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String uploadListInfo(@RequestBody listRequest req,HttpServletRequest request) throws JSONException {
        AppUser appUser = userRepository.findAppUserByDisplayName(req.getUsername());
        Locale l = localeResolver.resolveLocale(request);
        List<InsurancePolicyRecord> insuranceList = insurancePolicyRecordRepository.findAllByCustomer(appUser.getDetails());
        return parseJsonInsuranceList(insuranceList,l);
    }


    @RequestMapping(value="/customer/Profile/uploadProfileInfo",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String uploadProfileInfo(@RequestBody profileRequest req) throws JSONException {
        AppUser appUser = userRepository.findAppUserByDisplayName(req.getDisplayName());
//        AppUser appUser = userRepository.findAppUserByDisplayName("customer0");
        CustomerDetails details = appUser.getDetails();
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        jsonObject.put("userName",appUser.getUserName());
        jsonObject.put("email",appUser.getEmail());
        jsonObject.put("phone",details.getPhoneNumber());
        jsonObject.put("firstName",details.getFirstName());
        jsonObject.put("lastName",details.getLastName());
        return jsonObject.toString();
    }


    @RequestMapping(value="/customer/Profile/modifyProfileInfo",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String uploadProfileInfo(@RequestBody modifyProfileRequest req, HttpSession session) throws JSONException {
        AppUser appUser = userRepository.findAppUserByDisplayName(req.getUser());
//        AppUser appUser = userRepository.findAppUserByDisplayName("customer0");
        AppUser user = (AppUser) session.getAttribute("user");
        appUser.setEmail(req.getEmail());
        appUser.setUserName(req.getDisplayName());
        appUser.setDisplayName(req.getDisplayName());
        CustomerDetails details = appUser.getDetails();
        details.setPhoneNumber(req.getPhone());
        details.setFirstName(req.getFirstName());
        details.setLastName(req.getLastName());
        customerDetailsRepository.save(details);
        userRepository.save(appUser);
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        jsonObject.put("Fxc","fxc");
        String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userRepository.findAppUserById(Long.valueOf(uid));
        session.setAttribute("user", user);
        return jsonObject.toString();
    }



    @RequestMapping(value="/customer/Renew/modifyEndDateInfo",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String uploadDateInfo(@RequestBody RenewRequest req) throws JSONException, ParseException {
        log.info("IDDD"+req.getId());
        Optional<InsurancePolicyRecord> ipr = insurancePolicyRecordRepository.findById(req.getId());
        InsurancePolicyRecord insurancePolicyRecord = ipr.get();
        Date oldDate = insurancePolicyRecord.getEndDatetime();
        Long time = oldDate.getTime();
        time = time+Long.parseLong(req.days)*24*60*60*1000;
        Timestamp newDate = new Timestamp(time);
        insurancePolicyRecord.setEndDatetime(newDate);
//        insurancePolicyRecord.setStartDatetime( new Timestamp(insurancePolicyRecord.getStartDatetime().getTime()));
        insurancePolicyRecordRepository.save(insurancePolicyRecord);
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        jsonObject.put("Fxc","fxc");
        return jsonObject.toString();
    }



    @RequestMapping(value="/customer/page/uploadInsuranceInfoInClaim",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String uploadInsuranceInfoInClaim(@RequestBody insuranceRequest req,HttpServletRequest request) throws JSONException {
        AppUser appUser = userRepository.findAppUserByDisplayName(req.getCustomer());
//        AppUser appUser = userRepository.findAppUserByUserName("customer0");
        Locale l = localeResolver.resolveLocale(request);
        List<InsurancePolicyRecord> insuranceList = insurancePolicyRecordRepository.findAllByCustomer(appUser.getDetails());
        return parseJsonInsuranceInClaim(insuranceList,l);
    }

    public String parseJson(List<InsuranceClaim> claimList,Locale locale) throws JSONException {
        if (claimList == null) return "";
        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        InsuranceClaim claim = null;
        for (int i = 0; i < claimList.size(); i++) {
            claim = claimList.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("claimID",claim.getId());
            jsonObject.put("Step",claim.getClaimStep());
            jsonObject.put("InsuranceId",claim.getPolicy().getId());
            if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                jsonObject.put("InsuranceName", claim.getPolicy().getInsuranceProduct().getInsuranceChineseName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>详情</button>");

            }
            else {
                jsonObject.put("InsuranceName", claim.getPolicy().getInsuranceProduct().getInsuranceName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
            }
            //            jsonObject.put("button","<button class=\"btn btn-info btn-rounded\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }

    public String parseJsonInsurance(List<InsurancePolicyRecord> insuranceList,Locale locale) throws JSONException {

        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        if (insuranceList == null) {
            array.put("data",data);
            return array.toString();
        }
        InsurancePolicyRecord insurancePolicyRecord = null;
        for (int i = 0; i < insuranceList.size(); i++) {
            insurancePolicyRecord = insuranceList.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("InsuranceId",insurancePolicyRecord.getId());
            if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                jsonObject.put("InsuranceName", insurancePolicyRecord.getInsuranceProduct().getInsuranceChineseName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>细节</button>");

            }
            else {
                jsonObject.put("InsuranceName", insurancePolicyRecord.getInsuranceProduct().getInsuranceName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
            }
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }


    public String parseJsonInsuranceList(List<InsurancePolicyRecord> insuranceList,Locale locale) throws JSONException {

        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        if (insuranceList == null) {
            array.put("data",data);
            return array.toString();
        }
        InsurancePolicyRecord insurancePolicyRecord = null;
        for (int i = 0; i < insuranceList.size(); i++) {
            insurancePolicyRecord = insuranceList.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("PolicyID",insurancePolicyRecord.getId());
            if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                jsonObject.put("InsuranceName", insurancePolicyRecord.getInsuranceProduct().getInsuranceChineseName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#modalQuickView\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>续保</button>");

            }
            else {
                jsonObject.put("InsuranceName", insurancePolicyRecord.getInsuranceProduct().getInsuranceName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#modalQuickView\" onclick = \"clicke(this);\" ><i class=\"fas fa-magic mr-1\"></i>Renew</button>");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonObject.put("StartTime",df.format(insurancePolicyRecord.getStartDatetime()));
            jsonObject.put("EndTime",df.format(insurancePolicyRecord.getEndDatetime()));
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }



    public String parseJsonInsuranceInClaim(List<InsurancePolicyRecord> insuranceList,Locale locale) throws JSONException {
        if (insuranceList == null) return "";
        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        InsurancePolicyRecord insurancePolicyRecord = null;
        for (int i = 0; i < insuranceList.size(); i++) {
            insurancePolicyRecord = insuranceList.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("InsuranceId",insurancePolicyRecord.getId());
            if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                jsonObject.put("InsuranceName", insurancePolicyRecord.getInsuranceProduct().getInsuranceChineseName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>索赔</button>");
            }
            else {
                jsonObject.put("InsuranceName", insurancePolicyRecord.getInsuranceProduct().getInsuranceName());
                jsonObject.put("button", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Claim</button>");
            }
            data.put(jsonObject);


        }
        array.put("data",data);
        return array.toString();
    }

}
