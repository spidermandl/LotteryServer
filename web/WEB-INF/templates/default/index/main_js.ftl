<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/assets/js/fullcalendar.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>


<!--[if lte IE 8]>
		  <script src="/assets/js/excanvas.min.js"></script>
		<![endif]-->

<script src="/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>
<script src="/assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="/assets/js/jquery.gritter.min.js"></script>
<script src="/assets/js/spin.min.js"></script>

<script src="/assets/js/jquery.dataTables.min.js"></script>
<script src="/assets/js/jquery.dataTables.bootstrap.js"></script>

<#--<script src="/assets/js/bootstrap.min.js"></script>-->
<script src="/assets/js/typeahead-bs2.min.js"></script>

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

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        defaultView: 'month',

        events: [
//            {
//                title: 'All Day Event',
//                start: new Date(y, m, 1),
//                className: 'label-important'
//            },
//            {
//                title: 'Long Event',
//                start: new Date(y, m, d - 5),
//                end: new Date(y, m, d - 2),
//                className: 'label-success'
//            },
//            {
//                title: 'Some Event',
//                start: new Date(y, m, d - 3, 16, 0),
//                allDay: false
//            },
//            {
//                title: '上海真北家乐福鞋柜巡店',
//                start: new Date(y, m, d, 10, 0),
//                end: new Date(y, m, d, 12, 0),
//                className: 'label-info',
//                allDay: false
//            },
//            {
//                title: '上海XXX鞋柜巡店',
//                start: new Date(y, m, d + 1, 11, 0),
//                end: new Date(y, m, d + 1, 13, 0),
//                className: 'label-info',
//                allDay: false
//            },
//            {
//                title: '上海XXX鞋柜巡店',
//                start: new Date(y, m, d + 2, 12, 0),
//                end: new Date(y, m, d + 2, 14, 0),
//                className: 'label-info',
//                allDay: false
//            },
//            {
//                title: '上海铜川乐购鞋柜巡店',
//                start: new Date(y, m, d, 16, 0),
//                end: new Date(y, m, d, 18, 0),
//                className: 'label-info',
//                allDay: false
//            }
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

//            bootbox.prompt("New Event Title:", function (title) {
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

            var form = $("<form class='form-inline'><label>Change event name &nbsp;</label></form>");
            form.append("<input class='middle' autocomplete=off type=text value='" + calEvent.title + "' /> ");
            form.append("<button type='submit' class='btn btn-sm btn-success'><i class='icon-ok'></i> Save</button>");

            var div = bootbox.dialog({
                message: form,

                buttons: {
                    "delete": {
                        "label": "<i class='icon-trash'></i> Delete Event",
                        "className": "btn-sm btn-danger",
                        "callback": function () {
                            calendar.fullCalendar('removeEvents', function (ev) {
                                return (ev._id == calEvent._id);
                            })
                        }
                    },
                    "close": {
                        "label": "<i class='icon-remove'></i> Close",
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

//    var unique_id = $.gritter.add({
//// (string | mandatory) the heading of the notification
//        title: '您有1份优先级变更申请审批通过!',
//// (string | mandatory) the text inside the notification
//        text: '上海光新乐购鞋柜,当前优先级为<span class=\"label label-sm label-yellow\">2</span>',
//// (string | optional) the image to display on the left
////        image: 'http://s3.amazonaws.com/twitter_production/profile_images/132499022/myface_bigger.jpg',
//// (bool | optional) if you want it to fade out on its own or just sit there
//        sticky: true,
//// (int | optional) the time you want it to be alive for before fading out
//        time: '5',
//// (string | optional) the class name you want to apply to that specific message
//        class_name: 'gritter-success'
//    });
})
</script>