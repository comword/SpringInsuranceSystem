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
        userName:'',
        email:'',
        firstName:'',
        lastName:'',
        phone:'',
        information:[],
        user:'',
        show: true
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
        validEmail: function (email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        },
        validPhone: function (phone) {
            var re = /^1(3|4|5|7|8)\d{9}$/;
            return re.test(phone);
        },
        edit: function () {
            this.show = false;
            $("#userName").removeAttr("disabled");
            $("#email").removeAttr("disabled");
            $("#firstName").removeAttr("disabled");
            $("#lastName").removeAttr("disabled");
            $("#phone").removeAttr("disabled");
            $("#userName").css('border-bottom','solid 2px #d72c2c');
            $("#email").css('border-bottom','solid 2px #d72c2c');
            $("#firstName").css('border-bottom','solid 2px #d72c2c');
            $("#lastName").css('border-bottom','solid 2px #d72c2c');
            $("#phone").css('border-bottom','solid 2px #d72c2c');
        },
        submit: function () {
            var d = {};
            var issubmit = true;
            d.displayName = this.userName;
            d.firstName = this.firstName;
            d.lastName = this.lastName;
            d.email = this.email;
            d.phone = this.phone;
            d.user = this.user;

            if(!this.userName){
                this.setInvalid('#userName');
                issubmit = false;
            }
            else{
                this.removeInvalid('#userName');
            }
            if(!this.validEmail(this.email)){
                this.setInvalid('#email');
                issubmit = false;
            }
            else{
                this.removeInvalid('#email');
            }
            if(!this.firstName){
                this.setInvalid('#firstName');
                issubmit = false;
            }
            else{
                this.removeInvalid('#fistName');
            }
            if(!this.lastName){
                this.setInvalid('#lastName');
                issubmit = false;
            }
            else{
                this.removeInvalid('#lastName');
            }
            if(!this.validPhone(this.phone)){
                this.setInvalid('#phone');
                issubmit = false;
            }
            else{
                this.removeInvalid('#phone');
                this.phone = '+86'+this.phone;
            }
            if(issubmit){
            $.ajax({ url: "Profile/modifyProfileInfo",
                data: JSON.stringify(d),
                type: "POST",
                contentType: "application/json;charset=utf-8",
                success: function(response){
                    main.phone = main.phone.replace("\+86","");
                    $("#userName").attr("disabled","disabled");
                    $("#email").attr("disabled","disabled");
                    $("#firstName").attr("disabled","disabled");
                    $("#lastName").attr("disabled","disabled");
                    $("#phone").attr("disabled","disabled");
                    $("#userName").css('border-bottom','solid 2px #757575');
                    $("#email").css('border-bottom','solid 2px #757575');
                    $("#firstName").css('border-bottom','solid 2px #757575');
                    $("#lastName").css('border-bottom','solid 2px #757575');
                    $("#phone").css('border-bottom','solid 2px #757575');
                    this.show = true;
                    window.location.href = "/customer/Profile";
                },
                error:function(jqXHR){
                    console.log("Error: "+ jqXHR.status);
                    main.errors='';
                }
            });
            }

        }

    },
    watch:{
        userName(newName,oldName){
            if(!newName){
                this.setInvalid('#userName');
            }
            else{
                this.removeInvalid('#userName');
            }
        },
        email(newName,oldName){
            if(!this.validEmail(newName)){
                this.setInvalid('#email');
            }
            else{
                this.removeInvalid('#email');
            }
        },
        firstName(newName,oldName){
            if(!newName){
                this.setInvalid('#firstName');
            }
            else{
                this.removeInvalid('#fistName');
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
        phone(newName,oldName){
            if(!this.validPhone(newName)){
                this.setInvalid('#phone');
            }
            else{
                this.removeInvalid('#phone');
            }
        },
    },
    mounted: function () {
        let that = this;
        $('#edit').click(function () {
            that.edit();
        });
        $('#submit').click(function () {
            that.submit();
        });
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
                main.user = self.username;
                $(".button-collapse").sideNav();
// SideNav Scrollbar Initialization
                var sideNavScrollbar = document.querySelector('.custom-scrollbar');
                // var ps = new PerfectScrollbar(sideNavScrollbar);
                main.user = self.username;
                var d ={};
                d.displayName = self.username;
                $.ajax({ url: "Profile/uploadProfileInfo",
                    data: JSON.stringify(d),
                    //type、contentType必填,指明传参方式
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    success: function(response){
                        main.information = response;
                        main.userName = main.information.userName;
                        main.email = main.information.email;
                        main.firstName = main.information.firstName;
                        main.lastName = main.information.lastName;
                        main.phone = main.information.phone;
                        main.phone = main.phone.replace("\+86","");
                        $("#userName").attr("disabled","disabled");
                        $("#email").attr("disabled","disabled");
                        $("#firstName").attr("disabled","disabled");
                        $("#lastName").attr("disabled","disabled");
                        $("#phone").attr("disabled","disabled");

                    },
                    error:function(jqXHR){
                        console.log("Error: "+ jqXHR.status);
                        main.errors='';
                    }
                });

            } else {
                window.location.href = "/login";
            }
        });
    }
});