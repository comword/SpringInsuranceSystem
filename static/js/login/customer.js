$.getScript('/js/md5.min.js');
$.getScript('/js/login/translation-cn.js');
let app_lang;
function setInvalid(id){
    $(id).removeClass("is-valid")
    $(id).addClass("is-invalid")
}

function setValid(id){
    $(id).removeClass("is-invalid")
    $(id).addClass("is-valid")
}

window.onload = function() {
    const app = new Vue({
        el: '#form-signin',
        data: {
            loginForm:{
                username: '',
                password: '',
                rem: false,
                token: ''
            },
            errors: []
        },
        mounted: function () {
            let self = this
            $.ajax({
                url: '/login/tokenId',
                method: 'GET',
                success: function (data) {
                    self.loginForm.token = data["token"];
                    app_lang = data["lang"];
                },
                error: function (error) {
                    console.log(error);
                }
            });
        },
        methods: {
            login: function () {
                this.errors = [];
                let failed = false;
                if (!this.loginForm.username) {
                    setInvalid('#inputUsername');
                    failed = true;
                }
                if (!this.loginForm.password) {
                    setInvalid('#inputPassword');
                    failed = true;
                }
                if(failed)
                    return;
                let postData = Object.assign({}, this.loginForm);
                postData.password = md5(this.loginForm.password);
                let self = this;
                $.ajax({
                    url: '/login',
                    method: 'POST',
                    data: JSON.stringify(postData),
                    contentType: "application/json; charset=UTF-8",
                    async: false,
                    cache: false,
                    processData: false,
                    success: function (data) {
                        if (data.rescode===0) {
                            if (data.redirect!=null)
                                window.location.replace(data.redirect);
                            else
                                window.location.replace("/");
                        } else
                            self.errors.push(_T(data.errmsg));
                    },
                    error: function (error) {
                        let err = JSON.parse(error.responseText);
                        self.errors.push(_T(err.errmsg));
                        switch (err.errcode) {
                            case -1001:
                                location.reload();
                                break;
                        }
                    }
                });
            }
        }
    });
};