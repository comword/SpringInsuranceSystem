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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
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

    private Logger log= LoggerFactory.getLogger(CustomerResponseController.class);

    @Data
    private static class insuranceRequest {
        private String appUserName;
    }

    @Data
    private static class claimRequest {
        private String claimOrderNum;
    }

    @Data
    private static class claimSubmitRequest {
        private String itemType,itemName,itemPrice,itemDescription,contactEmail,policyNum,username;
    }


    @RequestMapping(value="/customer/page/uploadClaimInfo",  method = RequestMethod.POST)
    @ResponseBody
    public String uploadClaimInfo() throws JSONException {
//        AppUser appUser = userRepository.findAppUserByUserName(req.getAppUserName());
        AppUser appUser = userRepository.findAppUserByUserName("customer0");
       List<InsuranceClaim> claimList = insuranceClaimRepository.findAllByUser(appUser);
        return parseJson(claimList);
    }


    @RequestMapping(value="/customer/page/uploadInsuranceInfo",  method = RequestMethod.POST)
    @ResponseBody
    public String uploadInsuranceInfo() throws JSONException {
//        AppUser appUser = userRepository.findAppUserByUserName(req.getAppUserName());
        AppUser appUser = userRepository.findAppUserByUserName("customer0");
        List<InsurancePolicyRecord> insuranceList = insurancePolicyRecordRepository.findAllByCustomer(appUser.getDetails());
        return parseJsonInsurance(insuranceList);
    }


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
}