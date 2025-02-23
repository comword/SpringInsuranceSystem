var product = new Vue({
    el: '#vue_product',
    data: {
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