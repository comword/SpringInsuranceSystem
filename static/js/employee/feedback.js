var feedback = new Vue({
    el: '#vue_feedback',
    data: {
        id: "",
        result:"Accepted",
        feedback:"",
        timeStamp:"",
        resResult: true,
    },
    methods:{

    },
    mounted: function () {
        let that= this;
        var id = $.cookie("claimID");
        if(id==="null"){
        window.location.href = "/employee/admin/claims";
        }
        else{
            var d ={};
            this.id = id;
            d.claimID = id;
            $.ajax({ url: "feedback/precondition",
                data: JSON.stringify(d),
                //type、contentType必填,指明传参方式
                type: "POST",
                contentType: "application/json;charset=utf-8",
                success: function(response){
                    feedback.precondition = response;
                    //前端调用成功后，可以处理后端传回的json格式数据。
                    if( feedback.precondition.resCode==='0'&&feedback.precondition.result!=="Undetermined"){
                            feedback.result = feedback.precondition.result;
                            feedback.feedback = feedback.precondition.feedback;
                            $("#exampleFormControlTextarea1").attr("disabled","disabled");
                            $("#materialInline1").attr("disabled","disabled");
                            $("#materialInline2").attr("disabled","disabled");
                            $("#materialInline3").attr("disabled","disabled");
                            $("#submit").attr("disabled","disabled");
                        }
                        else{
                        }

                },
                error:function(jqXHR){
                    console.log("Error: "+ jqXHR.status);
                }
            });
        }
    }
});
function clicke(x){

    var d = {};
    d.claimID= feedback.id;
    d.result = feedback.result;
    d.feedback = feedback.feedback;
    var date = new Date();
    d.timeStamp = date.getTime();
    $.ajax({ url: "feedback/request",
        data: JSON.stringify(d),
        //type、contentType必填,指明传参方式
        type: "POST",
        contentType: "application/json;charset=utf-8",
        success: function(response){
            feedback.response = new Function("return" + response)();
            //前端调用成功后，可以处理后端传回的json格式数据。
            if( feedback.response.resCode==='0'){
                $('#centralModalSuccess').modal('show');
                if(feedback.response.result==='4'){
                    feedback.resResult = false;
                    $("#exampleFormControlTextarea1").attr("disabled","disabled");
                    $("#materialInline1").attr("disabled","disabled");
                    $("#materialInline2").attr("disabled","disabled");
                    $("#materialInline3").attr("disabled","disabled");
                    $("#submit").attr("disabled","disabled");
                }
                else{
                    feedback.resResult = true;
                }
            }
        },
        error:function(jqXHR){
            console.log("Error: "+ jqXHR.status);
        }
    });
}
