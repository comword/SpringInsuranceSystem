var attachment = new Vue({
    el: '#vue_attachment',
    // el: '#vue_feedback',
    data: {
        claimID: '',
        url: [],
        info:[]
    },
    mounted: function () {
        // alert("fxc");
        this.claimID = $.cookie("claimID");
        if(this.claimID==="null"){
            window.location.href = "/employee/admin/claims";
        }
        var d = {};
        d.claimID = this.claimID;
        $.ajax({ url: "/employee/admin/attachment/request",
            data: JSON.stringify(d),
            type: "POST",
            contentType: "application/json;charset=utf-8",
            success: function(response){
                // detail.info = new Function("return" + response)();
                // detail.info.StartTime = detail.info.StartTime;
                attachment.info = new Function("return" + response)();
                var urls = attachment.info.url.split(",");
                // alert(urls);
                for (var i=0,len=urls.length; i<len; i++)
                {
                    $("#box").append("<figure class=\"col-md-4\">\n" +
                        "                        <a href=\"/images/claimPhotos/" +urls[i]+
                        "\" data-size=\"1600x1067\">\n" +
                        "                            <img src=\"/images/claimPhotos/" +urls[i]+
                        "\" alt=\"placeholder\"\n" +
                        "                                 class=\"img-fluid\">\n" +
                        "                        </a>\n" +
                        "                    </figure>");
                }


                $("#mdb-lightbox-ui").load("../../../static/libs/MDB/mdb-addons/mdb-lightbox-ui.html");
            },
            error:function(jqXHR){
                console.log("Error: "+ jqXHR.status);
            }

        });
    }
});
