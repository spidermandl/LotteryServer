<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>
<script src="/assets/js/date-time/bootstrap-datepicker.min.js"></script>

<script type="text/javascript">

    function getpros(){
        $.ajax({
            type: "POST",
            url: "/week/getpros",
            dataType: "text",
            success: function(data){
                alert(data);
            }
        });
    }
    function getprodetails(test){
        alert(test);
    }


    jQuery(function ($) {

        var oTable1 = $('#prioritylist').dataTable({
            "aoColumns": [
                null,
                null,
                null,
                null,
                null,
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
                    disable_search_threshold:10
                }
        );

        $('#pro').on('change', function () {
            $.ajax({
                type: "POST",
                url: "/week/getDetailByPro",
                data: {pro:$("#pro").val()},
                dataType: "text",
                success: function(data){
                    $('#proDetail').html(data);
                }
            });
        });

        $('#shops').on('change', function () {
            $('#searchForm').submit();
        });
        $('#months').on('change', function () {
            $('#searchForm').submit();
        });

        $("#pgrq").datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN'
        });
        $("#endDate").datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'zh-CN',
        });

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

</script>