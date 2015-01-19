<script language="JavaScript">
    $("#menu000500").addClass("active");
    $("#menu000500").addClass("open");
    $("#menu000502").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            周工作汇报
            <small>
                <i class="icon-double-angle-right"></i>
                历史记录
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
                            <h4>请输入查询条件</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form action="/week/reportHistory" method="post">
                                    <table class="table table-stript table-bordered">
                                        <#if session.account.ORG_TYPE == '100'>
                                        <tr>
                                            <td></td><td></td>
                                            <td class="align-right col-xs-1">
                                                SV
                                            </td>
                                            <td class="col-xs-6">
                                                <div class="col-xs-6">
                                                    <select class="form-control chosen-select" id="svs" name="svorgname">
                                                        <Option value="">&nbsp;&nbsp;</Option>
                                                        <#if svs??>
                                                            <#list svs as item>
                                                                <Option value="${item[0]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        </#if>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>
                                        </#if>
                                        <tr>
                                            <td class="align-right col-xs-2">
                                                查询会计月
                                            </td>
                                            <td class="col-xs-4">
                                                <select class="col-xs-4 chosen-select" id="months" name="searchmonth">
                                                    <Option value="">&nbsp;</Option>
                                                <#if months??>
                                                    <#list months as item>
                                                        <Option value="${item.XSL_MONTH}" <#if month??><#if month==item.XSL_MONTH>selected="selected"</#if></#if>>${item.XSL_MONTH}</Option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td class="align-right col-xs-2">
                                                店铺
                                            </td>
                                            <td class="col-xs-6">
                                                <div class="col-xs-6">
                                                    <select class="form-control chosen-select" id="shops" name="shopcode">
                                                        <Option value="">&nbsp;</Option>
                                                    <#if shops??>
                                                        <#list shops as item>
                                                            <Option value="${item[0]}" ${item[2]}>${item[0]},${item[1]}</Option>
                                                        </#list>
                                                    </#if>
                                                    </select>
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
                        周工作汇报历史记录
                    </div>
                    <div class="table-responsive">
                        <form method="post" name="modfiyPriority" action="/week/reportSearch">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th class="center">
                                    </th>
                                    <th>
                                        汇报单号
                                    </th>
                                    <th>
                                        <small>门店名称</small>
                                    </th>
                                    <th>
                                        <i class="icon-time bigger-110 hidden-480"></i>
                                        月份
                                    </th>
                                    <th>
                                        门店等级
                                    </th>
                                    <th>
                                        汇报时间
                                    </th>
                                   
                                </tr>
                                </thead>

                                <tbody>
									<#list reportlist as report>
									<tr>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons center">
                                                <a class="blue" href="/week/reportDetail?shop=${report.SHOP_CODE}&month=${report.C_MOUTH}">
                                                    <i class="icon-zoom-in bigger-110"></i>
                                                </a></div>
                                        </td>
										<td>
											<#if report.APPLY_NUMBER??>${report.APPLY_NUMBER}</#if>
										</td>
										<td>
											<#if report.SHOP_NAME??>${report.SHOP_NAME}</#if>
										</td>
										<td>
											<#if report.C_MOUTH??>${report.C_MOUTH}</#if>
										</td>
										<td>
											<#if report.SHOP_DJ??>${report.SHOP_DJ}</#if>
										</td>
										<td>
											<#if report.ATTRIBUTE_2??>${report.ATTRIBUTE_2}</#if>
										</td>
									</tr>
									</#list>
                                </tbody>
                            </table>
                        <#--<div class="form-actions center">-->
                        <#--<button type="submit" class="btn btn-sm btn-info">-->
                        <#--申请修改优先级-->
                        <#--<i class="icon-arrow-right icon-on-right bigger-110"></i>-->
                        <#--</button>-->
                        <#--</div>-->
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->