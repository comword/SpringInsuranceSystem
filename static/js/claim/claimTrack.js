
let translationCn = {
    "年" : "/",
    "月" : "/",
    "日" : "",
    "索赔受理中" : "Claim is being reviewed",
    "需要更多信息": "Additional information required",
    "索赔成功" : "Success!",
    "索赔失败" : "Failed!",
};
let translationUs = {
    "The claim order record was not found, please try again." : "索赔单号没有找到,请输入一个有效的索赔单号!"
};

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
        username: '',
        isMobile: false,
        showDetail:false,
        html: "wait",
        test: message,
        info: "",
        errors: '',
        submit: [
            {claimOrderNum:''},
            {firstName:''},
            {lastName:''},
            {policyNum:''},
            {phone:''}
        ],
        nowTime: '',
        claimOrderNum: 'CZY1904137777',
        claimOrder: [],
        app_lang: '',
        res: [],
        color:'',
        title:'',

    },

    methods:{
        getUrlKey(name){
            return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null
        },
        _T(msg) {
            if(this.app_lang === "zh-CN")
                return msg;
            return translationCn[msg];
        },
        _TUs(msg) {
            if(this.app_lang !== "zh-CN")
                return msg;
            return translationUs[msg];
        },
        validPrice(num){
            var re = /(^[1-9]\d*$)/;
            return re.test(num);
        },
        timeFormate(timeStamp) {
            let year = new Date(timeStamp).getFullYear();
            let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
            let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
            let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
            let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
            // let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
            this.nowTime = year + this._T("年") + month + this._T("月") + date +this._T("日")+" "+hh+":"+mm;
        },
        nowTimes(){
            this.timeFormate(new Date());
            setInterval(this.nowTimes,10*1000);
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
        this.nowTimes();

        this.app_lang=this.getUrlKey("lang");
    }
});
$(document).ready(function(){
    const navbar = new Vue({
        el: '#menu-nav',
        data: {
            islogin: false,
            username: ''
        },
        mounted: function () {
            let self = this;
            this.$http.get('/userinfo/username').then(response => {
                if(response.body.status === 1) { //have login
                self.islogin = true;
                self.username = response.body.displayName;
            }
        });
        }
    });
    });
$("#search").click(function (){
    var info = main.submit;
    var isCorrect = true;

    /*   验证是否为空   */
    if(!info.claimOrderNum || !main.validPrice(info.claimOrderNum)){
        isCorrect=false;
        main.setInvalid('#policyNumber1');
        main.errors='';
    }
    else{
        main.removeInvalid('#policyNumber1');
    }
    if(!isCorrect)
        return;
    var d = {};
    d.claimOrderNum = info.claimOrderNum;
    $.ajax({ url: "claimTrack/claimOrderNum",
        data: JSON.stringify(d),
        //type、contentType必填,指明传参方式
        type: "POST",
        contentType: "application/json;charset=utf-8",
        success: function(response){
            main.claimOrder = new Function("return" + response)();
            //前端调用成功后，可以处理后端传回的json格式数据。
            if(main.claimOrder.resCode==='0'){
                main.errors='';
                if(main.claimOrder.step==='2'){
                    main.color="text-info";
                    main.title=main._T("索赔受理中");//Claim is being reviewed
                }
                else if(main.claimOrder.step==='3'){
                    main.color="text-warning";
                    main.title=main._T("需要更多信息");//Additional information required
                }
                else{
                    if(main.claimOrder.result==='success'){
                        main.color="text-success";
                        main.title=main._T("索赔成功");//Success
                    }
                    else{
                        main.color="text-danger";
                        main.title=main._T("索赔失败");//Failed
                    }
                }
            }
            else{
                main.errors= main._TUs(main.claimOrder.message);
            }
        },
        error:function(jqXHR){
            console.log("Error: "+ jqXHR.status);
            main.errors='';
        }
    });

});


