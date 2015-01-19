<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="/assets/js/date-time/My97DatePicker/WdatePicker.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>

<script type="text/javascript">

    function monthSelector() {
        WdatePicker({ dateFmt: 'yyyy-MM', isShowToday: false, isShowClear: false });
    }

    function selectMonth(month,start,end){
        $('#months option[value=' + month + ']').attr("selected",true);
        $('#start').val(start);
        $('#end').val(end);
        if ($('#months').val()!="" && $('#start').val()!="" && $('#end').val()!="") {
            $('#btnSubmit').removeAttr("disabled");
        }
        else {
            $('#btnSubmit').attr("disabled","disabled");
        }
    }

    jQuery(function ($) {
        var oTable1 = $('#prioritylist').dataTable({
            "aoColumns": [
                { "bSortable": false },
                null,
                null,
                null
            ] });

        $("#start").datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN'
        });
        $("#end").datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN',
        });

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

        $('#start').on('change', function () {
            if ($('#months').val()!="" && $('#start').val()!="" && $('#end').val()!="") {
                $('#btnSubmit').removeAttr("disabled");
            }
            else {
                $('#btnSubmit').attr("disabled","disabled");
            }
        });

        $('#end').on('change', function () {
            if ($('#months').val()!="" && $('#start').val()!="" && $('#end').val()!="") {
                $('#btnSubmit').removeAttr("disabled");
            }
            else {
                $('#btnSubmit').attr("disabled","disabled");
            }
        });


        $('#months').on('change', function () {
            $.ajax({
                cache: true,
                type: "POST",
                url: '/calendar/getXslMonthDetail',
                data: {"month": $(this).val()},// 你的formid
                async: false,
                error: function (request) {
                    alert("Connection error");
                },
                success: function (data) {
                    if (data.indexOf(",") > -1) {
                        $("#start").val(data.split(",")[0]);
                        $("#end").val(data.split(",")[1]);
                    }
                    else {
                        $("#start").val("");
                        $("#end").val("");
                    }
                    if ($('#months').val()!="" && $('#start').val()!="" && $('#end').val()!="") {
                        $('#btnSubmit').removeAttr("disabled");
                    }
                    else {
                        $('#btnSubmit').attr("disabled","disabled");
                    }
                }
            });
        });
    });

</script>