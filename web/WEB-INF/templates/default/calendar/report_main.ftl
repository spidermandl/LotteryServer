<script language="JavaScript">
    $("#menu000200").addClass("active");
    $("#menu000200").addClass("open");
    $("#menu000203").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            行事历
            <small>
                <i class="icon-double-angle-right"></i>
                汇总报表
            </small>
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="widget-box">
                        <div class="widget-header">
                            <h4>请选择查询条件</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form action="/calendar/history" method="post" name="form1">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-1">
                                                年月
                                            </td>
                                            <td class="col-xs-5">
                                                <div class="col-xs-5">
                                                    <select class="form-control chosen-select" id="month" name="month">
                                                        <#if months??>
                                                            <#list months as item>
                                                                <Option value="${item.XSL_MONTH}" <#if month??><#if month==item.XSL_MONTH>selected="selected"</#if></#if>>${item.XSL_MONTH}</Option>
                                                            </#list>
                                                        </#if>
                                                    </select>
                                                </div>
                                            </td>
                                            <td class="align-right col-xs-1">
                                            <#if session.account.ORG_TYPE == '100'>
                                                SV
                                            </#if>
                                            </td>
                                            <td class="col-xs-6">
                                                <div class="col-xs-6">
                                                <#if session.account.ORG_TYPE == '100'>
                                                    <select class="form-control chosen-select" id="sv" name="sv">
                                                        <#if svs??>
                                                            <#list svs as item>
                                                                <Option value="${item[0]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        </#if>
                                                    </select>
                                                </#if>
                                                </div>
                                            </td>

                                        </tr>
                                    </table>
                                    <div class="form-actions center">
                                        <button type="submit" class="btn btn-sm btn-success">
                                            查询
                                            <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-header">
                        汇总报表
                    </div>
                    <div class="table-responsive">
                        <table id="prioritylist" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    年月
                                </th>
                                <th>
                                    SV
                                </th>
                                <th>
                                    门店代码
                                </th>
                                <th>
                                    门店名称
                                </th>
                                <th>
                                    优先级
                                </th>
                                <th>
                                    标准巡店次数
                                </th>
                                <th>
                                    巡店次数(终版)
                                </th>
                                <th>
                                    巡店次数差异
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <#if params??>
                                <#list params as param>
                                <tr>
                                    <td><#if param[0]??>${param[0]}</#if></td>
                                    <td><#if param[1]??>${param[1]}</#if></td>
                                    <td><#if param[2]??>${param[2]}</#if></td>
                                    <td><#if param[3]??>${param[3]}</#if></td>
                                    <td><#if param[4]??>${param[4]}</#if></td>
                                    <td><#if param[5]??>${param[5]}</#if></td>
                                    <td><#if param[6]??>${param[6]}</#if></td>
                                    <td><#if param[7]??>${param[7]}</#if></td>
                                </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->