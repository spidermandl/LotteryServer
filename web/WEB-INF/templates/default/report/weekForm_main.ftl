<script language="JavaScript">
    $("#menu000600").addClass("active");
    $("#menu000600").addClass("open");
    $("#menu000602").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            业绩报表
            <small>
                <i class="icon-double-angle-right"></i>
                业绩周报
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
                            <h4>周报表数据查看</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form action="/report/weekForm" method="post">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-1">
                                                账期月
                                            </td>
                                            <td class="col-xs-4">
                                                <select class="col-xs-4 chosen-select" id="months" name="searchmonth">
                                                    <Option value="">&nbsp;&nbsp;</Option>
                                                <#if months??>
                                                    <#list months as item>
                                                        <Option value="${item.XSL_MONTH}" <#if month??><#if month==item.XSL_MONTH>selected="selected"</#if></#if>>${item.XSL_MONTH}</Option>
                                                    </#list>
                                                </#if>
                                                </select>
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
                                                        <#if svs??>
                                                            <#list svs as item>
                                                                <Option value="${item[1]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        </#if>
                                                    </select>
                                                </#if>
                                                </div>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td class="align-right col-xs-2">
                                                *周：
                                            </td>
                                            <td class="col-xs-4">
                                                    <select class="col-xs-4 chosen-select" id="week" name="week">
                                                    <#if weeks??>
                                                        <#list weeks as item>
                                                            <Option value="${item[0]}" ${item[1]}>${item[0]}</Option>
                                                        </#list>
                                                    </#if>
                                                    </select>
                                            </td>
                                            <td class="align-right col-xs-1">
                                                店铺代码
                                            </td>
                                            <td class="col-xs-6">
                                                <div class="col-xs-6">
                                                    <select class="form-control chosen-select" id="shops" name="shopcode">
                                                        <Option value="">&nbsp;&nbsp;</Option>
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
                        周报表平均数据
                    </div>
                    <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        大区
                                    </th>
                                    <th>
                                        经销部
                                    </th>
                                    <th>
                                        报表年份
                                    </th>
                                    <th>
                                        报表年/周
                                    </th>
                                    <th>
                                        鞋类经销部平均单价
                                    </th>
                                    <th>
                                        经销部畅销品销售金额占经销部净销售金额的比例
                                    </th>
                                    <th>
                                        经销部畅销品断码率
                                    </th>
                                    <th>
                                        经销部畅销品周销售速率
                                    </th>
                                    <th>
                                        经销部连带率
                                    </th>
                                    <th>
                                        经销部畅销品门店覆盖率
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                    <#if weekJxbForm??>

									<tr>
                                        <td>
                                            <#if weekJxbForm.SHOP_DAQU??>${weekJxbForm.SHOP_DAQU}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_JXB??>${weekJxbForm.SHOP_JXB}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_YEAR??>${weekJxbForm.SHOP_YEAR}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_WEEK??>${weekJxbForm.SHOP_WEEK}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_XLJXBPJDJ??>${weekJxbForm.SHOP_XLJXBPJDJ}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_JXBCXPXSJEZJXBJXSJEDBL??>${weekJxbForm.SHOP_JXBCXPXSJEZJXBJXSJEDBL}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_JXBCXPDML??>${weekJxbForm.SHOP_JXBCXPDML}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_JXBCXPZXSSL??>${weekJxbForm.SHOP_JXBCXPZXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_JXBLDL??>${weekJxbForm.SHOP_JXBLDL}</#if>
                                        </td>
                                        <td>
                                            <#if weekJxbForm.SHOP_JXBCXPMDFGL??>${weekJxbForm.SHOP_JXBCXPMDFGL}</#if>
                                        </td>
									</tr>
                                    </#if>
                                </tbody>
                            </table>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="table-header">
                        周报表明细列表
                    </div>
                    <div class="table-responsive">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        门店名称
                                    </th>
                                    <th>
                                        销售金额
                                    </th>
                                    <th>
                                        销售金额指标
                                    </th>

                                    <th>
                                        销售指标完成率
                                    </th>
                                    <th>
                                        鞋类销售金额
                                    </th>
                                    <th>
                                        鞋类销售金额占比
                                    </th>
                                    <th>
                                        鞋类销售数量
                                    </th>
                                    <th>
                                        鞋类平均单价
                                    </th>
                                    <th>
                                        新品占销售比例
                                    </th>
                                    <th>
                                        断码率
                                    </th>
                                    <th>
                                        断色率
                                    </th>
                                    <th>
                                        畅销品销售额
                                    </th>
                                    <th>
                                        畅销品销售额占总销售额比例
                                    </th>
                                    <th>
                                        周畅销品销售速率
                                    </th>
                                    <th>
                                        畅销品覆盖率
                                    </th>
                                    <th>
                                        畅销品断码率
                                    </th>
                                    <th>
                                        人效(销售金额)
                                    </th>
                                    <th>
                                        人效(销售数量)
                                    </th>
                                    <th>
                                        上周人效(销售金额)
                                    </th>
                                    <th>
                                        上周人效(销售数量)
                                    </th>
                                    <th>
                                        上周缺编率
                                    </th>
                                    <th>
                                        上上周缺编率
                                    </th>
                                    <th>
                                        上周离职率
                                    </th>
                                    <th>
                                        上上周离职率
                                    </th>
                                    <th>
                                        连带率
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if weekForms??>
                                    <#list weekForms as item>
                                    <tr>
                                        <td width="100px">
                                            <#if item.SHOP_NAME??>${item.SHOP_NAME}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XSJE??>${item.SHOP_XSJE}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XSJEZB??>${item.SHOP_XSJEZB}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XSZBWCL??>${item.SHOP_XSZBWCL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XLXSJE??>${item.SHOP_XLXSJE}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XLXSJEZB??>${item.SHOP_XLXSJEZB}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XLXSSL??>${item.SHOP_XLXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XLPJDJ??>${item.SHOP_XLPJDJ}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XPZXSEBL??>${item.SHOP_XPZXSEBL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_DML??>${item.SHOP_DML}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_DSL??>${item.SHOP_DSL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_CXPXSE??>${item.SHOP_CXPXSE}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_CXPXSEZZXSEBL??>${item.SHOP_CXPXSEZZXSEBL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_ZCXPXSSL??>${item.SHOP_ZCXPXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_CXPFGL??>${item.SHOP_CXPFGL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_CXPDML??>${item.SHOP_CXPDML}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_RXXSJE??>${item.SHOP_RXXSJE}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_RXXSSL??>${item.SHOP_RXXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SZRXXSJE??>${item.SHOP_SZRXXSJE}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SZRXXSSL??>${item.SHOP_SZRXXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SZQBL??>${item.SHOP_SZQBL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SSZQBL??>${item.SHOP_SSZQBL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SZLZL??>${item.SHOP_SZLZL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SSZLZL??>${item.SHOP_SSZLZL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_LDL??>${item.SHOP_LDL}</#if>
                                        </td>
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