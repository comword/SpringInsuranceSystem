package org.gtdev.webapps.iaatraesamhsaat.claimfunc;


import lombok.Data;
import org.gtdev.webapps.iaatraesamhsaat.configs.AppConfig;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.InsuranceClaimRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.dao.InsurancePolicyRecordRepository;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.CustomerDetails;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsuranceClaim;
import org.gtdev.webapps.iaatraesamhsaat.database.entities.InsurancePolicyRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClaimResponseController {
    @Autowired
    private InsurancePolicyRecordRepository insurancePolicyRecordRepository;

    @Autowired
    private InsuranceClaimRepository insuranceClaimRepository;

    @Autowired
    private AppConfig.DataPathConfig dataPathConfig;

    private Logger log= LoggerFactory.getLogger(ClaimResponseController.class);

    @Data
    private static class insuranceRequest {
        private String firstName, lastName, policyNum, phone;
    }

    @Data
    private static class claimRequest {
        private String claimOrderNum;
    }

    @RequestMapping(value = "/claim/newclaim/insurance", method = RequestMethod.POST)
    @ResponseBody
    public String search(@RequestBody insuranceRequest req) throws JSONException {
        log.info("Searching insurance: " + req.toString());
        JSONObject result = new JSONObject();
        Optional<InsurancePolicyRecord> ipr = insurancePolicyRecordRepository.findById(req.getPolicyNum());
        if(!ipr.isPresent()){
            result.put("message", "The insurance policy record was not found, please try again.");
            result.put("resCode", "-2100");
        } else {

            // Check customer's name
            CustomerDetails details = ipr.get().getCustomer();
            if(!details.getFirstName().equals(req.getFirstName()) || !details.getLastName().equals(req.getLastName())) {
                result.put("message", "The insured individual in policy record has a different name, please try again.");
                result.put("resCode", "-2101");
                return result.toString();
            }
            result.put("startDate", ipr.get().getStartDatetime());
            result.put("endDate", ipr.get().getEndDatetime());
            result.put("destination", ipr.get().getDestination());

            result.put("name", details.getFirstName()+";"+details.getLastName());
            result.put("idNum", details.getIdNumber());
            result.put("phone", req.getPhone());
            result.put("country", details.getCountryStr());
            result.put("zip",details.getZipCode());
            result.put("email", details.getUser().getEmail());
            result.put("city", details.getProvince());

            result.put("resCode", 0);
            result.put("message", "OK");

            result.put("idType", "ID card");
            result.put("birthDay", "1992-10-21");
            result.put("gender", "Male");
        }
        return result.toString();
//        result.put("message", "OK");
//        result.put("resCode", "777");
////        result.put("resCode","222");
//
//        result.put("startDate", "2019-03-29");
//        result.put("endDate", "2019-03-30");
//        result.put("destination", "Ireland");
//        result.put("method", "legal heir");

//        result.put("name", "fffffff");
//        result.put("idType", "ID card");
//        result.put("birthDay", "1992-10-21");
//        result.put("phone", "18611111111");
//        result.put("country", "China");
//        result.put("zipCode", "100000");
//
//        result.put("gender", "male");
//        result.put("idNum", "123131312313131321");
//        result.put("email", "test0@hibernia-sino.com");
//        result.put("city", "Beijing");
//        return result.toString();
    }

    @RequestMapping(value = "/claim/newclaim/image", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadImage(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("status", 400);
        String policyNum =request.getParameter("policy");// 接受上传图片时的额外参数 对应 uploadExtraData:function()
        log.info("The policy number: "+policyNum);
        if(file!=null&&file.length>0){
            //组合image名称，“;隔开”
            List<String> fileName =new ArrayList<String>();
            PrintWriter out = null;

            try {
                String[] typeImg={"gif","png","jpg"};
                for(int k = 0; k < file.length; k++){
                    if (!file[k].isEmpty()) {
                        MultipartFile file1 = file[k];
                        if(file1!=null){
                            String origName=file1.getOriginalFilename();// 文件原名称
                            log.info("File original name: "+origName);
                            // 判断文件类型
                            String type=origName.indexOf(".")!=-1?origName.substring(origName.lastIndexOf(".")+1, origName.length()):null;
                            if (type!=null) {
                                boolean booIsType=false;
                                for (int i = 0; i < typeImg.length; i++) {
                                    if (typeImg[i].equals(type.toLowerCase())) {
                                        booIsType=true;
                                    }
                                }
                                //类型正确
                                if (booIsType) {
                                    //存放图片文件的路径
                                    String path = dataPathConfig.getUploadPath() + File.pathSeparator + policyNum + File.pathSeparator;
                                    log.info("文件上传的路径"+path);
                                    //组合名称
                                    String fileSrc = path;
                                    File targetFile=new File(fileSrc,origName);
                                    //上传
                                    if(!targetFile.exists()){
                                        targetFile.getParentFile().mkdirs();//创建目录
                                    }
                                    log.info("完整路径:" + targetFile.getAbsolutePath());
                                    file1.transferTo(targetFile);
                                }
                            }
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            try {
                resultMap.put("status", 200);
                resultMap.put("message", "上传成功！");
                resultMap.put("hasPhoto",true);

            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("status", 500);
                resultMap.put("message", "上传异常！");
            }
        }
        else {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
        }
        return resultMap;
    }

    @RequestMapping(value="/claim/newclaim/ClaimItemInfo",  method = RequestMethod.POST)
    @ResponseBody
    public String uploadInfo(@RequestBody Map<String,Object> map1) throws JSONException {

        JSONObject result = new JSONObject();

//      这个是数据搜寻是否成功
        result.put("message", "更新成功了");
        result.put("resCode", 0);             //随便定义的响应参数
//        result.put("resCode","222");              //随便定义的响应参数

//      这个是保单索赔信息 要与保险单号关联
        result.put("claimOrderNum","816385292874222");
        return result.toString();

    }



    @RequestMapping(value="/claim/claimTrack/claimOrderNum",  method = RequestMethod.POST)
    @ResponseBody
    public String claimOrderNum(@RequestBody claimRequest req) throws JSONException {
        Optional<InsuranceClaim> ic = insuranceClaimRepository.findById(Long.parseLong(req.claimOrderNum));
        JSONObject result = new JSONObject();
//        result.put("resCode", 0);
        log.info("Claim: " + req.toString());
        if(!ic.isPresent()){//如果没有找到索赔单号
            result.put("resCode","-2002");
            result.put("message","The claim order record was not found, please try again.");
        }
        else{//找到索赔单号
            result.put("resCode","0");
            result.put("claimOrderNum",ic.get().getId());//索赔单号
//            result.put("step","2");             //目前所在的阶段 审核中....
//            result.put("step","3");             //目前所在的阶段 需要额外的信息
            result.put("step",ic.get().getClaimStep());             //目前所在的阶段 成功或者失败
            if(result.getString("step").equals("4")){
                result.put("result",ic.get().getResult());
//                result.put("result","fail");
            }
        }
        return result.toString();
    }
}



