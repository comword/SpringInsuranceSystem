
var claim = new Vue({
    el: '#vue_claim',
    data: {
        claim: [{
            claimId: '001',
            customerId: 'C1',
            applyDate: '2019-03-09',
            productId: 'CA0002',
            categories:'Clothes',
            status: 'Accepted',
            email:'fuzixin000@gmail.com'
        },
            {
                claimId: '001',
                customerId: 'C1',
                applyDate: '2019-03-09',
                productId: 'CA0002',
                categories:'Clothes',
                status: 'Accepted',
                email:'fuzixin000@gmail.com'
            },
            {
                claimId: '001',
                customerId: 'C1',
                applyDate: '2019-03-09',
                productId: 'CA0002',
                categories:'Clothes',
                status: 'Accepted',
                email:'fuzixin000@gmail.com'
            },
            {
                claimId: '001',
                customerId: 'C1',
                applyDate: '2019-03-09',
                productId: 'CA0002',
                categories:'Clothes',
                status: 'Accepted',
                email:'fuzixin000@gmail.com'
            }],
            tables:"",
            timeOption:"all"
    },
    methods:{
        table: function () {
            let that = this;
            var tables = $('#dtMaterialDesignExample').DataTable({
                    "ajax": {
                        "url": "claim/request",
                        "type": "POST",
                        "contentType": "application/json",
                        "complete": function(xhr, status){
                            xhr=null;
                        },
                        "data": function (d) {
                            d = {};
                            d.timeOption = that.timeOption;
                            return JSON.stringify(d);
                        }
                    },
                    "columns": [
                        { "data": "ClaimID" },
                        { "data": "InsuranceName" },
                        { "data": function ( row, type, set ) {return row.StartTime;}},
                        { "data": "CustomerID"},
                        { "data": "ItemType"},
                        { "data": "Email"},
                        { "data": "Status"},
                        { "data": "Detail"}
                    ],
                    "scrollX": true,
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
        },
        timeFormate(timeStamp) {
            let year = new Date(timeStamp).getFullYear();
            let month =new Date(timeStamp).getMonth() + 1 < 10? "0" + (new Date(timeStamp).getMonth() + 1): new Date(timeStamp).getMonth() + 1;
            let date =new Date(timeStamp).getDate() < 10? "0" + new Date(timeStamp).getDate(): new Date(timeStamp).getDate();
            let hh =new Date(timeStamp).getHours() < 10? "0" + new Date(timeStamp).getHours(): new Date(timeStamp).getHours();
            let mm =new Date(timeStamp).getMinutes() < 10? "0" + new Date(timeStamp).getMinutes(): new Date(timeStamp).getMinutes();
            let ss =new Date(timeStamp).getSeconds() < 10? "0" + new Date(timeStamp).getSeconds(): new Date(timeStamp).getSeconds();
            return year + "/" + month + "/" + date + "/ " + hh + ":" + mm + ":" + ss;
        },
        refresh: function () {
            $('#dtMaterialDesignExample').dataTable()._fnAjaxUpdate();
            // this.table();
        }
    },
    mounted: function () {
        var tables;
        $.cookie('claimID',null);
        this.table();
        setInterval(this.refresh,1000);
    }
});


function clicke(x){
    var id=x.parentNode.parentNode.firstChild.textContent;
    var d = {};
    d.claimID= id;
    var date = new Date();
    date.setTime(date.getTime()+20*60*1000);
    $.cookie('claimID', d.claimID,{expires:date});
    window.location.href = "/employee/admin/detail";
}