<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>

<script type="text/javascript">
    var approveId = "";
    jQuery(function ($) {
        var oTable1 = $('#prioritylist').dataTable({
            "aoColumns": [
                { "bSortable": false },
                null,
                null,
                null,
                null,
                null,
                null,
                null
            ] });

        $(".chosen-select").chosen(
                {
                    search_contains: true,
                    disable_search_threshold: 10
                }
        );

        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                    .each(function () {
                        this.checked = that.checked;
                        $(this).closest('tr').toggleClass('selected');
                    });

        });


        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();

            if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) return 'right';
            return 'left';
        }
    });

    //    function search(){
    //
    //        $.ajax({
    //            cache: true,
    //            type: "POST",
    //            url:ajaxCallUrl,
    //            data:$('#yourformid').serialize(),// 你的formid
    //            async: false,
    //            error: function(request) {
    //                alert("Connection error");
    //            },
    //            success: function(data) {
    //
    //            }
    //        });
    //    }
    function getDetail(headid) {
        $.ajax({
            cache: false,
            type: "POST",
            url: "/priority/getApplyDetailByHead",
            data: {"applyHeadId": headid},// 你的formid
            async: false,
            error: function (request) {
                alert("Connection error");
            },
            success: function (data) {
//                dataTable.fnDestroy();

                $('#applyNumber').text(headid);
                $('#modal-table').modal('show');
                approveId = headid;
                $('#applyDetail').dataTable({
                    "bDestroy": true,
                    "bAutoWidth": true,
                    "bRetrieve": true,
                    "bPaginate": false,
                    "bFilter": false,
                    "aoColumns": [
                        {"bVisible": false},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    ],
                    "aaData": data
                });
            }
        });
    }

    function approve() {
        if (approveId != "") {
            approveForm.formApproveId.value = approveId;
            approveForm.submit();
        }
    }
</script>