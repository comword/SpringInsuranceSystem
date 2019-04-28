package org.gtdev.webapps.iaatraesamhsaat.claimfunc;


import lombok.Data;
import org.gtdev.webapps.iaatraesamhsaat.configs.AppConfig;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.AppUserRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.InsuranceClaimRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.InsurancePolicyRecordRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.LostItemRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class CustomerResponseController {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;
    @Autowired
    private InsurancePolicyRecordRepository insurancePolicyRecordRepository;
    @Autowired
    private LostItemRepository lostItemRepository;
    @Autowired
    private AppConfig.DataPathConfig dataPathConfig;

    private Logger log = LoggerFactory.getLogger(this.getClass());
//    @Data
//    private static class insuranceRequest {
//        private String customer;
//    }
//    @Data
//    private static class claimRequest {
//        private String claimOrderNum;
//    }
//    @Data
//    private static class insuranceNumRequest {
//        private String insuranceNum;
//    }
    @Data
    private static class claimSubmitRequest {
        private String itemType,itemName,itemPrice,itemDescription,contactEmail,policyNum,username;
    }

    @RequestMapping(value={"/customer/claimTrack/claimOrderNum"},  method = RequestMethod.POST)
    @ResponseBody
    public String claimOrderNum(@RequestParam Long claimOrderNum) throws JSONException {
        Optional<InsuranceClaim> ic = insuranceClaimRepository.findById(claimOrderNum);
        JSONObject result = new JSONObject();
        if(!ic.isPresent()){
            result.put("resCode","-2000");
            return result.toString();
        } else {
            result.put("resCode","0");
            result.put("claimOrderNum",ic.get().getId());//索赔单号
            result.put("step",ic.get().getClaimStep());//目前所在的阶段 成功或者失败
            result.put("date",ic.get().getDateTime());
            if(ic.get().getClaimStep() == 4)
                result.put("result", ic.get().getResult());
            return result.toString();
        }
    }

    @RequestMapping(value="/customer/page/uploadClaimInfo",  method = RequestMethod.POST)
    @ResponseBody
    public String uploadClaimInfo(@RequestParam String customer) throws JSONException {
        AppUser appUser = userRepository.findAppUserByUserName(customer);
        List<InsuranceClaim> claimList = insuranceClaimRepository.findAllByUser(appUser);
        return parseJson(claimList);
    }

    @RequestMapping(value="/customer/insucranceTrack/insuranceNum",  method = RequestMethod.POST)
    @ResponseBody
    public String insuranceNum(@RequestParam String insuranceNum) throws JSONException {
        Optional<InsurancePolicyRecord> ipr = insurancePolicyRecordRepository.findById(insuranceNum);
        JSONObject result = new JSONObject();
        if(!ipr.isPresent()){
            result.put("resCode","-2000");
            return result.toString();
        } else {
            CustomerDetails details = ipr.get().getCustomer();
            result.put("resCode","0");
            result.put("InsuranceName", ipr.get().getInsuranceProduct().getInsuranceName());
            result.put("InsuranceNum", ipr.get().getId());
            result.put("TotalPaid", ipr.get().getTotalPaid());
            result.put("StartTime", ipr.get().getStartDatetime());
            result.put("EndTime", ipr.get().getEndDatetime());
            result.put("Destination", ipr.get().getDestination());
            result.put("CustomerName", details.getFirstName() + ";" + details.getLastName());
            result.put("idNum", details.getIdNumber());
            result.put("Country", details.getCountryStr());
            result.put("Province", details.getProvince());
            result.put("Zip", details.getZipCode());
            result.put("Email", details.getUser().getEmail());
            result.put("Phone", details.getPhoneNumber());
            return result.toString();
        }
    }

    @RequestMapping(value="/customer/page/uploadInsuranceInfo",  method = RequestMethod.POST)
    @ResponseBody
    public String uploadInsuranceInfo(@RequestParam String customer) throws JSONException {
        AppUser appUser = userRepository.findAppUserByUserName(customer);
        if(appUser == null){
            JSONObject result = new JSONObject();
            result.put("resCode","-2000");
            return result.toString();
        } else {
            List<InsurancePolicyRecord> insuranceList = insurancePolicyRecordRepository.findAllByCustomer(appUser.getDetails());
            return parseJsonInsurance(insuranceList);
        }
    }

//    @RequestMapping(value="/customer/page/uploadInsuranceInfoInClaim",  method = RequestMethod.POST)
//    @ResponseBody
//    public String uploadInsuranceInfoInClaim(@RequestParam String customer) throws JSONException {
//        AppUser appUser = userRepository.findAppUserByUserName(customer);
//        List<InsurancePolicyRecord> insuranceList = insurancePolicyRecordRepository.findAllByCustomer(appUser.getDetails());
//        return parseJsonInsurance(insuranceList);
//    }

    public String parseJson(List<InsuranceClaim> claimList) throws JSONException {
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
            jsonObject.put("InsuranceName",claim.getPolicy().getInsuranceProduct().getInsuranceName());
            jsonObject.put("button","<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
//            jsonObject.put("button","<button class=\"btn btn-info btn-rounded\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }

    public String parseJsonInsurance(List<InsurancePolicyRecord> insuranceList) throws JSONException {
        if (insuranceList == null) return "";
        JSONObject array = new JSONObject();
        JSONObject jsonObject = null;
        JSONArray data = new JSONArray();
        InsurancePolicyRecord insurancePolicyRecord = null;
        for (int i = 0; i < insuranceList.size(); i++) {
            insurancePolicyRecord = insuranceList.get(i);
            jsonObject = new JSONObject();
            jsonObject.put("InsuranceId",insurancePolicyRecord.getId());
            jsonObject.put("InsuranceName",insurancePolicyRecord.getInsuranceProduct().getInsuranceName());
            jsonObject.put("button","<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Details</button>");
            data.put(jsonObject);
        }
        array.put("data",data);
        return array.toString();
    }

//    public String parseJsonInsuranceInClaim(List<InsurancePolicyRecord> insuranceList) throws JSONException {
//        if (insuranceList == null) return "";
//        JSONObject array = new JSONObject();
//        JSONObject jsonObject = null;
//        JSONArray data = new JSONArray();
//        InsurancePolicyRecord insurancePolicyRecord = null;
//        for (int i = 0; i < insuranceList.size(); i++) {
//            insurancePolicyRecord = insuranceList.get(i);
//            jsonObject = new JSONObject();
//            jsonObject.put("InsuranceId",insurancePolicyRecord.getId());
//            jsonObject.put("InsuranceName",insurancePolicyRecord.getInsuranceProduct().getInsuranceName());
//            jsonObject.put("button","<button class=\"btn btn-info btn-rounded\" id=\"detail\"  data-toggle=\"modal\" data-target=\"#exampleModalPreview\" onclick = \"clicke(this);\"><i class=\"fas fa-magic mr-1\"></i>Claim</button>");
//            data.put(jsonObject);
//        }
//        array.put("data",data);
//        return array.toString();
//    }

}
