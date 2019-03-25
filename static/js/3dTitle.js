var title =  $("#company-screen");
$(document).on("mousemove",function (e) {
    var ax = -($(window).innerWidth()/2-e.pageX)/20;
    var ay =  ($(window).innerHeight()/2-e.pageY)/10;
    title.attr("style","transform: rotateY(" +(ax)/14+ "deg) rotateX("+ ay/8 +"deg) translateZ(100px)");
    console.log("X:"+ax/14+"  Y:"+ay/8);
})
