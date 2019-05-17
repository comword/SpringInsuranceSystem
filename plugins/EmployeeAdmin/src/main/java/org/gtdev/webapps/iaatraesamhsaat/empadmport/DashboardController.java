package org.gtdev.webapps.iaatraesamhsaat.empadmport;

import lombok.Data;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.*;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.*;
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
import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class DashboardController {
    private Logger Log = LoggerFactory.getLogger(this.getClass());
//import entity dao classes
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private InsurancePolicyProductsRepository insurancePolicyProductsRepository;
    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private ItemTypeRepository itemTypeRepository;
    @Autowired
    private LocaleResolver localeResolver;
// create log entity
    private Logger log= LoggerFactory.getLogger(DashboardController.class);


    // create request data class

    @Data
    private static class adminRequest {
        private String pageNo;
    }
    @Data
    private static class ClaimIDRequest {
        private String claimID;
    }
    @Data
    private static class FeedbackRequest {
        private String claimID,result,feedback,timeStamp;
    }
    @Data
    private static class ClaimsRequest {
        private String timeOption;
    }

    /*   These are html mapping functions  */
    @GetMapping("/employee/admin/dashboard")
    public String funcDashboard(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/dashboard-cn";
        else
            return "employee/admin/dashboard";
    }

    @GetMapping("/employee/admin/claims")
    public String funcClaims(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/claims-cn";
        else
        return "employee/admin/claims";
    }

//    @GetMapping("/employee/admin")
//    public String login(HttpServletRequest request) {
////        Locale l = localeResolver.resolveLocale(request);
////        if(l.equals(Locale.SIMPLIFIED_CHINESE))
////            return "employee/admin/admin";
////        else
//        return "employee/admin/admin";
//    }


    @GetMapping("/employee/admin/detail")
    public String detail(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/detail-cn";
        else
        return "employee/admin/detail";
    }

    @GetMapping("/employee/admin/product")
    public String product(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/product-cn";
        else
        return "employee/admin/product";
    }

    @GetMapping("/employee/admin/customer")
    public String customer(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/customer-cn";
        else
        return "employee/admin/customer";
    }

//    @GetMapping("/employee/login/login")
//    public String sign(HttpServletRequest request) {
//        Locale l = localeResolver.resolveLocale(request);
//        if(l.equals(Locale.SIMPLIFIED_CHINESE))
//            return "employee/admin/admin";
//        else
//        return "employee/login/login";
//    }

    @GetMapping("/employee/admin/claim_attachment")
    public String feedback(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/claim/claim_attachment-cn";
        else
        return "employee/admin/claim/claim_attachment";
    }

    @GetMapping("/employee/admin/claim_feedback")
    public String atachment(HttpServletRequest request) {
        Locale l = localeResolver.resolveLocale(request);
        if(l.equals(Locale.SIMPLIFIED_CHINESE))
            return "employee/admin/claim/claim_feedback-cn";
        else
        return "employee/admin/claim/claim_feedback";
    }

//    @GetMapping("/employee/admin/product_detail")
//    public String product_detail(HttpServletRequest request) {
////        Locale l = localeResolver.resolveLocale(request);
////        if(l.equals(Locale.SIMPLIFIED_CHINESE))
////            return "employee/admin/admin";
////        else
//        return "employee/admin/product_detail";
//    }




    @RequestMapping(value="/employee/admin/customer/request",  method = RequestMethod.POST)
    @ResponseBody
    public String claimOrderNum() throws JSONException {
        List<AppUser> appUsers =  appUserRepository.findAll();
        log.info("是否为空"+appUsers.isEmpty());
        String json = parseJson(appUsers);
        log.info("JSON"+json);
        return json;
    }


    @RequestMapping(value="/employee/admin/attachment/request",  method = RequestMethod.POST)
    @ResponseBody
    public String Attachment(@RequestBody ClaimIDRequest req) throws JSONException {
        Long id = Long.parseLong(req.getClaimID());
        log.info("IDDDDD"+id);
        Optional<InsuranceClaim> oicd =  insuranceClaimRepository.findById(id);
        log.info("是否为空"+oicd.isPresent());
        LostItem lostItem= oicd.get().getLostItem();
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        jsonObject.put("url",lostItem.getUrl());
        jsonObject.put("policyNum",oicd.get().getPolicy().getId());
        log.info("JSON"+jsonObject);
        return jsonObject.toString();
    }

    @RequestMapping(value="/employee/admin/product/request",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String ResponseProduct(HttpServletRequest request) throws JSONException {
        List<InsurancePolicyProducts> insurancePolicyProducts =  insurancePolicyProductsRepository.findAll();
        Locale l = localeResolver.resolveLocale(request);
        log.info("是否为空"+insurancePolicyProducts.isEmpty());
        String json = ippParseJson(insurancePolicyProducts,l);
        log.info("JSON"+json);
        return json;
    }

    @RequestMapping(value="/employee/admin/claim/request",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String ResponseClaim(@RequestBody ClaimsRequest req,HttpServletRequest request) throws JSONException {
        //judge claim time
        List<InsuranceClaim> insuranceClaims = null;
        if(req.getTimeOption().equals("all")){
            insuranceClaims = insuranceClaimRepository.findAll();
        }
        else if(req.getTimeOption().equals("year")){
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            insuranceClaims = insuranceClaimRepository.findByTimeMatch(year);
        }
        else if(req.getTimeOption().equals("month")){
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            DecimalFormat df=new DecimalFormat("00");
            String month = df.format(date.get(Calendar.MONTH)+1);
            insuranceClaims = insuranceClaimRepository.findByTimeMatch(year+"-"+month);
        }
        else{
            log.info("unfinished");
            insuranceClaims = insuranceClaimRepository.findByClaimStepMatch("4");
        }
        //get client local
        Locale l = localeResolver.resolveLocale(request);
        log.info("是否为空"+insuranceClaims.isEmpty());
        // put response data to json
        String json = icParseJson(insuranceClaims,l);
        log.info("JSON"+json);
        return json;
    }
    @RequestMapping(value="/employee/admin/detail/request",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String ResponseClaim(@RequestBody ClaimIDRequest req,HttpServletRequest request) throws JSONException {
        Long id = Long.parseLong(req.getClaimID());
        Locale l = localeResolver.resolveLocale(request);
        Optional<InsuranceClaim> oicd = insuranceClaimRepository.findById(id);
        log.info("是否为空"+oicd.isPresent());
        InsuranceClaim icd = oicd.get();
        String json = icdParseJson(icd,l);
        log.info("JSON"+json);
        return json;
    }


    @RequestMapping(value="/employee/admin/feedback/precondition",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String ResponsePrecondition(@RequestBody ClaimIDRequest req) throws JSONException {
        Long id = Long.parseLong(req.getClaimID());
        Optional<Feedback> feedbackOptional = feedbackRepository.findByClaim(insuranceClaimRepository.findById(id).get());
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();

        if(feedbackOptional.isPresent()&&!feedbackOptional.get().getResult().equals("Undetermined")){
            jsonObject.put("resCode", "0");
            jsonObject.put("result",feedbackOptional.get().getResult());
            jsonObject.put("feedback",feedbackOptional.get().getFeedback());
        }
        else {
            jsonObject.put("resCode", "2");
        }
        return jsonObject.toString();
    }


    @RequestMapping(value="/employee/admin/feedback/request",  method = RequestMethod.POST)
    @ResponseBody
    public String ResponseFeedback(@RequestBody FeedbackRequest req) throws JSONException {
        Long id = Long.parseLong(req.getClaimID());
        String step = "";
        String ClaimResult = "";
        InsuranceClaim insuranceClaim = insuranceClaimRepository.findById(id).get();
        Optional<Feedback> feedbackOptional = feedbackRepository.findByClaim(insuranceClaim);
        Feedback feedback = new Feedback();
        if(feedbackOptional.isPresent()) {
            feedback = feedbackOptional.get();
        }
        else{
            feedback.setClaim(insuranceClaim);
        }
            feedback.setFeedback(req.getFeedback());
            feedback.setResult(req.getResult());
        if(req.getResult().equals("Undetermined")){
           step = "3";
           insuranceClaim.setClaimStep(step);
           insuranceClaim.setResult(req.getResult());
        }
        else if(req.getResult().equals("Accepted")){
            step = "4";
            ClaimResult = "success";
            insuranceClaim.setClaimStep(step);
            insuranceClaim.setResult(ClaimResult);

        }
        else{
            step = "4";
            ClaimResult = "fail";
            insuranceClaim.setClaimStep(step);
            insuranceClaim.setResult(ClaimResult);
        }
        feedbackRepository.save(feedback);
        insuranceClaimRepository.save(insuranceClaim);
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        jsonObject.put("resCode", "0");
        if(step.equals("4")){
            jsonObject.put("result","4");
        }
        return jsonObject.toString();
    }

    public String icdParseJson(InsuranceClaim info,Locale locale) throws JSONException {
        JSONObject jsonObject = null;
        jsonObject = new JSONObject();
        jsonObject.put("ClaimID", info.getId());
        if(info.getUser()==null){
            jsonObject.put("CustomerID", "unknown");
            jsonObject.put("CustomerName", "unknown");
        }
        else{
            jsonObject.put("CustomerID", info.getUser().getId());
            jsonObject.put("CustomerName", info.getUser().getUserName());
        }
        LostItem lostItem = info.getLostItem();
        Optional<ItemType> itemTypeOptional = itemTypeRepository.findById(lostItem.getTypeId());
        if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
            jsonObject.put("InsuranceName", info.getPolicy().getInsuranceProduct().getInsuranceChineseName());
            jsonObject.put("ItemType", itemTypeOptional.get().getChineseName());
        }
        else{
            jsonObject.put("InsuranceName", info.getPolicy().getInsuranceProduct().getInsuranceName());
            jsonObject.put("ItemType", itemTypeOptional.get().getEnglishName());
        }
        jsonObject.put("InsuranceID",info.getPolicy().getId());
        jsonObject.put("InsuranceStartDatetime",info.getPolicy().getStartDatetime());
        jsonObject.put("InsuranceEndDatetime",info.getPolicy().getEndDatetime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonObject.put("StartTime", df.format(info.getTime()));
        jsonObject.put("Status", info.getClaimStep());
        jsonObject.put("ItemName", lostItem.getItemName());
        jsonObject.put("ItemPrice", lostItem.getItemPrice());
        jsonObject.put("ItemDescription", lostItem.getItemDescription());
        jsonObject.put("Email", lostItem.getContactEmail());
        return jsonObject.toString();
    }


    public String icParseJson(List<InsuranceClaim> items,Locale locale) throws JSONException {
        if (items == null) return "";
        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        InsuranceClaim info = null;
        for (int i = 0; i < items.size(); i++) {
            info = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("ClaimID", info.getId());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonObject.put("StartTime", df.format(info.getTime()));
            if(info.getUser()== null){
                jsonObject.put("CustomerID", "null");
            }
            else{
                jsonObject.put("CustomerID", info.getUser().getId());

            }
            Optional<ItemType> itemTypeOptional = itemTypeRepository.findById(info.getLostItem().getTypeId());
            jsonObject.put("Email", info.getLostItem().getContactEmail());
            jsonObject.put("Status", info.getClaimStep());
            if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
                jsonObject.put("Detail", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>细节</button>");
                jsonObject.put("InsuranceName", info.getPolicy().getInsuranceProduct().getInsuranceChineseName());
                jsonObject.put("ItemType", itemTypeOptional.get().getChineseName());
            }
            else {
                jsonObject.put("InsuranceName", info.getPolicy().getInsuranceProduct().getInsuranceName());
                jsonObject.put("Detail", "<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
                jsonObject.put("ItemType", itemTypeOptional.get().getEnglishName());
            }
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }

    public String ippParseJson(List<InsurancePolicyProducts> items,Locale locale) throws JSONException {
        if (items == null) return "";
        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        InsurancePolicyProducts info = null;
        if(locale.equals(Locale.SIMPLIFIED_CHINESE)) {
        for (int i = 0; i < items.size(); i++) {
            info = items.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("InsuranceID", info.getId());
            jsonObject.put("InsuranceName", info.getInsuranceChineseName());
            jsonObject.put("Abstract", info.getInsuranceAbstractChinese());
            data.put(jsonObject);
        }
        }
        else {
            for (int i = 0; i < items.size(); i++) {
                info = items.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("InsuranceID", info.getId());
                jsonObject.put("InsuranceName", info.getInsuranceName());
                jsonObject.put("Abstract", info.getInsuranceAbstract());
                data.put(jsonObject);
            }
        }
        array.put("data",data);
        return array.toString();
    }



    public String parseJson(List<AppUser> items) throws JSONException {
        if (items == null) return "";
        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
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
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }

}
