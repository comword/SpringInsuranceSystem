var product = new Vue({
    el: '#vue_product',
    data: {
        product: [{
            productId: 'CA001',
            name: 'Ireland luggage insurance',
            time: '7 days',
            abstract:'Operation in Tianjin Lotus\'s market development, sales and after-sale maintenance, spare parts suppliers, insurance claims, second-hand cars services.',
            standard:'We can help prevent this by using responsible approaches such as enhanced coordination of benefits, third-party liability verification, and electronic payment'
        },
            {
                productId: 'CA001',
                name: 'Ireland luggage insurance',
                time: '7 days',
                abstract:'Operation in Tianjin Lotus\'s market development, sales and after-sale maintenance, spare parts suppliers, insurance claims, second-hand cars services.',
                standard:'We can help prevent this by using responsible approaches such as enhanced coordination of benefits, third-party liability verification, and electronic payment'
            },
            {
                productId: 'CA001',
                name: 'Ireland luggage insurance',
                time: '7 days',
                abstract:'Operation in Tianjin Lotus\'s market development, sales and after-sale maintenance, spare parts suppliers, insurance claims, second-hand cars services.',
                standard:'We can help prevent this by using responsible approaches such as enhanced coordination of benefits, third-party liability verification, and electronic payment'
            },
            {
                productId: 'CA001',
                name: 'Ireland luggage insurance',
                time: '7 days',
                abstract:'Operation in Tianjin Lotus\'s market development, sales and after-sale maintenance, spare parts suppliers, insurance claims, second-hand cars services.',
                standard:'We can help prevent this by using responsible approaches such as enhanced coordination of benefits, third-party liability verification, and electronic payment'
            }]
    },
    methods:{
        table: function () {
            $('#dtMaterialDesignExample').DataTable({
                    "ajax": {
                        "url": "product/request",
                        "type": "POST",
                        "contentType": "application/json",
                    },
                    "columns": [
                        { "data": "InsuranceID" },
                        { "data": "InsuranceName" },
                        { "data": "Abstract"}
                    ],
                    "scrollX": true
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
        }
    },
    mounted: function () {
        this.table();
    }
});