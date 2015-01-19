<script language="JavaScript">
    $("#menu000400").addClass("active");
    $("#menu000400").addClass("open");
    $("#menu000403").addClass("active");
</script>
<div class="page-content">
<div class="page-header">
    <h1>
        巡店
        <small>
            <i class="icon-double-angle-right"></i>
            门店评估
        </small>
    </h1>
</div>
<!-- /.page-header -->

<div class="row">
<div class="col-xs-12">
<div class="row">
<div class="col-sm-10 col-sm-offset-1">
<div class="widget-box transparent invoice-box">
<div class="widget-header widget-header-large">
    <h3 class="grey lighter pull-left position-relative">
        <i class="icon-leaf green"></i>
    ${shopName}
    </h3>

    <div class="widget-toolbar no-border  hidden-480">
        <span class="invoice-info-label">检查单号:</span>
        <span class="red"><#if checkId??>${checkId}</#if></span>
    </div>

    <div class="widget-toolbar hidden-480">
        <a href="#">
            <i class="icon-print"></i>
        </a>
    </div>
</div>

<div class="widget-body">
<div class="widget-main padding-24">


<div class="row">
    <div class="col-sm-6">
        <div class="row">
            <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                <b>门店信息</b>
            </div>
        </div>
<form method="post" id="addInspection" action="/inspection/addInspection">
<input type="hidden" name="shopCode" value="${shopCode}">
<input type="hidden" name="checkId" value="${checkId}">
<input type="hidden" name="shopName" value="${shopName}">
<input type="hidden" name="shopLevel" value="${shopLevel}">
        <div class="row">
            <ul class="list-unstyled spaced">
                <li>
                    <i class="icon-caret-right blue"></i>
                    门店名称：<#if shopName??>${shopName}</#if>
                </li>
                <li>
                    <i class="icon-caret-right blue"></i>
                    门店分级：<#if shopLevel??>${shopLevel}</#if>
                </li>
                <li>
                    <i class="icon-caret-right blue"></i>
                    店长分级：<select name="ownerType">
                    <option value="A">A</option>
                    <option value="B">B</option>
                    <option value="C">C</option>
                    <option value="D">D</option>
                </select>
                </li>
                <li>
                    <i class="icon-caret-right blue"></i>
                    评估日期：<input id="checkdate" name="checkDate" <#if pgdate??>value="${pgdate}"</#if>>
                </select>
                </li>

            </ul>
        </div>
    </div>
    <!-- /span -->

    <div class="col-sm-6">
        <div class="row">
            <div class="col-xs-11 label label-lg label-success arrowed-in arrowed-right">
                <b>门店检查事项</b>
            </div>
        </div>

        <div>
            <ul class="list-unstyled  spaced">
                <li>
                    <i class="icon-caret-right green"></i>
                    重点评估项：<select id="form-field-select-2" multiple="" class="width-80 chosen-select" name="keyOptions">
                    <option value="件单价">件单价</option>
                    <option value="销量">销量</option>
                    <option value="连带销售率">连带销售率</option>
                    <option value="缺编率">缺编率</option>
                    <option value="离职率">离职率</option>
                    <option value="人效">人效</option>
                </select>
                </li>

                <li>
                    <i class="icon-caret-right green"></i>
                    评估发现重点问题：<select id="form-field-select-3" multiple="" class="width-80 chosen-select"
                                     name="keyProblems">
                    <option value="新老品比例">新老品比例</option>
                    <option value="客流量">客流量</option>
                    <option value="成交率">成交率</option>
                    <option value="连带销售率过低">连带销售率过低</option>
                    <option value="店长缺编">店长缺编</option>
                    <option value="总编制问题">总编制问题</option>
                    <option value="新人入职">新人入职</option>
                    <option value="离职人员">离职人员</option>
                    <option value="人效问题">人效问题</option>
                </select>
                </li>

                <li>
                    <i class="icon-caret-right green"></i>
                    不合格优先排序指标：<select id="form-field-select-4" multiple="" class="width-80 chosen-select"
                                     name="theKeyFirst">
                    <option value="营业额">营业额</option>
                    <option value="人效">人效</option>
                    <option value="离职率">离职率</option>
                    <option value="顾客体验自检">顾客体验自检</option>
                </select>
                </li>
                <li>
                    <i class="icon-caret-right green"></i>
                    日报周报不合格指标项：<select id="form-field-select-5" multiple="" class="width-80 chosen-select"
                                     name="theKeyOption">
                    <option value="件单价">件单价</option>
                    <option value="销量">销量</option>
                    <option value="连带销售率">连带销售率</option>
                    <option value="缺编率">缺编率</option>
                    <option value="离职率">离职率</option>
                    <option value="人效">人效</option>
                </select>
                </li>
            </ul>
        </div>
    </div>
    <!-- /span -->
