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
        }
    },
    mounted: function () {
        this.table();
    }
});