<script language="JavaScript">
    $("#menu000100").addClass("active");
    $("#menu000100").addClass("open");
    $("#menu000103").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            门店优先级
            <small>
                <i class="icon-double-angle-right"></i>
                门店优先级报表查看
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
                                <form action="/priority/list" method="post">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-2">
                                                查询会计月
                                            </td>
                                            <td class="col-xs-4">
                                                <select class="form-control col-xs-4" id="months" name="searchmonth">
                                                    <Option value=""></Option>
                                                <#if months??>
                                                    <#list months as item>
                                                        <Option value="${item[0]}" ${item[2]}>${item[1]}</Option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td class="align-right col-xs-2">
                                                <#if session.account.ORG_TYPE == "100" >
                                                    SV
                                                    <#else>
                                                店铺代码
                                                    </#if>
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="col-xs-4">
                                                <#if session.account.ORG_TYPE == "100" >
                                                    <select class="" id="shops" name="shopcode">
                                                        <Option value="">&nbsp;</Option>
                                                        <#if shops??>
                                                            <#list shops as item>
                                                                <Option value="${item[1]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        </#if>
                                                    </select>
                                                <#else>
                                                    <select class="" id="shops" name="shopcode">
                                                        <Option value="">&nbsp;</Option>
                                                        <#if shops??>
                                                            <#list shops as item>
                                                                <Option value="${item[0]}" ${item[2]}>${item[0]}
                                                                    ,${item[1]}</Option>
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
                        门店优先级列表
                    </div>
                    <div class="table-responsive">
                        <form method="post" name="modfiyPriority" action="/priority/applyModifyOriority">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th class="center">
                                        <label>
                                            <input type="checkbox" class="ace"/>
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <#if session.account.ORG_TYPE == "100" >
                                    <th>
                                        SV
                                    </th>
                                    </#if>
                                    <th>
                                        <small>品牌</small>
                                    </th>
                                    <th>
                                        <small>门店名称</small>
                                    </th>
                                    <th>
                                        <i class="icon-time bigger-110 hidden-480"></i>
                                        <small>年月</small>
                                    </th>
                                    <th>
                                        <small>目标金额</small>
                                    </th>
                                    <th class="hidden-480">
                                        <small>业绩达成率</small>
                                    </th>
                                    <th>
                                        <small>人效</small>
                                    </th>
                                    <th class="hidden-480">
                                        <small>人效得分</small>
                                    </th>
                                    <th>
                                        <small>离职率</small>
                                    </th>
                                    <th class="hidden-480">
                                        <small>离职率得分</small>
                                    </th>
                                    <th>
                                        <small>运营总分</small>
                                    </th>
                                    <th>原</th>
                                    <th>新</th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if priorityList??>
                                    <#list priorityList as mapitem>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" id="cbShop" name="cbMsgId"
                                                       value="${mapitem.SALES_MSG_NUMBER}"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <#if session.account.ORG_TYPE == "100" >
                                            <td>${mapitem.SV}</td>
                                        </#if>
                                        <td>${mapitem.BRAND}</td>
                                        <td>
                                            <small>${mapitem.SHOP_NAME}</small>
                                        </td>
                                        <td>${mapitem.PERIOD}</td>
                                        <td><#if mapitem.SALESGOAL??>${mapitem.SALESGOAL}</#if></td>
                                        <td class="hidden-480"><#if mapitem.SALES_RATE??>${mapitem.SALES_RATE?number?string('#.00')}
                                            %</#if></td>
                                        <td><#if mapitem.PSN_AVE??>${mapitem.PSN_AVE?number?string('#.##')}</#if></td>
                                        <td class="hidden-480"><#if mapitem.PSN_AVE_GOAL??>${mapitem.PSN_AVE_GOAL}</#if></td>
                                        <td><#if mapitem.DIMISSION_RATE??>${mapitem.DIMISSION_RATE?number?string('#.00')}
                                            %</#if></td>
                                        <td class="hidden-480"><#if mapitem.DIMISSION_RATE_GOAL??>${mapitem.DIMISSION_RATE_GOAL}</#if></td>
                                        <td><#if mapitem.OPERATION_GOAL??>${mapitem.OPERATION_GOAL}</#if></td>
                                        <td>
                                            <#if mapitem.PRORITY_ORDER??>
                                                <#switch mapitem.PRORITY_ORDER?number>
                                                    <#case 1>
                                                        <span class="label label-sm label-danger">1</span>
                                                        <#break>
                                                    <#case 2>
                                                        <span class="label label-sm label-yellow">2</span>
                                                        <#break>
                                                    <#case 3>
                                                        <span class="label label-sm label-warning">3</span>
                                                        <#break>
                                                    <#case 4>
                                                        <span class="label label-sm label-success">4</span>
                                                        <#break>
                                                </#switch>
                                            </#if>
                                        </td>
                                        <td><#if mapitem.ATTRIBUTE15??>
                                            <#switch mapitem.ATTRIBUTE15?number>
                                                <#case 1>
                                                    <span class="label label-sm label-danger">1</span>
                                                    <#break>
                                                <#case 2>
                                                    <span class="label label-sm label-yellow">2</span>
                                                    <#break>
                                                <#case 3>
                                                    <span class="label label-sm label-warning">3</span>
                                                    <#break>
                                                <#case 4>
                                                    <span class="label label-sm label-success">4</span>
                                                    <#break>
                                            </#switch>
                                        </#if></td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>

            <div id="modal-table" class="modal fade" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header no-padding">
                            <div class="table-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    <span class="white">&times;</span>
                                </button>
                                门店优先级列表
                            </div>
                        </div>

                        <div class="modal-body no-padding">
                            <table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
                                <thead>
                                <tr>
                                    <th>门店编号</th>
                                    <th>门店名称</th>
                                    <th>
                                        <i class="icon-time bigger-110"></i>
                                        更新时间
                                    </th>
                                    <th>当前优先级</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        1125000047
                                    </td>
                                    <td>上海光新乐购鞋柜</td>
                                    <td>2014-08-22 12:00</td>
                                    <td><span class="label label-sm label-yellow">2</span></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="modal-footer no-margin-top">
                            <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                                <i class="icon-remove"></i>
                                Close
                            </button>

                            <ul class="pagination pull-right no-margin">
                                <li class="prev disabled">
                                    <a href="#">
                                        <i class="icon-double-angle-left"></i>
                                    </a>
                                </li>

                                <li class="active">
                                    <a href="#">1</a>
                                </li>

                                <li>
                                    <a href="#">2</a>
                                </li>

                                <li>
                                    <a href="#">3</a>
                                </li>

                                <li class="next">
                                    <a href="#">
                                        <i class="icon-double-angle-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->