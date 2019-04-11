new Vue({
    el:".main-whole",
    data:{
        step:1,
        isMobile: false,
        showDetail:false
    },

    methods:{
        nextStep: function () {
            this.step=step+1;
        },
        preStep: function () {
            this.step=step-1;
        },

    },
    mounted:function () {
        if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
            this.isMobile = true;
        }
    }
})