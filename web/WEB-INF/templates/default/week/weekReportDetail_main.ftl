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
                工作汇报新增/编辑
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
                            <h4></h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-center col-xs-2">
                                                门店
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="">
                                                <#if shop??>${shop.ORG_NAME}</#if>
                                                </div>
                                            </td>
                                            <td class="align-center col-xs-2">
                                                门店等级
                                            </td>
                                            <td class="">
                                                <div class="">
                                                <#if shoplevel??>${shoplevel}</#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="align-center col-xs-2">
                                                月份
                                            </td>
                                            <td class="">
                                                <div class="">
                                                <#if month??>${month}</#if>
                                                </div>
                                            </td>
                                            <td class="align-center col-xs-2">
                                                评估日期
                                            </td>
                                            <td class="">
                                                <div class="">
                                                <#if pgdate??>${pgdate}</#if>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-responsive">
                        <table id="prioritylist" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    编号
                                </th>
                                <th>
                                    问题项目
                                </th>
                                <th>
                                    问题描述
                                </th>
                                <th>
                                    总部支援
                                </th>
                                <th>
                                    解决方案
                                </th>
                                <th>
                                    需要资源
                                </th>
                                <th>
                                    追踪结果一
                                </th>
                                <th>
                                    追踪结果二
                                </th>
                                <th>
                                    追踪结果三
                                </th>
                                <th>
                                    追踪结果四
                                </th>
                                <th>
                                    追踪结果五
                                </th>
                                <th>
                                    完成日期
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <#if records??>
                                <#list records as mapitem>
                                <tr>
                                    <td><#if mapitem.LINE_ID??>${mapitem.LINE_ID}</#if></td>
                                    <td><#if mapitem.QUESTION_VALUE??>${mapitem.QUESTION_VALUE}</#if></td>
                                    <td><#if mapitem.QUESTION_DESCRIBE??>${mapitem.QUESTION_DESCRIBE}</#if></td>
                                    <td><#if mapitem.ATTRIBUTE11??><#if mapitem.ATTRIBUTE11=='Y'>是<#else>否</#if></#if></td>
                                    <td><#if mapitem.SOVLE_CASE??>${mapitem.SOVLE_CASE}</#if></td>
                                    <td><#if mapitem.NEED_RESOURCE??>${mapitem.NEED_RESOURCE}</#if></td>
                                    <td><#if mapitem.STORE_RESULT??>${mapitem.STORE_RESULT}</#if></td>
                                    <td><#if mapitem.ATTRIBUTE13??>${mapitem.ATTRIBUTE13}</#if></td>
                                    <td><#if mapitem.ATTRIBUTE14??>${mapitem.ATTRIBUTE14}</#if></td>
                                    <td><#if mapitem.ATTRIBUTE15??>${mapitem.ATTRIBUTE15}</#if></td>
                                    <td><#if mapitem.ATTRIBUTE12??>${mapitem.ATTRIBUTE12}</#if></td>
                                    <td><#if mapitem.END_DATE??>${mapitem.END_DATE}</#if></td>
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