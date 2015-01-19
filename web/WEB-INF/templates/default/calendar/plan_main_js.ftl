<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.js"></script>

<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/assets/js/fullcalendar.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>
<script src="/assets/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>

<script type="text/javascript">
    jQuery(function ($) {
        /* initialize the external events
            -----------------------------------------------------------------*/
        $('#external-events div.external-event').each(function () {

            // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
            // it doesn't need to have a start or end
            var eventObject = {
                title: $.trim($(this).text()) // use the element's text as the event title
            };

            // store the Event Object in the DOM element so we can get to it later
            $(this).data('eventObject', eventObject);

            // make the event draggable using jQuery UI
            $(this).draggable({
                zIndex: 999,
                revert: true,      // will cause the event to go back to its
                revertDuration: 0  //  original position after the drag
            });

        });

    <#if firstXsl != '0'>
        bootbox.dialog({
            message: "<h5>您是第一次进行${firstXsl}的行事历制定，制定以后，将无法修改${lastXsl}的优先级, 是否继续?</h5>",
            title: "友情提醒",
            buttons: {
                success: {
                    label: "继续",
                    className: "btn-success",
                    callback: function () {

                    }
                },
                main: {
                    label: "取消",
                    className: "btn-primary",
                    callback: function () {
                        location.href = "/calendar/index";
                    }
                }
            }
        });
    </#if>


        /* initialize the calendar
        -----------------------------------------------------------------*/

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();


        var calendar = $('#calendar').fullCalendar({
            monthNames: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            monthNamesShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
            dayNames: ['周日', '周一', '周二', '周三', '周日', '周五', '周六'],
            dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],

            buttonText: {
                prevYear: '去年',
                nextYear: '明年',
                today: '今天',
                month: '月',
                week: '周',
                day: '日',
                prev: '<i class="icon-chevron-left"></i>',
                next: '<i class="icon-chevron-right"></i>'
            },
            currentTimezone: 'Asia/Shanghai',
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultView: 'month',
            editable: true,
            events: [
            <#if cuxXsl??>
                <#list cuxXsl as xsl>
                    <#if xsl.STORE_CODE??>
                        {
                            sid: '${xsl.STORE_CODE}',
                            uid: '${xsl.XSL_LINE_ID}',
                            title: '<#if xsl.ATTRIBUTE_2??>${xsl.ATTRIBUTE_2}<#else>${xsl.STORE_CODE}</#if>',
                            start: '${xsl.C_DATE?substring(0,10)} ${xsl.ATTRIBUTE_9}',
                            end: '${xsl.C_DATE?substring(0,10)} ${xsl.ATTRIBUTE_10}',
                            pid:'<#if xsl.ATTRIBUTE_4??>${xsl.ATTRIBUTE_4}</#if>',
                        className:<#if xsl.STORE_YXJ??><#switch xsl.STORE_YXJ?number>
                            <#case 0>
                            <#if xsl.ATTRIBUTE_3??>
                                <#switch xsl.ATTRIBUTE_3?number>
                                    <#case 2>'label-default-grey'<#break>
                                    <#case 3>'label-default-grey'<#break>
                                    <#case 4>'label-default-grey'<#break>
                                    <#case 5>'label-default-grey'<#break>
                                    <#case 6>'label-default-grey'<#break>
                                    <#case 7>'label-default-grey'<#break>
                                    <#default>'label-default-grey'<#break>
                                </#switch>
                            <#else>'label-default-grey'</#if>
                                <#break>
                            <#case 1>'label-important'<#break>
                            <#case 2>'label-yellow'<#break>
                            <#case 3>'label-warning'<#break>
                            <#case 4>'label-success'<#break>
                            <#default>'label-default-grey'<#break>
                        </#switch><#else>'label-default-grey'</#if>,
                            allDay: false
                        },
                    </#if>
                </#list>
            </#if>{}
            ],
            droppable: true, // this allows things to be dropped onto the calendar !!!
            drop: function (date, allDay) { // this function is called when something is dropped
                var thisToday = new Date();
                var betweenDays = ( date.getTime() - thisToday.getTime()) / ( 1000 * 60 * 60 * 24 );
                if (betweenDays < -2) {
                    bootbox.alert("不可编辑过往行事历！");
                    return false;
                }

                // retrieve the dropped element's stored Event Object
                var originalEventObject = $(this).data('eventObject');
                var $extraEventClass = $(this).attr('data-class');

                // we need to copy it, so that multiple events don't have a reference to the same object
                var copiedEventObject = $.extend({}, originalEventObject);

                var checkStoreForm = $("<form class='form-inline' id='formEvent'><div class='form-group'>");
                checkStoreForm.append("<label for='storeid'>巡&nbsp;&nbsp;&nbsp;&nbsp;店：</label><input class='middle " +
                "input-group  form-control' id='storeid' name='storename' type=text value='" + copiedEventObject.title + "' /> <br />");
                checkStoreForm.append("<label for='commentsid'>备&nbsp;&nbsp;&nbsp;&nbsp;注：</label><input class='middle " +
                "input-group  form-control' id='commentsid' name='comments' type=text value='' /> <br />");
                checkStoreForm.append("<table><tr><td class='width-60'><label >开始时间：</label>" +
                "<div class='input-group bootstrap-timepicker'>" +
                "<input class='middle ' id='timepicker1hour' name='bHour' type=text />" +
                "</div></td><td><label >结束时间：</label>" +
                "<div class='input-group bootstrap-timepicker'>" +
                "<input class='middle ' id='timepicker2hour' name='eHour' type=text />" +
                "</div></td></tr></table>");
//            checkStoreForm.append("<label >结束时间：</label><div class='input-group clockpicker'><input type='text' class='form-control' value='00:00'><span class='input-group-addon'><span class='glyphicon glyphicon-time'></span></span></div><br />");
                checkStoreForm.append("\<script type='text/javascript'>jQuery(function ($) " +
                "{$('#timepicker1hour').timepicker({minuteStep: 1,showSeconds: false,showMeridian: false}).next()." +
                "on(ace.click_event, function(){$(this).prev().focus();});})\<\/script>");
                checkStoreForm.append("\<script type='text/javascript'>jQuery(function ($)" +
                "{$('#timepicker2hour').timepicker({minuteStep: 1,showSeconds: false,showMeridian: false}).next()." +
                "on(ace.click_event, function(){$(this).prev().focus();});})\<\/script>");

                checkStoreForm.append("</div></form>");

                bootbox.dialog({
                    message: checkStoreForm,
                    title: "行事历",
                    buttons: {
                        main: {
                            label: "保存",
                            className: "btn-primary",
                            callback: function () {
                                var params = $("#formEvent").serialize() + "&date=" + $.fullCalendar.formatDate(date, 'yyyy-MM-dd');
                                console.log("Hi " + params);
                                $.post("/calendar/saveCalendar", params, function (data, textStatus) {
                                    copiedEventObject.sid = data.STORE_CODE;
                                    copiedEventObject.uid = data.XSL_LINE_ID;
                                    copiedEventObject.pid = data.ATTRIBUTE_4;
                                    copiedEventObject.start = $.fullCalendar.formatDate(date, 'yyyy-MM-dd') + " " + data.ATTRIBUTE_9;
                                    copiedEventObject.end = $.fullCalendar.formatDate(date, 'yyyy-MM-dd') + " " + data.ATTRIBUTE_10;
                                    copiedEventObject.allDay = false;
                                    if (data.ATTRIBUTE_2 != null && data.ATTRIBUTE_2 != "")
                                        copiedEventObject.title = data.ATTRIBUTE_2;
                                    if ($extraEventClass) copiedEventObject['className'] = [$extraEventClass];
// render the event on the calendar
// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                                    $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
                                });
                                // assign it the date that was reported
                            }
                        }
                    }
                });
// is the "remove after drop" checkbox checked?
//            if ($('#drop-remove').is(':checked')) {
//                // if so, remove the element from the "Draggable Events" list
//                $(this).remove();
//            }

            },
            selectable: true,
            selectHelper: true,
            select: function (start, end, allDay) {

//            bootbox.prompt("新的巡店计划:", function (title) {
//                if (title !== null) {
//                    calendar.fullCalendar('renderEvent',
//                            {
//                                title: title,
//                                start: start,
//                                end: end,
//                                allDay: allDay
//                            },
//                            true // make the event "stick"
//                    );
//                }
//            });


                calendar.fullCalendar('unselect');

            },
            eventClick: function (calEvent, jsEvent, view) {

                var form = $("<form class='form-inline'><label>修改巡店 &nbsp;</label></form> ");
                form.append("<input class='middle' autocomplete=off type=text value='" + calEvent.title + "'/> ");
                form.append("<button type='submit' class='btn btn-sm btn-success'><i class='icon-ok'></i>保存</button>");
                var checkStoreForm = $("<form class='form-inline' id='formEvent'><div class='form-group'>");
                checkStoreForm.append("<label for='storeid'>巡&nbsp;&nbsp;&nbsp;&nbsp;店：</label><input class='middle input-group  " +
                "form-control' id='storeid' name='storename'  type=text value='" + calEvent.title + "' /> <br />");
                checkStoreForm.append("<label for='commentsid'>备&nbsp;&nbsp;&nbsp;&nbsp;注：</label><input class='middle " +
                "input-group  form-control' id='commentsid' name='comments' type=text value='" + calEvent.pid + "' /> <br />");
                checkStoreForm.append("<table><tr><td class='width-60'><label >开始时间：</label>" +
                "<div class='input-group bootstrap-timepicker'>" +
                "<input class='middle ' id='timepicker1hour' name='bHour' type=text value='" + $.fullCalendar.formatDate(calEvent.start, 'HH:mm') + "' />" +
                "</div></td><td><label >结束时间：</label>" +
                "<div class='input-group bootstrap-timepicker'>" +
                "<input class='middle ' id='timepicker2hour' name='eHour' type=text value='" + $.fullCalendar.formatDate(calEvent.end, 'HH:mm') + "' />" +
                "</div></td></tr></table>");
                checkStoreForm.append("\<script type='text/javascript'>jQuery(function ($) " +
                "{$('#timepicker1hour').timepicker({minuteStep: 1,showSeconds: false,showMeridian: false}).next()." +
                "on(ace.click_event, function(){$(this).prev().focus();});})\<\/script>");
                checkStoreForm.append("\<script type='text/javascript'>jQuery(function ($)" +
                "{$('#timepicker2hour').timepicker({minuteStep: 1,showSeconds: false,showMeridian: false}).next()." +
                "on(ace.click_event, function(){$(this).prev().focus();});})\<\/script>");
                checkStoreForm.append("</div></form>");
                var div = bootbox.dialog({
                    message: checkStoreForm,
                    buttons: {
//                        "modify": {
//                            "label": "<i class='icon-edit'></i> 修改",
//                            "className": "btn-sm btn-primary",
//                            "callback": function () {
//                                calendar.fullCalendar('removeEvents', function (ev) {
//                                    return (ev._id == calEvent._id);
//                                })
//                            }
//                        },
                        "delete": {
                            "label": "<i class='icon-trash'></i> 删除",
                            "className": "btn-sm btn-danger",
                            "callback": function () {
                                if ($.fullCalendar.formatDate(calEvent.start, 'yyyy-MM-dd') < $.fullCalendar.formatDate(date, 'yyyy-MM-dd')) {
                                    bootbox.alert("无法删除过往记录。", function () {

                                    });
                                    return false;
                                }
                                bootbox.confirm("是否删除本条记录？", function (result) {
                                    if (result) {
                                        var params = "lineid=" + calEvent.uid;
                                        $.post("/calendar/deleteCalendar", params, function (data, textStatus) {
                                            calendar.fullCalendar('removeEvents', function (ev) {
                                                console.log(calEvent.uid);
                                                console.log(calEvent.sid);
                                                console.log(ev._id);
                                                console.log(calEvent._id);
                                                return (ev._id == calEvent._id);
                                            });
                                        });
                                    }
                                });
                            }
                        },
                        "close": {
                            "label": "<i class='icon-remove'></i> 关闭",
                            "className": "btn-sm"
                        }
                    }

                });

                form.on('submit', function () {
                    calEvent.title = form.find("input[type=text]").val();
                    calendar.fullCalendar('updateEvent', calEvent);
                    div.modal("hide");
                    return false;
                });


//console.log(calEvent.id);
//console.log(jsEvent);
//console.log(view);

// change the border color just for fun
//$(this).css('border-color', 'red');

            }
        });

        var oTable1 = $('#sample-table-2').dataTable({
            "aoColumns": [
                {"bSortable": false},
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                {"bSortable": false}
            ]
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

    })
</script>