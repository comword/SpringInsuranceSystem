var customer = new Vue({
    el: '#vue_customer',
    data: {
        info:[],
        test:[
            {fxc:"Fxc"},
            {czy:"Czy"}
        ],
        isRefresh: true,
        Customer_Id: "Customer Id"
    }
});
$(document).ready(function () {
    var d = {};
    d.fxc = customer.test.fxc;
    d.czy = customer.test.czy;

$.ajax({ url: "customer/request",
    data: JSON.stringify(d),
    //type、contentType必填,指明传参方式
    type: "POST",
    contentType: "application/json;charset=utf-8",
    success: function(response){
        customer.info = new Function("return" + response)();
        // customer.people.splice(1,0,customer.info[0]);

    },
    error:function(jqXHR){
        console.log("Error: "+ jqXHR.status);
    }

});
    $('#dtMaterialDesignExample').DataTable({
            "ajax": {
                "url": "customer/request",
                "data": [],
                "type": "POST"
            },
            "columns": [
                { "data": "CustomerId" },
                { "data": "CustomerName" },
                { "data": "Phone"},
                { "data": "Email"}
            ]
        }
    );
    $('#dtMaterialDesignExample_wrapper').find('label').each(function () {
        $(this).parent().append($(this).children());
    });
    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('input').each(function () {
        $('input').attr("placeholder", "Search");
        $('input').removeClass('form-control-sm');
    });
    $('#dtMaterialDesignExample_wrapper .dataTables_length').addClass('d-flex flex-row');
    $('#dtMaterialDesignExample_wrapper .dataTables_filter').addClass('md-form');
    $('#dtMaterialDesignExample_wrapper select').removeClass(
        'custom-select custom-select-sm form-control form-control-sm');
    $('#dtMaterialDesignExample_wrapper select').addClass('mdb-select');
    $('#dtMaterialDesignExample_wrapper .mdb-select').materialSelect();
    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('label').remove();


});
