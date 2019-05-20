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
            "url": "page/uploadInsuranceInfo",
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
        "scrollX": true,
        language: {
            "decimal": "",
            "emptyTable": "没有数据",
            "info": "显示 _START_ 到 _END_ 页共 _TOTAL_ 条",
            "infoEmpty": "显示 0 到 0 页共 0 条",
            "infoFiltered": "(filtered from _MAX_ total entries)",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu": "显示 _MENU_ 条",
            "loadingRecords": "加载中...",
            "processing": "处理中...",
            "search": "搜索:",
            "zeroRecords": "没有匹配项",
            "paginate": { "first": "First", "last": "Last", "next": "下页", "previous": "上页" },
        },
        }
    );
    $('#dtMaterialDesignExample_wrapper').find('label').each(function () {
        $(this).parent().append($(this).children());
    });
    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('input').each(function () {
        $('input').attr("placeholder", "搜索");
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
        +'<th className="th-sm">保险单号 </th>'
        +'<th className="th-sm">保险种类 </th>'
        +'<th className="th-sm">细节</th>'
        +'</tr>'
        +'</thead>'
        +'<tbody>'
        +'</tbody>'
   +'</table></div>',
};

var Detail = {template: '<div>' +
        '<h1 className="text-md-center">保险细节</h1>' +
        '       <div  id="accordion" >' +
        '            <div class="card">' +
        '                <div class="card-header bg-primary">' +
        '                    <a class="card-link  text-white" data-toggle="collapse" href="#collapseOne">' +
        '                        保单信息' +
        '                    </a>' +
        '                </div>' +
        '                <div id="collapseOne" class="collapse show" data-parent="#accordion">' +
        '                    <div class="card-body">' +
        '                        <div class="row">' +

        '                            <div class="infMargin insurnInf col-xs-12 col-md-3">保险单号:</div>' +
        '                            <div  class="infMargin col-md-4" >{{ $route.params.insuranceDetails.InsuranceNum }}</div>' +
        '                        </div>' +
        '                        <div class="row">' +

        '                            <div class="infMargin insurnInf col-xs-12 col-md-3">保险种类:</div>' +
        '                            <div  class="infMargin col-md-4" >{{ $route.params.insuranceDetails.InsuranceName }}</div>' +
        '                         </div>' +
        '                        <div class="row">' +

        '                            <div class="infMargin insurnInf col-xs-12 col-md-3">起始时间:</div>' +
        '                            <div  class="infMargin col-md-4" >{{ $route.params.insuranceDetails.StartTime }}</div>' +
        '                        </div>' +
        '                        <div class="row">' +

        '                            <div class="infMargin insurnInf col-xs-12 col-md-3">结束时间:</div>' +
        '                            <div class="infMargin col-md-4">{{ $route.params.insuranceDetails.EndTime }}</div>' +

        '                        </div>' +
        '' +
        '                        <div class="row">' +

        '                            <div class="infMargin insurnInf col-xs-12 col-md-3">目的地:</div>' +
        '                            <div class="infMargin col-md-4">{{ $route.params.insuranceDetails.Destination }}</div>' +

        '                        </div>' +
        '                        <div class="row">' +

        '                            <div class="infMargin insurnInf col-xs-12 col-md-3">总共花费:</div>' +
        '                            <div class="infMargin col-md-4">{{ $route.params.insuranceDetails.TotalPaid }}</div>' +

        '                        </div>' +
        '                    </div>' +
        '                </div>' +
        '            </div>' +
        '            <div class="card">' +
        '                <div class="card-header bg-primary">' +
        '                    <a class="collapsed card-link  text-white " data-toggle="collapse" href="#collapseTwo">' +
        '                        投保人信息' +
        '                    </a>' +
        '                </div>' +
        '                <div id="collapseTwo" class="collapse" data-parent="#accordion">' +
        '                    <div class="card-body">' +
        '                        <div class="row">' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">姓名:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.CustomerName }}</div>' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">号码:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.idNum }}</div>' +
        '                        </div>' +
        '                        <div class="row">' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">手机号:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.Phone }}</div>' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">邮箱号:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.Email }}</div>' +
        '                        </div>' +
        '                        <div class="row">' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">国家:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.Country }}</div>' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">省份:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.Province }}</div>' +
        '                        </div>' +
        '                        <div class="row">' +
        '                            <div class="infMargin insurnInf col-xs-12 col-md-2">邮编:</div>' +
        '                            <div class="infMargin col-xs-12 col-md-4">{{ $route.params.insuranceDetails.Zip }}</div>' +
        '                        </div>' +
        '                    </div>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '</div>'
};

const routes = [
    { path: '/customer/insurance/details',name: 'Detail', component: Detail },
    { path: '/customer/insurance', name: 'insuranceTable',component: insuranceTable }
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
        req:[
            {username: ""}
        ]
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
                insurance.req.username = self.username;
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

function clicke(x){
    var id=x.parentNode.parentNode.firstChild.textContent;
    var d = {};
    d.insuranceNum = id;
    // alert(d.insuranceNum);
    $.ajax({ url: "insucranceTrack/insuranceNum",
        data: JSON.stringify(d),
        //type、contentType必填,指明传参方式
        type: "POST",
        contentType: "application/json;charset=utf-8",
        success: function(response){
            // insurance.insuranceDetails = new Function("return" + response)();
           insurance.insuranceDetails = response;
            //前端调用成功后，可以处理后端传回的json格式数据。
            details();
        },
        error:function(jqXHR){
            console.log("Error: "+ jqXHR.status);
            claim.errors='';
        }
    });
}
function details(){
    insurance.$router.push({name :'Detail',params: { insuranceDetails: insurance.insuranceDetails }});
}


