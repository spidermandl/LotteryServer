<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>
<script src="/assets/js/date-time/bootstrap-datepicker.min.js"></script>

<script type="text/javascript">
    jQuery(function ($) {

        var oTable1 = $('#prioritylist').dataTable({
            "aoColumns": [
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null
            ] });

        $(".chosen-select").chosen(
                {
                    search_contains: true,
                    disable_search_threshold:10
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

        $('#months').chosen().change(function () {
            $.ajax({
                type: "POST",
                url: '/report/getWeeksByXslMonth',
                data: {"month": $(this).children('option:selected').val()},
                error: function (request) {
                    alert("Connection error");
                },
                success: function (data) {
                    $("#week").html("");
                    $("#week").chosen("destroy");
                    $.each(data, function (i, val) {
                        $('#week').append("<option value='" + val[0] + "'>" + val[0] + "</option>");
                    });
                    $('#week').chosen();
                    $('#week').trigger("liszt:updated");
                }
            });
        });

        $('#svs').chosen().change(function () {
            $.ajax({
                cache: true,
                type: "POST",
                url: '/priority/smGetSvShopOptionsBySvName',
                data: {"svorgname": $(this).children('option:selected').val()},// 你的formid
                async: false,
                error: function (request) {
                    alert("Connection error");
                },
                success: function (data) {
                    $("#shops").html("");
                    $("#shops").chosen("destroy");
                    $("<option value=''>&nbsp;&nbsp;</option>").appendTo('#shops')
                    $.each(data, function (i, val) {
                        $('#shops').append("<option value='" + val[0] + "'>" + val[0] + "," + val[1] + "</option>");
                    });
                    $('#shops').chosen();
                    $('#shops').trigger("liszt:updated");
                }
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
//
//        $("#week").datepicker({
//            format: 'yyyy-mm-dd',
//            weekStart: 1,
//            autoclose: true,
//            todayBtn: 'linked',
//            language: 'zh-CN'
//        });
    });

</script>