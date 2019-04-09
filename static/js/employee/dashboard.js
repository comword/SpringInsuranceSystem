$(document).ready(function () {
    $('[data-toggle="offcanvas"]').click(function () {
        $('.row-offcanvas').toggleClass('active')
    });
    feather.replace();
    const router = new VueRouter({
        mode: 'history',
        routes: []
    });
    const app = new Vue({
        router,
        el: '#main'
    });
});