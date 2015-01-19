<script language="JavaScript">
    $("#menu000600").addClass("active");
    $("#menu000600").addClass("open");
    $("#menu000601").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            业绩报表
            <small>
                <i class="icon-double-angle-right"></i>
                业绩日报
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
                            <h4>日报表数据查看</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form action="/report/dayForm" method="post">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-2">
                                                报表日期：
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="col-xs-8">
                                                    <input class="form-control" id="day" name="day" <#if day??>value="${day}"</#if>>
                                                    </input>
                                                </div>
                                            </td>
                                            <td class="align-right col-xs-2">
                                                <#if session.account.ORG_TYPE == '100'>
                                                    SV：
                                                <#else>
                                                    店铺：
                                                </#if>
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="col-xs-8">
                                                    <select class="form-control" id="sv" name="sv">
                                                    <#if svs??>
                                                        <#if session.account.ORG_TYPE == '100'>
                                                            <#list svs as item>
                                                                <Option value="${item[1]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        <#else>
                                                            <#list svs as item>
                                                                <Option value="${item[0]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        </#if>
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
                        日报表平均数据
                    </div>
                    <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        日期
                                    </th>
                                    <th>
                                        大区
                                    </th>
                                    <th>
                                        经销部
                                    </th>
                                    <th>
                                        报表时间
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
                                        经销部畅销品日销售速率
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
                                    <#if dayJabForm??>

									<tr>
										<td>
											<#if day??>${day}</#if>
										</td>
                                        <td>
                                            <#if dayJabForm.SHOP_DAQU??>${dayJabForm.SHOP_DAQU}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_JXB??>${dayJabForm.SHOP_JXB}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_DAY_DATE??>${dayJabForm.SHOP_DAY_DATE}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_XLJXBPJDJ??>${dayJabForm.SHOP_XLJXBPJDJ}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_JXBCXPXSJEZJXBJXSJEDBL??>${dayJabForm.SHOP_JXBCXPXSJEZJXBJXSJEDBL}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_JXBCXPDML??>${dayJabForm.SHOP_JXBCXPDML}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_JXBCXPRXSSL??>${dayJabForm.SHOP_JXBCXPRXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_JXBLDL??>${dayJabForm.SHOP_JXBLDL}</#if>
                                        </td>
                                        <td>
                                            <#if dayJabForm.SHOP_JXBCXPMDFGL??>${dayJabForm.SHOP_JXBCXPMDFGL}</#if>
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
                        日报表明细列表
                    </div>
                    <div class="table-responsive">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th width="100px">
                                        门店名称
                                    </th>
                                    <th>
                                        销售金额
                                    </th>
                                    <th>
                                        销售金额指标
                                    </th>
                                    <th>
                                        本周累计销售金额
                                    </th>
                                    <th>
                                        本周累计销售金额指标
                                    </th>
                                    <th>
                                        本周累计销售完成率
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
                                        本周累计鞋类销售数量
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
                                        日畅销品销售速率
                                    </th>
                                    <th>
                                        畅销品覆盖率
                                    </th>
                                    <th>
                                        畅销品断码率
                                    </th>
                                    <th>
                                        连带率
                                    </th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if dayForms??>
                                    <#list dayForms as item>
                                    <tr>
                                        <td>
                                            <#if item.SHOP_NAME??>${item.SHOP_NAME}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_SALE_MONEY??>${item.SHOP_SALE_MONEY}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_XSJEZB??>${item.SHOP_XSJEZB}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_BZLJXSJE??>${item.SHOP_BZLJXSJE}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_BZLJXSJEZB??>${item.SHOP_BZLJXSJEZB}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_BZLJXSWCL??>${item.SHOP_BZLJXSWCL}</#if>
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
                                            <#if item.SHOP_BZLJXLXSSL??>${item.SHOP_BZLJXLXSSL}</#if>
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
                                            <#if item.SHOP_RXSPXSSL??>${item.SHOP_RXSPXSSL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_CXPFGL??>${item.SHOP_CXPFGL}</#if>
                                        </td>
                                        <td>
                                            <#if item.SHOP_CXPDML??>${item.SHOP_CXPDML}</#if>
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