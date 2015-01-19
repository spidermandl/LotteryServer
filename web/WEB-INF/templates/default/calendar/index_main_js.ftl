<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/assets/js/fullcalendar.min.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>

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

        $(".chosen-select").chosen(
                {
                    search_contains: true,
                    disable_search_threshold:10
                }
        );

        $('#sv').on('change', function () {
            $('#svForm').submit();
        });


        /* initialize the calendar
        -----------------------------------------------------------------*/

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();


        var calendar = $('#calendar').fullCalendar({
            monthNames: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
            monthNamesShort: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
            dayNames: ['周日','周一','周二','周三','周日','周五','周六'],
            dayNamesShort: ['日','一','二','三','四','五','六'],

            buttonText: {
                prevYear: '去年',
                nextYear: '明年',
                today:    '今天',
                month:    '月',
                week:     '周',
                day:      '日',
                prev: '<i class="icon-chevron-left"></i>',
                next: '<i class="icon-chevron-right"></i>'
            },

            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            defaultView: 'agendaWeek',

            events: [
            <#if cuxXsl??>
                <#list cuxXsl as xsl>
                    <#if xsl.STORE_CODE??>
                        {
                            sid: '${xsl.STORE_CODE}',
                            uid:'${xsl.XSL_LINE_ID}',
                            title: '<#if xsl.ATTRIBUTE_2??>${xsl.ATTRIBUTE_2}</#if>',
                            start: '${xsl.C_DATE?substring(0,10)} ${xsl.ATTRIBUTE_9}',
                            end: '${xsl.C_DATE?substring(0,10)} ${xsl.ATTRIBUTE_10}',
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
            editable: true,
            droppable: true, // this allows things to be dropped onto the calendar !!!
            drop: function (date, allDay) { // this function is called when something is dropped

                // retrieve the dropped element's stored Event Object
                var originalEventObject = $(this).data('eventObject');
                var $extraEventClass = $(this).attr('data-class');


                // we need to copy it, so that multiple events don't have a reference to the same object
                var copiedEventObject = $.extend({}, originalEventObject);

                // assign it the date that was reported
                copiedEventObject.start = date;
                copiedEventObject.allDay = allDay;
                if ($extraEventClass) copiedEventObject['className'] = [$extraEventClass];

                // render the event on the calendar
                // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

                // is the "remove after drop" checkbox checked?
                if ($('#drop-remove').is(':checked')) {
                    // if so, remove the element from the "Draggable Events" list
                    $(this).remove();
                }

            },
            selectable: true,
            selectHelper: true,
            select: function (start, end, allDay) {
//
//                bootbox.prompt("New Event Title:", function (title) {
//                    if (title !== null) {
//                        calendar.fullCalendar('renderEvent',
//                                {
//                                    title: title,
//                                    start: start,
//                                    end: end,
//                                    allDay: allDay
//                                },
//                                true // make the event "stick"
//                        );
//                    }
//                });


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

            }

        });


    })
</script>