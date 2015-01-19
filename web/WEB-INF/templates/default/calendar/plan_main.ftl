<script language="JavaScript">
    $("#menu000200").addClass("active");
    $("#menu000200").addClass("open");
    $("#menu000202").addClass("active");
</script>
<style type="text/css">
    .label-default-grey {
        background-color: #abbac3!important;
    }
</style>
<div class="page-content">
    <div class="page-header">
        <h1>
            行事历
            <small>
                <i class="icon-double-angle-right"></i>
                行事历安排
            </small>
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->

            <div class="col-sm-12">
                <div class="widget-box transparent" id="recent-box">
                    <div class="widget-header">
                        <h4 class="lighter smaller">
                            <i class="icon-rss orange"></i>
                            SV行事历
                        </h4>

                        <div class="widget-toolbar no-border">
                            <ul class="nav nav-tabs" id="recent-tab">
                                <li class="active">
                                    <a data-toggle="tab" href="#task-tab">日历视图</a>
                                </li>

                                <#--<li>-->
                                    <#--<a data-toggle="tab" href="#member-tab">列表视图</a>-->
                                <#--</li>-->
                            </ul>
                        </div>
                    </div>

                    <div class="widget-body">
                        <div class="widget-main padding-12">
                            <div class="tab-content padding-8 overflow-visible">
                                <div id="task-tab" class="tab-pane active">
                                    <div class="clearfix">
                                        <h4 class="smaller lighter green">
                                            <i class="icon-calendar"></i>
                                            行事历计划日历视图
                                        </h4>

                                        <div class="space"></div>
                                        <div class="row">
                                            <div class="col-sm-9">
                                                <div class="space"></div>

                                                <div id="calendar"></div>
                                            </div>

                                            <div class="col-sm-3">
                                                <div class="widget-box transparent">
                                                    <div class="widget-header">
                                                        <h4>拖动进行编辑</h4>
                                                    </div>
                                                    <div class="widget-body">
                                                        <div class="widget-main no-padding">
                                                            <div id="external-events">
                                                                <div class="external-event label-default"
                                                                     data-class="label-light">
                                                                    <i class="icon-move"></i>
                                                                    会议
                                                                </div>
                                                                <div class="external-event label-default"
                                                                     data-class="label-light">
                                                                    <i class="icon-move"></i>
                                                                    培训
                                                                </div>
                                                                <div class="external-event label-default"
                                                                     data-class="label-light">
                                                                    <i class="icon-move"></i>
                                                                    DT店铺
                                                                </div>
                                                                <div class="external-event label-default"
                                                                     data-class="label-light">
                                                                    <i class="icon-move"></i>
                                                                    开撤店
                                                                </div>
                                                                <div class="external-event label-default"
                                                                     data-class="label-light">
                                                                    <i class="icon-move"></i>
                                                                    其他
                                                                </div>
                                                                <div class="external-event label-default"
                                                                     data-class="label-light">
                                                                    <i class="icon-move"></i>
                                                                    休息
                                                                </div>
                                                            <#if shops??>
                                                                <#list shops as item>
                                                                    <div class="external-event <#if item[3]??><#switch item[3]?number><#case 1>label-danger<#break><#case 2>label-yellow<#break><#case 3>label-warning<#break><#case 4>label-success<#break></#switch><#else>label-info</#if>"
                                                                         data-class="<#if item[3]??><#switch item[3]?number><#case 1>label-danger<#break><#case 2>label-yellow<#break><#case 3>label-warning<#break><#case 4>label-success<#break></#switch><#else>label-info</#if>">
                                                                        <i class="icon-move"></i>
                                                                        <small><span style="display:none">${item[0]},</span>${item[1]}</small>
                                                                    </div>
                                                                </#list>
                                                            </#if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <#--<div class="hr hr-double hr8"></div>-->
                                </div>

                                <#--<div id="member-tab" class="tab-pane">-->
                                    <#--<div class="clearfix">-->
                                        <#--<div class="table-header">-->
                                            <#--SV行事历修改-->
                                        <#--</div>-->

                                        <#--<div class="table-responsive">-->
                                            <#--<table id="sample-table-2"-->
                                                   <#--class="table table-striped table-bordered table-hover ">-->
                                                <#--<thead>-->
                                                <#--<tr>-->
                                                    <#--<th class="center">-->
                                                        <#--<label>-->
                                                            <#--<input type="checkbox" class="ace"/>-->
                                                            <#--<span class="lbl"></span>-->
                                                        <#--</label>-->
                                                    <#--</th>-->
                                                    <#--<th>巡店日期</th>-->
                                                    <#--<th>星期</th>-->
                                                    <#--<th>门店名称</th>-->
                                                    <#--<th class="hidden-480">门店优先级</th>-->
                                                    <#--<th class="hidden-480"><i-->
                                                            <#--class="icon-time bigger-110 hidden-480"></i>巡店时间-->
                                                    <#--</th>-->
                                                    <#--<th class="hidden-480">备注</th>-->
                                                    <#--<th>-->
                                                        <#--更新时间-->
                                                    <#--</th>-->
                                                    <#--<th></th>-->
                                                <#--</tr>-->
                                                <#--</thead>-->

                                                <#--<tbody>-->

                                                <#--</tbody>-->
                                            <#--</table>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="hr hr-double hr8"></div>-->
                                <#--</div>-->
                                <!-- member-tab -->
                            </div>
                        </div>
                        <!-- /widget-main -->
                    </div>
                    <!-- /widget-body -->
                </div>
                <!-- /widget-box -->
            </div>
            <!-- /span -->

            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</div><!-- /.page-content -->