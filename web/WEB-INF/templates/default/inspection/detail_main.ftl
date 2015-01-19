<script language="JavaScript">
    $("#menu000400").addClass("active");
    $("#menu000400").addClass("open");
    $("#menu000404").addClass("active");
</script>
<div class="page-content">
<div class="page-header">
    <h1>
        门店评估
        <small>
            <i class="icon-double-angle-right"></i>
            门店评估检查详情
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
    ${head.ATTRIBUTE_2}
    </h3>

    <div class="widget-toolbar no-border  hidden-480">
        <span class="invoice-info-label">检查单号:</span>
        <span class="red"><#if head.CHECK_HEAD_ID??>${head.CHECK_HEAD_ID}</#if></span>
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

        <div class="row">
            <ul class="list-unstyled spaced">
                <li>
                    <i class="icon-caret-right blue"></i>
                    门店名称：${head.ATTRIBUTE_2}
                </li>
                <li>
                    <i class="icon-caret-right blue"></i>
                    门店分级：${head.STORE_LEVEL}
                </li>
                <li>
                    <i class="icon-caret-right blue"></i>
                    店长分级：${head.MAG_LEVEL}
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
                    重点评估项：${head.ZDPG}
                </li>

                <li>
                    <i class="icon-caret-right green"></i>
                    评估发现重点问题：${head.PGWT}
                </li>

                <li>
                    <i class="icon-caret-right green"></i>
                    不合格优先排序指标：${head.BHGYXPX}
                </li>
                <li>
                    <i class="icon-caret-right green"></i>
                    日报周报不合格指标项：${head.RBBHG}
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


<div class="step-content row-fluid position-relative" id="step-container">
    <div class="step-pane active" id="step1">
        <div class="center">
        <#if lineList??>
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
                    <#list lineList as item>
                    <tr>
                        <th><#if item.TYPE_CODE??>${item.TYPE_CODE}</#if></th>
                        <th><#if item.TYPE_NAME??>${item.TYPE_NAME}</#if></th>
                        <td><#if item.TYPE_Y=='Y'>是<#else>否</#if></td>
                        <td><#if item.COMMENTS??>${item.COMMENTS}</#if></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>

    
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