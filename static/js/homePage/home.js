let app_lang;
Vue.component('navbar-indicator', {
    props: ['islogin', 'username'],
    data: function () {
        return {
            avatarPath: 'https://mdbootstrap.com/img/Photos/Avatars/avatar-2.jpg'
        }
    },
    mounted: function () {
        if(this.islogin) {

        }
    },
    template: navbar_indicator_template
});

$(document).ready(function () {
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