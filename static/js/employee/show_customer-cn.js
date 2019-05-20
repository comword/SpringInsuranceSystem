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
            ],
            "scrollX": true,
        language: {
            "decimal": "",
            "emptyTable": "没有数据",
            "info": "显示 _START_ 到 _END_ 页共 _TOTAL_ 条",
            "infoEmpty": "显示 0 到 0 页共 0 条",
            "infoFiltered": "(filtered from _MAX_ total entries)",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu": "显示 _MENU_ 条",
            "loadingRecords": "加载中...",
            "processing": "处理中...",
            "search": "搜索:",
            "zeroRecords": "没有匹配项",
            "paginate": { "first": "First", "last": "Last", "next": "下页", "previous": "上页" },
        },
        }
    );
    $('#dtMaterialDesignExample_wrapper').find('label').each(function () {
        $(this).parent().append($(this).children());
    });
    $('#dtMaterialDesignExample_wrapper .dataTables_filter').find('input').each(function () {
        $('input').attr("placeholder", "搜索");
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
