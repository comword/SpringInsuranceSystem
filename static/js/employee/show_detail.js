var detail = new Vue({
    el: '#vue_detail',
    data: {
        info: []
    },
    mounted:function () {
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
                detail.info = new Function("return" + response)();
            },
            error:function(jqXHR){
                console.log("Error: "+ jqXHR.status);
            }

        });
    }
});

