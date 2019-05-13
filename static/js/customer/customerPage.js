let translationCn = {
    "年" : "/",
    "月" : "/",
    "日" : "",
    "索赔受理中" : "Claim is being reviewed",
    "需要更多信息": "Additional information required",
    "索赔成功" : "Success!",
    "索赔失败" : "Failed!",
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






var claim = new Vue({
    el: '#main',
    data: {
        info:[],
        claimOrder:[],
        title: "",
        isMobile:"",
        color:"",
    },
    methods:{
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
        timeFormate(timeStamp) {
            let year = new Date(timeStamp).getFullYear();
            let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
            let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
            let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
            let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
            let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
            return year + "-" + month + "-" + date + " " + hh + ":" + mm + ":" + ss;
        }

    },
    mounted:function () {
        if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
            this.isMobile = true;
        }
    }
});
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
                claim.username = self.username;
                    $('#dtMaterialDesignExample').DataTable({
                            "ajax": {
                                "url": "page/uploadClaimInfo",
                                "type": "POST",
                                "contentType": "application/json",
                                "data": function (d) {
                                    d = {};
                                    d.customer = claim.username;
                                    return JSON.stringify(d);
                                }
                            },
                            "columns": [
                                { "data": "claimID" },
                                { "data": "InsuranceId" },
                                { "data": "InsuranceName" },
                                { "data": "Step" },
                                { "data": "button"}
                            ],
                            "scrollX": true
                        }
                    );
                    $('#dtMaterialDesignExample_wrapper').find('label').each(function () {
                        $(this).parent().append($(this).children());
                    });
                    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('input').each(function () {
                        $('input').attr("placeholder", "Search");
                        $('input').removeClass('form-control-sm');
                    });
                    $('#dtMaterialDesignExample_wrapper .dataTables_length').addClass('d-flex flex-row');
                    $('#dtMaterialDesignExample_wrapper .dataTables_filter').addClass('md-form');
                    $('#dtMaterialDesignExample_wrapper select').removeClass(
                        'custom-select custom-select-sm form-control form-control-sm');
                    $('#dtMaterialDesignExample_wrapper select').addClass('mdb-select');
                    $('#dtMaterialDesignExample_wrapper .mdb-select').materialSelect();
                    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('label').remove();
                    // SideNav Button Initialization
                    $(".button-collapse").sideNav();
// SideNav Scrollbar Initialization
                    var sideNavScrollbar = document.querySelector('.custom-scrollbar');
                    var ps = new PerfectScrollbar(sideNavScrollbar);

                } else {
                window.location.href = "/login";
            }
        });
        }
    });

function clicke(x){
    var id=x.parentNode.parentNode.firstChild.textContent;
    var d = {};
    d.claimOrderNum = id;
    // d.claimOrderNum = "1";
    $.ajax({ url: "claimTrack/claimOrderNum",
        data: JSON.stringify(d),
        //type、contentType必填,指明传参方式
        type: "POST",
        contentType: "application/json;charset=utf-8",
        success: function(response){
            claim.claimOrder = new Function("return" + response)();
            //前端调用成功后，可以处理后端传回的json格式数据。
            if(claim.claimOrder.resCode==='0'){
                claim.errors='';
                if(claim.claimOrder.step==='2'){
                    claim.color="text-info";
                    claim.title=claim._T("索赔受理中");//Claim is being reviewed
                }
                else if(claim.claimOrder.step==='3'){
                    claim.color="text-warning";
                    claim.title=claim._T("需要更多信息");//Additional information required
                }
                else{
                    if(claim.claimOrder.result==='success'){
                        claim.color="text-success";
                        claim.title=claim._T("索赔成功");//Success
                    }
                    else{
                        claim.color="text-danger";
                        claim.title=claim._T("索赔失败");//Failed
                    }
                }
                claim.claimOrder.StartDate = claim.claimOrder.date;
                 var time = claim.claimOrder.date;
                // claim.claimOrder.StartDate = claim.timeFormate(parseInt(claim.claimOrder.date));
                var date = new Date();
                claim.claimOrder.NowDate = claim.timeFormate(date.getTime());
            }
            else{
                claim.errors= claim._TUs(claim.claimOrder.message);
            }
        },
        error:function(jqXHR){
            console.log("Error: "+ jqXHR.status);
            claim.errors='';
        }
    });

}



