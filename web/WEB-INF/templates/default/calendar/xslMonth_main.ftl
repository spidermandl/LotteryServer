<script language="JavaScript">
    $("#menu000700").addClass("active");
    $("#menu000700").addClass("open");
    $("#menu000701").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            系统维护
            <small>
                <i class="icon-double-angle-right"></i>
                行事历账期维护
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
                            <h4>账期月编辑</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form action="/calendar/saveXslMonth" method="post" name="form1">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-1">
                                                账期月
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="col-xs-4">
                                                    <#--<select class="form-control" id="months" name="months">-->
                                                        <#--<Option value="">&nbsp;&nbsp;</Option>-->
                                                        <#--<#if months??>-->
                                                            <#--<#list months as item>-->
                                                                <#--<Option value="${item.MEANING}">${item.MEANING}</Option>-->
                                                            <#--</#list>-->
                                                        <#--</#if>-->
                                                    <#--</select>-->
                                                        <input class="form-control" onchange="" id="months" name="months" onclick="monthSelector()"/>
                                                </div>
                                            </td>
                                            <td class="align-right col-xs-1">
                                                起始日期
                                            </td>
                                            <td class="col-xs-5">
                                                <div class="col-xs-5">
                                                    <input class="form-control" id="start" name="start" <#if end??>value="${start}"</#if>/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align-right col-xs-1">
                                            </td>
                                            <td class="col-xs-4">
                                            </td>
                                            <td class="align-right col-xs-1">
                                                结束日期
                                            </td>
                                            <td class="col-xs-5">
                                                <div class="col-xs-5">
                                                    <input class="form-control" id="end" name="end" <#if end??>value="${end}"</#if>/>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <div class="form-actions center">
                                        <button id="btnSubmit" type="submit" class="btn btn-sm btn-success" disabled>
                                            &nbsp;保&nbsp;&nbsp;存&nbsp;
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
                        账期月列表
                    </div>
                    <div class="table-responsive">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                    </th>
                                    <th>
                                        月份
                                    </th>
                                    <th>
                                        开始日期
                                    </th>
                                    <th>
                                        结束日期
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if xslMonths??>
                                    <#list xslMonths as mapitem>
                                    <tr>
                                        <td class="col-xs-1 center"><div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                            <a class="blue" href="javascript:selectMonth('${mapitem.XSL_MONTH}','${mapitem.M_START}','${mapitem.N_END}');">
                                                <i class="icon-edit bigger-130"></i>
                                            </a></div></td>
                                        <td>
                                            <#if mapitem.XSL_MONTH??>${mapitem.XSL_MONTH}</#if>
                                        </td>
                                        <td><#if mapitem.M_START??>${mapitem.M_START}</#if></td>
                                        <td><#if mapitem.N_END??>${mapitem.N_END}</#if></td>
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