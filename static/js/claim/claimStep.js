var main = new Vue({
    el:".main-whole",
    data:{
        step:stepBack,
        isMobile: false,
        showDetail:false,
        html: "wait",
        test: message,
        info: "",
        isSearch: 0,
        submit: [
            {firstName:''},
            {lastName:''},
            {zipCode:''},
            {policyNum:''},
            {phone:''}
        ],
        claimSubErr: 0,
        claimSubmit: [
            {itemType:''},
            {itemName:''},
            {itemPrice: ''},
            {itemDescription:''},
            {contactEmail:''},
            {hasPhoto: false}
        ],
        result:[
            {claimOrderNum: ''},
            {claimOrderPrice: NaN}
        ]
    },

    methods:{
        validPhone: function (phone) {
            var re = /^1(3|4|5|7|8)\d{9}$/;
            return re.test(phone);
        },
        validEmail: function (email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        },
        validPrice: function (price) {
            var re = /^\d+(\.\d{1,2})?$/;
            return re.test(price);
        },
       setInvalid: function (id) {
           $(id).addClass("is-invalid");
           $(id).removeClass("is-valid");
        },
        removeInvalid: function (id) {
            $(id).addClass("is-valid");
            $(id).removeClass("is-invalid");
        }

    },
    mounted:function () {
        if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
            this.isMobile = true;
        }
    }
});

function getClaimPrice(){
    main.result.claimOrderPrice = parseFloat(main.claimSubmit.itemPrice)*0.7;
}

$(document).ready(function(){
    $("#search").click(function (){
        var info = main.submit;
        var isCorrect = true;


        /*   验证是否为空   */
        if(!info.firstName){
           isCorrect=false;
            main.setInvalid('#firstName');
        }
        else{
            main.removeInvalid('#firstName');
        }
        if(!info.lastName){
            isCorrect=false;
            main.setInvalid('#lastName');
        }
        else{
            main.removeInvalid('#lastName');
        }
        if(!info.zipCode){
            isCorrect=false;
            main.setInvalid('#zipCode');
        }
        else{
            main.removeInvalid('#zipCode');
        }
        if(!info.policyNum){
            isCorrect=false;
            main.setInvalid('#policyNum');
        }
        else{
            main.removeInvalid('#policyNum');
        }
        /*   验证电话号是否格式正确   */
        if(!main.validPhone(info.phone)){
            isCorrect=false;
            main.setInvalid('#phone');
        }
        else{
            main.removeInvalid('#phone');
        }
        if(!isCorrect){
            main.isSearch = 0;
            return;
        }



        /*   查询数据库保险信息   */
        var d = {};
        d.firstName = info.firstName;
        d.lastName = info.lastName;
        d.zipCode = info.zipCode;
        d.policyNum = info.policyNum;
        d.phone = info.phone;

        $.ajax({ url: "/dev/upload/searchInsurance",
            data: JSON.stringify(d),
            //type、contentType必填,指明传参方式
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                main.info = new Function("return" + response)();
                //前端调用成功后，可以处理后端传回的json格式数据。
                if(main.info.resCode==="777"){
                    alert("nice");
                    main.step = main.step+1;
                    main.isSearch = 777;
                    alert("nice");
                    // main.info = JSON.parse(response);

                }
                else{
                    main.isSearch = 222;
                    alert("fuck");
                }
            } });
    });


    $("#submit").click(function (){
        var info = main.claimSubmit;
        var isCorrect = true;


        /*   验证是否为空   */
        if(!info.itemType){
            isCorrect=false;
            main.setInvalid('#itemType');
        }
        else{
            main.removeInvalid('#itemType');
        }
        if(!info.itemName){
            isCorrect=false;
            main.setInvalid('#itemName');
        }
        else{
            main.removeInvalid('#itemName');
        }
        if(!info.itemDescription){
            isCorrect=false;
            main.setInvalid('#itemDesc');
        }
        else{
            main.removeInvalid('#itemDesc');
        }
        /*   验证物品价格是否格式正确   */
        if(!main.validPrice(info.itemPrice)){
            isCorrect=false;
            main.setInvalid('#itemPrice');
        }
        else{
            main.removeInvalid('#itemPrice');
        }
        /*   验证email是否格式正确   */
        if(!main.validEmail(info.contactEmail)){
            isCorrect=false;
            main.setInvalid('#itemEmail');
        }
        else{
            main.removeInvalid('#itemEmail');
        }
        if(!main.claimSubmit.hasPhoto){
            main.claimSubErr = 222;
            isCorrect=false;
        }
        else{
            main.claimSubErr = 0;
        }
        if(!isCorrect){
            return;
        }


        var d = {};
        d.itemType = info.itemType;
        d.itemName = info.itemName;
        d.itemPrice = info.itemPrice;
        d.itemDescription = info.itemDescription;
        d.contactEmail = info.contactEmail;

        $.ajax({ url: "/dev/upload/UpLoadClaimItemInfo",
            data: JSON.stringify(d),
            //type、contentType必填,指明传参方式
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                main.claimOrder = new Function("return" + response)();
                //前端调用成功后，可以处理后端传回的json格式数据。
                if(main.claimOrder.resCode==="777"){
                    alert("nice");
                    main.step = main.step+1;
                    main.isSearch = 777;
                    main.result.claimOrderNum = main.claimOrder.claimOrderNum;
                    getClaimPrice();
                    alert("nice");
                }
                else{
                    main.isSearch = 222;
                    alert("fuck");
                }

            } });

    });







});








$(function () {
    initFileInput("input-id");
});
function initFileInput(ctrlName) {
    var control = $('#' + ctrlName);
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: "/dev/upload/UpLoadImage", //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        uploadExtraData:function(){//向后台传递参数
            var extraInfo={
                phone: main.submit.phone,
                firstName: main.submit.firstName,
                fuck: main.html
            };
            return extraInfo;
        },
        uploadAsync: true, //默认异步上传
        showUpload: true, //是否显示上传按钮
        showRemove : true, //显示移除按钮
        showPreview : true, //是否显示预览
        showCaption: false,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        maxFileCount: 5, //允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

    }).on('filepreupload', function(event, data, previewId, index) {     //上传中
        console.log('文件正在上传');
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
        var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
        console.log(response);//打印出返回的json
        console.log(response.status);//打印出路径
        main.claimSubmit.hasPhoto = response.hasPhoto;
    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
        console.log('文件上传失败！'+data.status);
    })
};

