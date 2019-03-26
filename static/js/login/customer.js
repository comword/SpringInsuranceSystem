$.getScript('/js/md5.min.js');
let app_token;
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
            lang: '',
            errors: []
        },
        mounted: function () {
            let self = this
            $.ajax({
                url: '/login/tokenId',
                method: 'GET',
                success: function (data) {
                    self.loginForm.token = data["token"];
                    self.lang = data["lang"];
                },
                error: function (error) {
                    console.log(error);
                }
            });
        },
        methods: {
            login: function () {
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

                    },
                    error: function (error) {
                        let err = JSON.parse(error.responseText);
                        self.errors.push(err.errmsg);
                        switch (err.errcode) {
                            case -1000:
                                location.reload();
                                break;
                        }
                    }
                });
            }
        }
    });
};