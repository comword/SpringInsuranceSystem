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

// SideNav Button Initialization
$(".button-collapse").sideNav();
// SideNav Scrollbar Initialization
var sideNavScrollbar = document.querySelector('.custom-scrollbar');
// var ps = new PerfectScrollbar(sideNavScrollbar);

var insurance = new Vue({
    el: '#main',
    data: {
        info:[],
        claimOrder:[],
        isMobile:""
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
$(document).ready(function () {
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
                main.username = self.username;
            } else {
                //window.location.href = "/login";
            }
        });
        }
    });
    $('#dtMaterialDesignExample').DataTable({
            "ajax": {
                "url": "page/uploadInsuranceInfo",
                "type": "POST"
            },
            "columns": [
                { "data": "InsuranceId" },
                { "data": "InsuranceName" },
                { "data": "button"}
            ]
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
});
function clicke(x){
    var id=x.parentNode.parentNode.firstChild.textContent;
    var d = {};
    d.insuranceId = id;
}



