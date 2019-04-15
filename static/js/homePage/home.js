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
    if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
        $('.scrolling-navbar').removeClass('navbar-dark').addClass('navbar-light');
    }
    else{
        var SCROLLING_NAVBAR_OFFSET_TOP = 50;
        $(window).on('scroll', function () {
            var $navbar = $('.navbar');

            if ($navbar.length) {
                if ($navbar.offset().top > SCROLLING_NAVBAR_OFFSET_TOP) {
                    $('.scrolling-navbar').removeClass('navbar-dark').addClass('navbar-light');
                } else {
                    $('.scrolling-navbar').removeClass('navbar-light').addClass('navbar-dark');
                }
            }
        });
    }

});