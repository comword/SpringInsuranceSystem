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



function table(){
    $('#dtMaterialDesignExample').DataTable({
        "ajax": {
            "url": "page/uploadInsuranceInfoInClaim",
            "type": "POST",
            "contentType": "application/json",
            "data": function (d) {
                d = {};
                d.customer = insurance.username;
                return JSON.stringify(d);
            }
        },
        "columns": [
            { "data": "InsuranceId" },
            { "data": "InsuranceName" },
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
    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('label').remove();}
var insuranceTable = {template: '<div><table id="dtMaterialDesignExample" class="table table-striped table-bordered table-sm" cellSpacing="0" width="100%">'
        +'<thead>'
    +'<tr>'
        +'<th className="th-sm">Insurance ID </th>'
        +'<th className="th-sm">Insurance Type </th>'
        +'<th className="th-sm">Details</th>'
        +'</tr>'
        +'</thead>'
        +'<tbody>'
        +'</tbody>'
   +'</table></div>',
};

var Detail = {template: '<div>' +
        '<h1 className="text-md-center">Claim</h1>' +
        '<div class="container-fluid form-body">' +
        '                            <transition name="fade">' +
        '                                <div v-show="claimSubErr===222" class="col-xs-8 form-group row"  >' +
        '                                    <div class="alert alert-danger" style="width: 100%;">' +
        '                                        <strong>No photos!</strong> Please upload photos again.' +
        '                                    </div>' +
        '                                </div>' +
        '                            </transition>' +
        '                            <form class="needs-validation" novalidate>' +
        '                                <div class="container-fluid">' +
        '                                    <div class="form-group row">' +
        '                                        <label class="col-form-label col-xs-8 col-md-3" for="itemType">Item Type:</label>' +
        '                                        <div class="col-md-8">' +
        '                                            <select v-model="claimSubmit.itemType" class="form-control browser-default custom-select" id="itemType" required>' +
        '                                                <option selected value="cloth">cloth</option>' +
        '                                                <option value="digital-product">digital product</option>' +
        '                                                <option value="document">document</option>' +
        '                                            </select>' +
        '                                            <div class="invalid-feedback" style="width: 100%;">' +
        '                                                Please enter a valid item type' +
        '                                            </div>' +
        '                                        </div>' +
        '                                    </div>' +
        '' +
        '                                    <div class="form-group row">' +
        '                                        <label class="col-form-label col-xs-8 col-md-3" for="itemName">Item Name:</label>' +
        '                                        <div class="col-md-8">' +
        '                                            <input v-model="claimSubmit.itemName"  type="text" class="form-control  col-xs-8 col-md-12 " id="itemName" placeholder="Ipad Pro 11.9" required>' +
        '                                            <div class="invalid-feedback" style="width: 100%;">' +
        '                                                Please enter a valid item name' +
        '                                            </div>' +
        '                                        </div>' +
        '                                    </div>' +
        '                                    <div class="form-group row">' +
        '                                        <label class="col-form-label col-xs-8 col-md-3" for="itemName">Item Price:</label>' +
        '                                        <div class="col-md-8">' +
        '                                            <input v-model="claimSubmit.itemPrice" type="text" class="form-control  col-xs-8 col-md-12 " id="itemPrice" placeholder="500/666.0/999.00" required>' +
        '                                            <div class="invalid-feedback" style="width: 100%;">' +
        '                                                Please enter a valid item price' +
        '                                            </div>' +
        '                                        </div>' +
        '                                    </div>' +
        '                                    <div class="form-group row">' +
        '                                        <label class="col-form-label col-xs-8 col-md-3" for="itemDesc">Item Description:</label>' +
        '                                        <div class="col-md-8">' +
        '                                            <textarea v-model="claimSubmit.itemDescription" class="form-control col-xs-8 col-md-12" id="itemDesc" placeholder="" required></textarea>' +
        '                                            <div class="invalid-feedback" style="width: 100%;">' +
        '                                                Please enter a valid item describe' +
        '                                            </div>' +
        '                                        </div>' +
        '                                    </div>' +
        '                                    <div class="form-group row">' +
        '                                        <label class="col-form-label col-xs-8 col-md-3" for="itemEmail">Email:</label>' +
        '                                        <div class="col-md-8">' +
        '                                            <input v-model="claimSubmit.contactEmail" type="email" class="form-control col-xs-8 col-md-12" id="itemEmail" placeholder="123@example.com" required>' +
        '                                            <div class="invalid-feedback" style="width: 100%;">' +
        '                                                Please enter a valid email' +
        '                                            </div>' +
        '                                        </div>' +
        '                                    </div>' +
        '                                </div>' +
        '                                <div class="form-group row upload-file">' +
        '                                    <label class="col-form-label col-md-4">Item Photo:</label>' +
        '                                    <div class="col-sm-12 col-md-12">' +
        '                                        <input class="is-invalid" id="input-id" name="file" multiple="multiple" type="file" data-show-caption="true" data-theme="fas" required>' +
        '                                        <div class="form-control invalid-feedback" style="width: 100%;">' +
        '                                            Please submit photo' +
        '                                        </div>' +
        '                                    </div>' +
        '                                </div>' +
        '                            </form>' +
        '                        </div>' +
        '<button type="button" id="submit" class="btn btn-lg btn-primary step-button">Submit</button>' +
        '</div>',
    methods:{
        validPrice: function (price) {
            var re = /^\d+(\.\d{1,2})?$/;
            return re.test(price);
        },
        validEmail: function (email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        },
        setInvalid: function (id) {
            $(id).addClass("is-invalid");
            $(id).removeClass("is-valid");
        },
        removeInvalid: function (id) {
            $(id).addClass("is-valid");
            $(id).removeClass("is-invalid");
        },
        getClaimPrice: function(){
             this.result.claimOrderPrice = parseFloat(this.claimSubmit.itemPrice)*0.7;
        },
        getTimeF: function(){
            var date= new Date();
            return date.getTime();
        },
        timeFormate(timeStamp) {
            let year = new Date(timeStamp).getFullYear();
            let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
            let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
            let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
            let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
            let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
            return year + "/" + month + "/" + date + "/ " + hh + ":" + mm + ":" + ss;
            }

    },
    data: function () {
        return {
            submit: [
                {firstName:''},
                {lastName:''},
                {policyNum:''},
                {phone:''}
            ],
            claimSubmit: [
                {itemType:''},
                {itemName:''},
                {itemPrice: ''},
                {itemDescription:''},
                {contactEmail:''},
                {hasPhoto: false}
            ],
            id: '',
            claimSubErr: 0
        }
    },
    mounted: function () {
        let that = this;
        $("#submit").click(function (){
            var info = that.claimSubmit;
            var isCorrect = true;
            /*   验证是否为空   */
            if(!info.itemType){
                isCorrect=false;
                that.setInvalid('#itemType');
            }
            else{
                that.removeInvalid('#itemType');
            }
            if(!info.itemName){
                isCorrect=false;
                that.setInvalid('#itemName');
            }
            else{
                that.removeInvalid('#itemName');
            }
            if(!info.itemDescription){
                isCorrect=false;
                that.setInvalid('#itemDesc');
            }
            else{
                that.removeInvalid('#itemDesc');
            }
            /*   验证物品价格是否格式正确   */
            if(!that.validPrice(info.itemPrice)){
                isCorrect=false;
                that.setInvalid('#itemPrice');
            }
            else{
                that.removeInvalid('#itemPrice');
            }
            /*   验证email是否格式正确   */
            if(!that.validEmail(info.contactEmail)){
                isCorrect=false;
                that.setInvalid('#itemEmail');
            }
            else{
                that.removeInvalid('#itemEmail');
            }
            if(!that.claimSubmit.hasPhoto){
                that.claimSubErr = 222;
                isCorrect=false;
            }
            else{
                that.claimSubErr = 0;
            }
            if(!isCorrect)
                return;
            var d = {};
            d.itemType = info.itemType;
            d.itemName = info.itemName;
            d.itemPrice = info.itemPrice;
            d.itemDescription = info.itemDescription;
            d.contactEmail = info.contactEmail;
            d.policyNum = that.id;
            d.date = that.getTimeF();
            // d.username = main.username;
            d.username = 'customer0';
            $.ajax({ url: "newclaim/ClaimItemInfo",
                data: JSON.stringify(d),
                //type、contentType必填,指明传参方式
                type: "POST",
                contentType: "application/json;charset=utf-8",
                success: function(response){
                    that.claimOrder = new Function("return" + response)();
                    //前端调用成功后，可以处理后端传回的json格式数据。
                    if(that.claimOrder.resCode===0){
                        that.result.claimOrderNum = that.claimOrder.claimOrderNum;
                        that.getClaimPrice();


                        var unixTimestamp = that.timeFormate(1477386005*1000);
                        alert(unixTimestamp);


                    }
                },
                error:function(jqXHR){
                    console.log("Error: "+ jqXHR.status);
                }
            });

        });


        this.id = this.$route.params.id;
        // alert(this.$route.params.id+'route');
        // alert(this.id);
        $(function () {
            initFileInput("input-id");
        });
        function initFileInput(ctrlName) {
            var control = $('#' + ctrlName);
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl: "/claim/newclaim/image", //上传的地址
                allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
                uploadExtraData:function(){//向后台传递参数
                    var extraInfo={
                        policy: insurance.id
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
                that.claimSubmit.hasPhoto = response.hasPhoto;
                alert(that.claimSubmit.hasPhoto);
            }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
                console.log('upload failed！'+data.status);
            })
        };
    },
    watch:{

    }
};

const routes = [
    { path: '/customer/Claim/ClaimStep',name: 'Detail', component: Detail },
    { path: '/customer/Claim', name: 'insuranceTable',component: insuranceTable }
];

const router = new VueRouter({
    mode: 'history',
    routes: routes // routes: routes 的简写
});

var insurance = new Vue({
    el: '#main',
    router: router,
    data: {
        info:[],
        insuranceDetails:[],
        isMobile:"",
        id:"",
        hasPhoto:"",
        username:""
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
                insurance.username = self.username;
                table();
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





function clicke(x) {
    var id = x.parentNode.parentNode.firstChild.textContent;
    insurance.id = id;
    var d = {};
    d.insuranceNum = id;
    details(id);
}
function details(id){
    alert(id);
    insurance.$router.push({name :'Detail',params: { id: id }});
}


