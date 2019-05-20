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
    el: '#main',
    data: {
        info:[],
        date:[],
        username: "",
        id:'',
        cost: 0,
        dobCost: 0,
        endDate:'',
        old:'',
        days:''
    },
    methods:{
        setInvalid: function(id){
            $(id).removeClass("is-valid");
            $(id).addClass("is-invalid");
        },
        removeInvalid: function (id) {
            $(id).addClass("is-valid");
            $(id).removeClass("is-invalid");
        },
    },
    computed: {
        Number1: function() {
            return this.cost.toFixed(0);
        },
        Number2: function() {
            return this.dobCost.toFixed(0);
        }
    },
    watch:{
        endDate(newName,oldName){
            if(!newName){
                this.setInvalid('#date-picker-example');
                this.cost = 0;
                this.dobCost = 0;
            }
            else{
                var date = newName.split('-');
                var end = new Date(date[0],date[1]-1,date[2]);
                var today = new Date(this.old[0],this.old[1]-1,this.old[2]);
                var iDays = Math.floor((end-today) / (24 * 3600 * 1000));
                this.days = iDays;
                var cost1 = iDays*0.5;
                var dobCost1 = iDays*1;
                TweenLite.to(this.$data, 1, { cost: cost1, dobCost: dobCost1 });
                this.removeInvalid('#date-picker-example');
            }
        },
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
                main.username = self.username;
                $('#dtMaterialDesignExample').DataTable({
                        "ajax": {
                            "url": "Renew/listRequest",
                            "contentType": "application/json",
                            "data": function (d) {
                                d = {};
                                d.username = self.username;
                                return JSON.stringify(d);
                            },
                            "type": "POST"
                        },
                        "columns": [
                            { "data": "PolicyID" },
                            { "data": "InsuranceName" },
                            { "data": "StartTime"},
                            { "data": "EndTime"},
                            { "data": "button"}
                        ],
                        "scrollX": true,
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
    var enddate=x.parentNode.parentNode.childNodes[3].textContent;
    var startdate=x.parentNode.parentNode.childNodes[2].textContent;
    main.id = id;
    var all = enddate.split(" ");
    var date = all[0].split("-");
    var all1 = startdate.split(" ");
    var date1 = all1[0].split("-");
    main.old = date;
    $('.datepicker').pickadate({
        monthsFull: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October',
            'November', 'December'],
        monthsShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        weekdaysFull: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
        weekdaysShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        format: 'yyyy-mm-dd',
        min: new Date(parseInt(date[0]),parseInt(date[1])-1,parseInt(date[2])+1),
        max: new Date(parseInt(date1[0])+1,parseInt(date1[1])-1,parseInt(date1[2])),
        onSet: function(context) {
           main.endDate = $('#date-picker-example').val();
        }
    });



}
function submit() {
    var d = {};
    d.id= main.id;
    d.endDate =main.endDate;
    d.days = main.days;
    if(main.endDate!==''){
        $.ajax({ url: "Renew/modifyEndDateInfo",
            data: JSON.stringify(d),
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                window.location.href = "/customer/Renew";
            },
            error:function(jqXHR){
                console.log("Error: "+ jqXHR.status);
                main.errors='';
            }
        });
    }
    else{
        main.setInvalid('#date-picker-example');
    }
}
