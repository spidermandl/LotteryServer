<script language="JavaScript">
    $("#menu000400").addClass("active");
    $("#menu000400").addClass("open");
    $("#menu000402").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            巡店
            <small>
                <i class="icon-double-angle-right"></i>
                签到签退记录
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
                                <form action="/sign/history" method="post" name="form1">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-1">
                                                起始日期
                                            </td>
                                            <td class="col-xs-5">
                                                <div class="col-xs-5">
                                                    <input class="form-control" id="start" name="start"
                                                           <#if end??>value="${start}"</#if>/>
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
                                                    <select class="form-control chosen-select" id="svs"
                                                            name="svorgname">
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
                                                    <input class="form-control" id="end" name="end"
                                                           <#if end??>value="${end}"</#if>/>
                                                </div>
                                            </td>
                                            <td class="align-right col-xs-1">
                                                店铺代码
                                            </td>
                                            <td class="col-xs-6">
                                                <div class="col-xs-6">
                                                    <select class="form-control chosen-select" id="shops"
                                                            name="shopcode">
                                                        <Option value="">&nbsp;</Option>
                                                    <#if shops??>
                                                        <#list shops as item>
                                                            <Option value="${item[0]}" ${item[2]}>${item[0]}
                                                                ,${item[1]}</Option>
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
                        历史记录列表
                    </div>
                    <div class="table-responsive">
                        <table id="prioritylist" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    门店名称
                                </th>
                                <th>
                                    签到时间
                                </th>
                                <th>
                                    签到地址
                                </th>
                                <th>
                                    签退时间
                                </th>
                                <th>
                                    签退地址
                                </th>
                                <th>
                                    店铺地址
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <#if records??>
                                <#list records as mapitem>
                                <tr>
                                    <td>
                                        <#if mapitem.SHOP_NAME??>${mapitem.SHOP_NAME}</#if>
                                    </td>
                                    <td>${mapitem.CHECKIN_TIME}</td>
                                    <td>
                                        <#if mapitem.SIGN_ADDR??>${mapitem.SIGN_ADDR}</#if>
                                    </td>
                                    <td><#if mapitem.CHECKOUT_TIME??>${mapitem.CHECKOUT_TIME}</#if></td>
                                    <td>
                                        <#if mapitem.SIGN_OUT_ADDR??>${mapitem.SIGN_OUT_ADDR}</#if>
                                    </td>
                                    <td><#if mapitem.COMMENTS??>${mapitem.COMMENTS}</#if></td>
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