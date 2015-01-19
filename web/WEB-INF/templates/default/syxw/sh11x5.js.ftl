<script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="assets/js/jquery.sparkline.min.js"></script>
<script src="assets/js/flot/jquery.flot.min.js"></script>
<script src="assets/js/flot/jquery.flot.pie.min.js"></script>
<script src="assets/js/flot/jquery.flot.resize.min.js"></script>

<script type="text/javascript">
    $(function () {
        var number0 = [];
        var number1 = [];
        var number2 = [];
        var number3 = [];
        var number4 = [];
    <#if sh11x5??>
        <#list sh11x5 as mapitem>
            <#if mapitem_index gt sh11x5?size - 20>
                <#assign mi1=mapitem.get("1") ><#assign mi2=mapitem.get("2") >
                number0.push([${mi1.dayperiods},${mi2[0]}]);
                number1.push([${mi1.dayperiods},${mi2[1]}]);
                number2.push([${mi1.dayperiods},${mi2[2]}]);
                number3.push([${mi1.dayperiods},${mi2[3]}]);
                number4.push([${mi1.dayperiods},${mi2[4]}]);
            </#if>
        </#list>
    </#if>

        var linebar1 = $('#linebar1').css({'width': '100%', 'height': '300px'});
        $.plot("#linebar1", [
            { label: "第一个数字", data: number0 },
            { label: "第二个数字", data: number1 },
            { label: "第三个数字", data: number2 },
            { label: "第四个数字", data: number3 },
            { label: "第五个数字", data: number4 }
        ], {
            hoverable: true,
            shadowSize: 0,
            series: {
                lines: { show: true },
                points: { show: true }
            },
            xaxis: {
                tickLength: 0
            },
            yaxis: {
                ticks: 10,
                min: 0,
                max: 100,
                tickDecimals: 2
            },
            grid: {
                backgroundColor: { colors: [ "#fff", "#fff" ] },
                borderWidth: 1,
                borderColor: '#555'
            }
        });
    });

    $(function () {
        var count1 = $('#count1').css({'width': '100%', 'height': '300px'});
        var ticks = [
            [0, '1'],
            [1, '2'],
            [2, '3'],
            [3, '4'],
            [4, '5'],
            [5, '6'],
            [6, '7'],
            [7, '8'],
            [8, '9'],
            [9, '10'],
            [10, '11']
        ];
        var valueCount = [<#if iCount??>[0,${iCount[0]}],
            [1,${iCount[1]}],
            [2,${iCount[2]}],
            [3,${iCount[3]}],
            [4,${iCount[4]}],
            [5,${iCount[5]}],
            [6,${iCount[6]}],
            [7,${iCount[7]}],
            [8,${iCount[8]}],
            [9,${iCount[9]}],
            [10,${iCount[10]}]
        </#if>];

        var dataset = [
            { label: "统计", data: valueCount, color: "#5482FF" }
        ];

        var options = {
            series: {
                bars: {
                    show: true
                }
            },
            bars: {
                align: "center",
                barWidth: 0.5
            },
            xaxis: {
                axisLabel: "数字统计",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 10,
                ticks: ticks
            },
            yaxis: {
                axisLabel: "总出现次数",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 3
            },
            legend: {
                noColumns: 0,
                labelBoxBorderColor: "#000000",
                position: "nw"
            },
            grid: {
                hoverable: true,
                borderWidth: 2,
                backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
            }
        };

        $.plot($("#count1"), dataset, options);
        $("#count1").UseTooltip();

    });

    var previousPoint = null, previousLabel = null;

    $.fn.UseTooltip = function () {
        $(this).bind("plothover", function (event, pos, item) {
            if (item) {
                if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                    previousPoint = item.dataIndex;
                    previousLabel = item.series.label;
                    $("#tooltip").remove();

                    var x = item.datapoint[0];
                    var y = item.datapoint[1];

                    var color = item.series.color;
                    //console.log(item.series.xaxis.ticks[x].label);
                    showTooltip(item.pageX,
                            item.pageY,
                            color,
                            "<strong>" + item.series.label + "</strong><br>" + item.series.xaxis.ticks[x].label + " : <strong>" + y + "</strong>");
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });
    };

    function showTooltip(x, y, color, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y - 50,
            left: x - 20,
            border: '2px solid ' + color,
            padding: '3px',
            'font-size': '9px',
            'border-radius': '5px',
            'background-color': '#fff',
            'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
            opacity: 0.9
        }).appendTo("body").fadeIn(200);
    }

</script>