</div>
<!-- row -->
<div class="hr hr-18 hr-double dotted"></div>

<div class="row-fluid">
<div class="span12">
<div class="widget-box">
<div class="widget-header widget-header-blue widget-header-flat">
    <h4 class="lighter">门店评估检查</h4>

<#--<div class="widget-toolbar">-->
<#--<label>-->
<#--<small class="green">-->
<#--<b>校验</b>-->
<#--</small>-->

<#--<input id="skip-validation" type="checkbox" class="ace ace-switch ace-switch-4"/>-->
<#--<span class="lbl"></span>-->
<#--</label>-->
<#--</div>-->
</div>

<div class="widget-body">
<div class="widget-main">
<div id="fuelux-wizard" class="row-fluid" data-target="#step-container">
    <ul class="wizard-steps">
        <li data-target="#step1" class="active">
            <span class="step">1</span>
            <span class="title">店外环境(观察)</span>
        </li>

        <li data-target="#step2">
            <span class="step">2</span>
            <span class="title">店内管理(观察)</span>
        </li>

        <li data-target="#step3">
            <span class="step">3</span>
            <span class="title">顾客服务(观察5位客户)</span>
        </li>

        <li data-target="#step4">
            <span class="step">4</span>
            <span class="title">运营管理(与店长沟通)</span>
        </li>
        <li data-target="#step5">
            <span class="step">5</span>
            <span class="title">库存管理(查看报表及仓库)</span>
        </li>
        <li data-target="#step6">
            <span class="step">6</span>
            <span class="title">总部重点关注问题</span>
        </li>
        <li data-target="#step7">
            <span class="step">7</span>
            <span class="title">完成</span>
        </li>
    </ul>
</div>

