var title =  $("#company-screen");
$(document).on("mousemove",function (e) {
    var ax = -($(window).innerWidth()/2-e.pageX)/20;
    var ay =  ($(window).innerHeight()/2-e.pageY)/10;
    title.attr("style","transform: rotateY(" +(ax)/14+ "deg) rotateX("+ ay/8 +"deg) translateZ(100px)");
    console.log("X:"+ax/14+"  Y:"+ay/8);
})

new Vue({
    el:"body",
    data:{
        isLogin:false
    },
    methods:{
    },
    computed:{}
});


new Vue({
    el:"#formEnter",
    data:{
        a: false,
        b: false,
    },
    methods:{

    },
    computed:{}
});

new Vue({
    el:"#Fxc",
    data:{
        a: false,
        b: false,
        c: false,
        d: scrollY
    },
    methods:{

    },
    computed:{}
});
new Vue({
    el:"#menu-nav",
    data:{
        isLogin:false,
        isTransP: true
    },

    methods:{
        scrollFunc: function (e) {

            e = e || window.event;

            if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件

                if(scrollY>200){
                    this.isTransP=false;
                }
                else{
                    this.isTransP=true;
                }

                console.log(this.isTransP);
                console.log(scrollY);




            } else if (e.detail) {  //Firefox滑轮事件

                if(scrollY>200){
                    this.isTransP=false;
                }
                else{
                    this.isTransP=true;
                }
                console.log(this.isTransP);
                console.log(scrollY);



            }

        } },
    mounted:function ()

    {
        if (document.addEventListener){//firefox
            document.addEventListener('DOMMouseScroll', this.scrollFunc, false);}
        window.onmousewheel = document.onmousewheel = this.scrollFunc;//ie chrome
    },
    computer:{}
});