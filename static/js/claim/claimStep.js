$.getScript('/js/claim/translation-cn.js');
let app_lang;

Vue.component('navbar-indicator', {
    props: ['username'],
    data: function () {
        return {
            avatarPath: 'https://mdbootstrap.com/img/Photos/Avatars/avatar-2.jpg'
        }
    },
    mounted: function () {

    },
    template: navbar_indicator_template
});
var main = new Vue({
    el:".main-whole",
    data:{
        step:1,
        islogin: false,
        isSearch: '',
        username: '',
        isMobile: false,
        showDetail:false,
        html: "wait",
        test: message,
        info: "",
        timeStamp:[],
        errors: [],
        submit: [
            {firstName:''},
            {lastName:''},
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
        claimOrder:[],
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
    watch:{
        firstName(newName,oldName){
            if(!newName){
                this.setInvalid('#firstName');
            }
            else{
                this.removeInvalid('#firstName');
            }
        },
        lastName(newName,oldName){
            if(!newName){
                this.setInvalid('#lastName');
            }
            else{
                this.removeInvalid('#lastName');
            }
        },
        policyNum(newName,oldName){
            if(!newName){
                this.setInvalid('#policyNum');
            }
            else{
                this.removeInvalid('#policyNum');
            }
        },
        phone(newName,oldName){
            if(!this.validPhone(newName)){
                this.setInvalid('#phone');
            }
            else{
                this.removeInvalid('#phone');
            }
        },
        itemType(newName,oldName){
            if(!newName){
                this.setInvalid('#itemType');
            }
            else{
                this.removeInvalid('#itemType');
            }
        },
        itemName(newName,oldName){
            if(!newName){
                this.setInvalid('#itemName');
            }
            else{
                this.removeInvalid('#itemName');
            }
        },
        itemPrice(newName,oldName){
            if(!this.validPrice(newName)){
                this.setInvalid('#itemPrice');
            }
            else{
                this.removeInvalid('#itemPrice');
            }
        },
        itemDescription(newName,oldName){
            if(!newName){
                this.setInvalid('#itemDesc');
            }
            else{
                this.removeInvalid('#itemDesc');
            }
        },
        contactEmail(newName,oldName){
            if(!this.validEmail(newName)){
                this.setInvalid('#itemEmail');
            }
            else{
                this.removeInvalid('#itemEmail');
            }
        },
    },
    computed: {
        firstName() {
            return this.submit.firstName;
        },
        lastName() {
            return this.submit.lastName;
        },
        policyNum() {
            return this.submit.policyNum;
        },
        phone() {
            return this.submit.phone;
        },
        itemType() {
            return this.claimSubmit.itemType;
        },
        itemName() {
            return this.claimSubmit.itemName;
        },
        itemPrice() {
            return this.claimSubmit.itemPrice;
        },
        itemDescription() {
            return this.claimSubmit.itemDescription;
        },
        contactEmail() {
            return this.claimSubmit.contactEmail;
        },

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
    const navbar = new Vue({
        el: '#menu-nav',
        data: {
            username: ''
        },
        mounted: function () {
            let self = this;
            this.$http.get('/userinfo/username').then(response => {
                if(response.body.status === 1) { //have login
                    self.username = response.body.displayName;
                } else {
                    //window.location.href = "/login";
                }
            });
        }
    });
    main.claimSubmit.itemType = 'cloth';
    $("#search").click(function (){
        main.errors = [];
        var info = main.submit;
        var isCorrect = true;
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


        /*   查询数据库保险信息   */
        var d = {};
        d.firstName = info.firstName;
        d.lastName = info.lastName;
        d.zipCode = info.zipCode;
        d.policyNum = info.policyNum;
        d.phone = info.phone;

        $.ajax({ url: "newclaim/insurance",
            data: JSON.stringify(d),
            //type、contentType必填,指明传参方式
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                main.info = new Function("return" + response)();
                //前端调用成功后，可以处理后端传回的json格式数据。
                if(main.info.resCode===0) {
                    main.step = main.step + 1;
                }
                else {
                    main.errors.push(_T(main.info.message));
                    main.isSearch = main.info.resCode;
                }
                },
            error:function(jqXHR){
                console.log("Error: "+ jqXHR.status);
            }

        });
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
        if(!isCorrect)
            return;
        var d = {};
        var date= new Date();
        d.itemType = info.itemType;
        d.itemName = info.itemName;
        d.itemPrice = info.itemPrice;
        d.itemDescription = info.itemDescription;
        d.contactEmail = info.contactEmail;
        d.timeStamps = main.timeStamp;
        d.date = date.getTime();
        d.username = main.username;
        d.policyNum = main.submit.policyNum;

        $.ajax({ url: "newclaim/ClaimItemInfo",
            data: JSON.stringify(d),
            //type、contentType必填,指明传参方式
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                main.claimOrder = new Function("return" + response)();
                //前端调用成功后，可以处理后端传回的json格式数据。
                if(main.claimOrder.resCode===0){
                    main.step = main.step+1;
                    main.result.claimOrderNum = main.claimOrder.claimOrderNum;
                    getClaimPrice();
                }
            },
            error:function(jqXHR){
                console.log("Error: "+ jqXHR.status);
            }
        });

    });

});

$(function () {
    initFileInput("input-id");
});
function initFileInput(ctrlName) {
    var control = $('#' + ctrlName);
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: "newclaim/image", //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        uploadExtraData:function(){//向后台传递参数
            var extraInfo={
                policy: main.submit.policyNum
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
        msgFilesTooMany: "Max number of Files({n}) Current number of Files{m}！"

    }).on('filepreupload', function(event, data, previewId, index) {     //上传中
        console.log('File is uploading');
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
        var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
        console.log(response);//打印出返回的json
        console.log(response.status);//打印出路径
        main.claimSubmit.hasPhoto = response.hasPhoto;
        main.timeStamp.push(response.timeStamp);
    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
        console.log('upload failed！'+data.status);
    })
};