<hr/>
<div class="step-content row-fluid position-relative" id="step-container">
    <div class="step-pane active" id="step1">
        <div class="center">
        <#if items1??>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>项目编号</th>
                    <th>检查项目</th>
                    <th>是/否</th>
                    <th>问题描述</th>
                </tr>
                </thead>
                <tbody>
                    <#list items1 as item>
                    <tr>
                    	<input type="hidden" name="itemB" value="<#if item.B??>${item.B}</#if>">
                    	<input type="hidden" name="itemC" value="<#if item.C??>${item.C}</#if>">
                    	<input type="hidden" name="itemD" value="${item.D}">
                        <th><#if item.B??>${item.B}</#if></th>
                        <th><#if item.C??>${item.C}</#if></th>
                        <td><select name="itemYes">
                            <option value="Y" <#if item.F??><#if item.F == "Y">selected</#if></#if>>是</option>
                            <option value="N" <#if item.F??><#if item.F == "N">selected</#if></#if>>否</option>
                        </select></td>
                        <td><input type="text" name="itemInput"></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>

    <div class="step-pane" id="step2">
        <div class="center">
        <#if items2??>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>项目编号</th>
                    <th>检查项目</th>
                    <th>是/否</th>
                    <th>问题描述</th>
                </tr>
                </thead>
                <tbody>
                    <#list items2 as item>
                    <tr>
                    	<input type="hidden" name="itemB" value="<#if item.B??>${item.B}</#if>">
                    	<input type="hidden" name="itemC" value="<#if item.C??>${item.C}</#if>">
                    	<input type="hidden" name="itemD" value="${item.D}">
                        <th><#if item.B??>${item.B}</#if></th>
                        <th><#if item.C??>${item.C}</#if></th>
                        <td><select name="itemYes">
                            <option value="Y" <#if item.F??><#if item.F == "Y">selected</#if></#if>>是</option>
                            <option value="N" <#if item.F??><#if item.F == "N">selected</#if></#if>>否</option>
                        </select></td>
                        <td><input type="text" name="itemInput" ></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>

    <div class="step-pane" id="step3">
        <div class="center">
        <#if items3??>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>项目编号</th>
                    <th>检查项目</th>
                    <th>是/否</th>
                    <th>问题描述</th>
                </tr>
                </thead>
                <tbody>
                    <#list items3 as item>
                    <tr>
                    	<input type="hidden" name="itemB" value="<#if item.B??>${item.B}</#if>">
                    	<input type="hidden" name="itemC" value="<#if item.C??>${item.C}</#if>">
                    	<input type="hidden" name="itemD" value="${item.D}">
                        <th><#if item.B??>${item.B}</#if></th>
                        <th><#if item.C??>${item.C}</#if></th>
                        <td><select name="itemYes" >
                            <option value="Y" <#if item.F??><#if item.F == "Y">selected</#if></#if>>是</option>
                            <option value="N" <#if item.F??><#if item.F == "N">selected</#if></#if>>否</option>
                        </select></td>
                        <td><input type="text" name="itemInput"  ></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>
    <div class="step-pane" id="step4">
        <div class="center">
        <#if items4??>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>项目编号</th>
                    <th>检查项目</th>
                    <th>是/否</th>
                    <th>问题描述</th>
                </tr>
                </thead>
                <tbody>
                    <#list items4 as item>
                    <tr>
                    	<input type="hidden" name="itemB" value="<#if item.B??>${item.B}</#if>">
                    	<input type="hidden" name="itemC" value="<#if item.C??>${item.C}</#if>">
                    	<input type="hidden" name="itemD" value="${item.D}">
                        <th><#if item.B??>${item.B}</#if></th>
                        <th><#if item.C??>${item.C}</#if></th>
                        <td><select name="itemYes"  >
                            <option value="Y" <#if item.F??><#if item.F == "Y">selected</#if></#if>>是</option>
                            <option value="N" <#if item.F??><#if item.F == "N">selected</#if></#if>>否</option>
                        </select></td>
                        <td><input type="text" name="itemInput"  ></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>
    <div class="step-pane" id="step5">
        <div class="center">
        <#if items5??>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>项目编号</th>
                    <th>检查项目</th>
                    <th>是/否</th>
                    <th>问题描述</th>
                </tr>
                </thead>
                <tbody>
                    <#list items5 as item>
                    <tr>
                    	<input type="hidden" name="itemB" value="<#if item.B??>${item.B}</#if>">
                    	<input type="hidden" name="itemC" value="<#if item.C??>${item.C}</#if>">
                    	<input type="hidden" name="itemD" value="${item.D}">
                        <th><#if item.B??>${item.B}</#if></th>
                        <th><#if item.C??>${item.C}</#if></th>
                        <td><select name="itemYes">
                            <option value="Y" <#if item.F??><#if item.F == "Y">selected</#if></#if>>是</option>
                            <option value="N" <#if item.F??><#if item.F == "N">selected</#if></#if>>否</option>
                        </select></td>
                        <td><input type="text" name="itemInput"  ></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>
    <div class="step-pane" id="step6">
        <div class="center">
        <#if items6??>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>项目编号</th>
                    <th>检查项目</th>
                    <th>是/否</th>
                    <th>问题描述</th>
                </tr>
                </thead>
                <tbody>
                    <#list items6 as item>
                    <tr>
                        <input type="hidden" name="itemB" value="<#if item.B??>${item.B}</#if>">
                        <input type="hidden" name="itemC" value="<#if item.C??>${item.C}</#if>">
                        <input type="hidden" name="itemD" value="${item.D}">
                        <th><#if item.B??>${item.B}</#if></th>
                        <th><#if item.C??>${item.C}</#if></th>
                        <td><select  name="itemYes">
                            <option value="Y" <#if item.F??><#if item.F == "Y">selected</#if></#if>>是</option>
                            <option value="N" <#if item.F??><#if item.F == "N">selected</#if></#if>>否</option>
                        </select></td>
                        <td><input type="text" name="itemInput" ></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>
    <div class="step-pane" id="step7">
        <div class="center">
            <h3 class="green">巡店完毕，请保存数据!</h3>
        </div>
    </div>
</div>
</form>
<hr/>
<div class="row-fluid wizard-actions">
    <button class="btn btn-prev">
        <i class="icon-arrow-left"></i>
        前一步
    </button>

    <button class="btn btn-success btn-next" data-last="完成">
        下一步
        <i class="icon-arrow-right icon-on-right"></i>
    </button>
</div>

</div>
<!-- /widget-main -->
</div>
<!-- /widget-body -->
</div>
</div>
</div>

</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>