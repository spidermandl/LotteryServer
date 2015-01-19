<script language="JavaScript">
    $("#menu000400").addClass("active");
    $("#menu000400").addClass("open");
    $("#menu000404").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            巡店
            <small>
                <i class="icon-double-angle-right"></i>
                门店评估记录
            </small>
        </h1>
    </div>
    <!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box">
                <div class="widget-header">
                    <h4>请选择查询条件</h4>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form action="/inspection/history" method="post" name="form1">
                            <table class="table table-stript table-bordered">
                                <tr>
                                    <td class="align-right col-xs-1">
                                        起始日期
                                    </td>
                                    <td class="col-xs-5">
                                        <div class="col-xs-5">
                                            <input class="form-control" id="start" name="start" <#if end??>value="${start}"</#if>/>
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
                                            <select class="form-control chosen-select" id="svs" name="svorgname">
                                                <Option value="">&nbsp;&nbsp;</Option>
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
                                <tr>
                                    <td class="align-right col-xs-1">
                                        结束日期
                                    </td>
                                    <td class="col-xs-5">
                                        <div class="col-xs-5">
                                            <input class="form-control" id="end" name="end" <#if end??>value="${end}"</#if>/>
                                        </div>
                                    </td>
                                    <td class="align-right col-xs-1">
                                        店铺代码
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
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-header">
                        门店评估历史记录
                    </div>
                    <div class="table-responsive">
                        <form method="post" name="modfiyPriority" action="#">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                    </th>
                                <#if session.account.ORG_TYPE == "100" >
                                    <th>
                                        SV
                                    </th>
                                </#if>
                                	
                                    <th>
                                        <small>门店名称</small>
                                    </th>
                                    <th>
                                        <small>店长等级</small>
                                    </th>
                                    <th>
                                        <i class="icon-time bigger-110 hidden-480"></i>
                                        <small>检查日期</small>
                                    </th>
                                    <th>
                                        <small>重点评估</small>
                                    </th>
                                    <th class="hidden-480">
                                        <small>重点问题</small>
                                    </th>
                                    <th>
                                        <small>确认时间</small>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if records??>
                                    <#list records as mapitem>
                                    <tr>
                                        <td><div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                                            <a class="blue" href="/inspection/detailInspection?id=${mapitem.CHECK_HEAD_ID}">
                                                <i class="icon-zoom-in bigger-130"></i>
                                            </a></div></td>
                                        <#if session.account.ORG_TYPE == "100" >
                                            <td>${mapitem.USER_ID}</td>
                                        </#if>
                                        <td><#if mapitem.ATTRIBUTE_2??>${mapitem.ATTRIBUTE_2}</#if></td>
                                        <td><#if mapitem.MAG_LEVEL??>${mapitem.MAG_LEVEL}</#if></td>
                                        <td>
                                            <#if mapitem.CHECK_DATE??>${mapitem.CHECK_DATE}</#if>
                                        </td>
                                        <td>
                                            <small><#if mapitem.ZDPG??>${mapitem.ZDPG}</#if></small>
                                        </td>
                                        <td>
                                            <small><#if mapitem.PGWT??>${mapitem.PGWT}</#if></small>
                                        </td>
                                        <td>
                                            <small><#if mapitem.UPDATE_DATE??>${mapitem.UPDATE_DATE}</#if></small>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</div>