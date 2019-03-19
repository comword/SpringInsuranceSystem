$.getScript('/js/md5.min.js');
let app_token, app_lang;
function setInvalid(id){
    $(id).removeClass("is-valid")
    $(id).addClass("is-invalid")
}

function setValid(id){
    $(id).removeClass("is-invalid")
    $(id).addClass("is-valid")
}

window.onload = function() {
    const router = new VueRouter({
        mode: 'history',
        routes: []
    })

    const app = new Vue({
        router,
        el: '#signup-form'
    });
    $.ajax({
        url: '/register/basicInfo',
        method: 'GET',
        success: function (data) {
            loadBasicPage(router, data);
        },
        error: function (error) {
            console.log(error);
        }
    });
};

function loadBasicPage(router, data) {
    let page = data.nextPage;
    let comp = Vue.component(page.name, {
        template: page.template,
        data: function () {
            return {
                signupForm: {
                    firstName: '',
                    lastName: '',
                    email: '',
                    password: '',
                    confirm: ''
                },
                errors: []
            }
        },
        mounted: function () {
            try {
                let saved = JSON.parse(localStorage.getItem('signupForm'));
                if(saved!=null)
                    this.signupForm = saved;
                else
                    localStorage.removeItem('signupForm');
            } catch(e) {
                localStorage.removeItem('signupForm');
            }
            $.ajax({
                url: '/register/tokenId',
                method: 'GET',
                success: function (data) {
                    app_token = data["token"];
                    app_lang = data["lang"];
                },
                error: function (error) {
                    console.log(error);
                }
            });
            $("#signup-form").bootstrapMaterialDesign();
        },
        methods: {
            validEmail: function (email) {
                let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            },
            next_1: function () {
                this.errors = [];
                let save = Object.assign({}, this.signupForm);
                save.confirm = '';
                save.password = '';
                localStorage.setItem('signupForm', JSON.stringify(save));
                let failed = false;
                if (!this.signupForm.firstName) {
                    setInvalid('#firstName');
                    failed = true;
                }
                else
                    setValid('#firstName');
                if (!this.signupForm.lastName) {
                    setInvalid('#lastName');
                    failed = true;
                }
                else
                    setValid('#lastName');
                if (!this.signupForm.username) {
                    setInvalid('#username');
                    failed = true;
                }
                else
                    setValid('#username');
                if (!this.validEmail(this.signupForm.email)) {
                    setInvalid('#email');
                    failed = true;
                }
                else
                    setValid('#email');
                if (this.signupForm.password.length<8) {
                    setInvalid('#password');
                    return
                } else
                    setValid('#password');
                if (this.signupForm.password!==this.signupForm.confirm) {
                    setInvalid('#confirm');
                    return
                } else
                    setValid('#confirm');
                if (failed)
                    return;
                let postData = Object.assign({}, this.signupForm);
                delete postData.confirm;
                postData.password = md5(this.signupForm.password);
                postData.lang = app_lang;
                postData.token = app_token;
                let self = this;
                $("#progress").animate({ opacity: 1 });
                $("#signup-form").data("bmd.bootstrapMaterialDesign", null);
                $.ajax({
                    url: '/register/basicInfo',
                    method: 'POST',
                    data: JSON.stringify(postData),
                    contentType: "application/json; charset=UTF-8",
                    async: false,
                    cache: false,
                    processData: false,
                    success: function (data) {
                        loadDetailPage(self.$router, data);
                    },
                    error: function (error) {
                        //console.log(error);
                        $("#progress").animate({ opacity: 0 });
                        let err = JSON.parse(error.responseText);
                        self.errors.push(err.errmsg);
                        switch (err.errcode) {
                            case -1000:
                                //location.reload();
                                break;
                        }
                    }
                });
            },
            back_1: function () {
                this.$router.back();
            }
        }
    });
    router.addRoutes([{ path: '/register/customer/'+page.name, component: comp }]);
    router.push( {path: '/register/customer/'+page.name} );
}

function loadDetailPage(router, data) {
    $("#progress").animate({ opacity: 0 });
    let page = data.nextPage;
    let comp = Vue.component(page.name, {
        template: page.template,
        data: function () {
            return {
                detailForm: {
                    phoneCountry: '',
                    phone: '',
                    ID: '',
                    address:'',
                    address2: '',
                    country: '',
                    state: '',
                    zip: '',
                    payment: '',
                    card_name: '',
                    card_num: '',
                    card_expr: '',
                    card_ccv: ''
                },
                errors: []
            }
        },
        mounted: function () {
            if(app_lang==="zh_CN")
                this.detailForm.phoneCountry = '+86';
            else
                this.detailForm.phoneCountry = '+353';
            $("#signup-form").bootstrapMaterialDesign();
        },
        watch: {
            'detailForm.phoneCountry': function(newVal, oldVal) {
                if(newVal === oldVal)
                    return;
                let phone = this.detailForm.phone;
                if(phone.startsWith("+353"))
                    this.detailForm.phone = newVal+phone.substr(4);
                else if(phone.startsWith("+86"))
                    this.detailForm.phone = newVal+phone.substr(3);
                else
                    this.detailForm.phone = newVal+phone;
                if(newVal === '+353')
                    $("#flagBtn").css({"backgroundPosition": "-1px -2670px"});
                if(newVal === '+86')
                    $("#flagBtn").css({"backgroundPosition": "-1px -1072px"});
            }
        },
        methods: {
            next_2: function () {
                this.errors = [];
                let failed = false;
                if (!this.detailForm.phone) {
                    setInvalid('#phone');
                    failed = true;
                }
                else
                    setValid('#firstName');
                if (!this.signupForm.lastName) {
                    setInvalid('#lastName');
                    failed = true;
                }
                else
                    setValid('#lastName');
            }
        }
    });
    router.addRoutes([{ path: '/register/customer/'+page.name, component: comp }]);
    router.push( {path: '/register/customer/'+page.name} );
}