package org.gtdev.webapps.iaatraesamhsaat.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;


@Controller
@RequestMapping(value = "/upload")
public class ClaimResponseController {
    private static Logger log= LoggerFactory.getLogger(HelloController.class);
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();




    /*    返回JSON
            {firstName:''},
            {lastName:''},
            {zipCode:''},
            {policyNum:''},
            {phone:''}
       传回JSON


       */
    @RequestMapping(value="/searchInsurance")
    @ResponseBody
    public Object search(@RequestBody Map<String,Object> map1) throws JSONException {

        JSONObject result = new JSONObject();

//      这个是数据搜寻是否成功
        result.put("message", "更新成功了");
        result.put("resCode", "777");//随便定义的响应参数
//        result.put("resCode","222");              //随便定义的响应参数

//      这个是保单信息
        result.put("startDate", "2019-03-29");
        result.put("endDate", "2019-03-30");
        result.put("destination", "爱尔兰 Ireland");
        result.put("method", "法定继承人");
//      这个是详细信息
        result.put("name", "冯鑫驰");
        result.put("certificate", "身份证");
        result.put("birthDay", "1998-02-20");
        result.put("phone", "18645068587");
        result.put("country", "中国");
        result.put("zipCode", "100777");


        result.put("gender", "男");
        result.put("certificateNum", "230103199802200919");
        result.put("email", "1300358325@qq.com");
        result.put("city", "哈尔滨");


        return result.toString();
    }

    @RequestMapping(value = "/UpLoadImage")
    @ResponseBody
    public Map<String,Object> uploadImage(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile[] file) throws Exception{
        resultMap.put("status", 400);
        String phone =request.getParameter("phone");// 接受上传图片时的额外参数 对应 uploadExtraData:function()
        System.out.println(phone);
        if(file!=null&&file.length>0){
            //组合image名称，“;隔开”
            List<String> fileName =new ArrayList<String>();
            PrintWriter out = null;
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



    @RequestMapping(value="/UpLoadClaimItemInfo")
    @ResponseBody
    public Object uploadInfo(@RequestBody Map<String,Object> map1) throws JSONException {

        JSONObject result = new JSONObject();

//      这个是数据搜寻是否成功
        result.put("message", "更新成功了");
        result.put("resCode", "777");             //随便定义的响应参数
//        result.put("resCode","222");              //随便定义的响应参数

//      这个是保单索赔信息 要与保险单号关联
        result.put("claimOrderNum","816385292874222");
        return result.toString();
    }






}

