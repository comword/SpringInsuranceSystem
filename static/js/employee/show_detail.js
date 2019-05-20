var detail = new Vue({
    el: '#vue_detail',
    data: {
        info: ''
    },
    methods:{
        timeFormate:function(timeStamp) {
            let year = new Date(timeStamp).getFullYear();
            let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
            let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
            let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
            let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
            let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
            return year + "/" + month + "/" + date + "/ " + hh + ":" + mm + ":" + ss;
        }
    },
    mounted:function () {
        let that= this;
            var id = $.cookie("claimID");
        if(id==="null"){
            window.location.href = "/employee/admin/claims";
        }
        var d = {};
        d.claimID = id;
        $.ajax({ url: "/employee/admin/detail/request",
            data: JSON.stringify(d),
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                // detail.info = new Function("return" + response)();
                // detail.info.StartTime = detail.info.StartTime;
                detail.info = response;

            },
            error:function(jqXHR){
                console.log("Error: "+ jqXHR.status);
            }

        });
    }
});

