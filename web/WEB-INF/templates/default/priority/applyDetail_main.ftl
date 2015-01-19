<script language="JavaScript">
    $("#menu000100").addClass("active");
    $("#menu000100").addClass("open");
    $("#menu000101").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            门店优先级
            <small>
                <i class="icon-double-angle-right"></i>
                门店优先级变更申请
            </small>
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-header">
                        门店优先级变更申请列表
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
                                    <th>
                                        申请表编号
                                    </th>
                                    <th>
                                        申请人
                                    </th>
                                    <th>
                                        <i class="icon-time bigger-110 hidden-480"></i>
                                        <small>申请时间</small>
                                    </th>
                                    <th>
                                        <small>申请变更数量</small>
                                    </th>
                                    <th>
                                        <i class="icon-time bigger-110 hidden-480"></i>
                                        <small>更新时间</small>
                                    </th>
                                    <th>
                                        <small>申请状态</small>
                                    </th>
                                    <th>操作</th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if applyHeadList??>
                                    <#list applyHeadList as mapitem>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" id="head_id" name="headid"
                                                       value="${mapitem.APPLY_NUMBER}"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>

                                        <td>${mapitem.APPLY_NUMBER}</td>
                                        <td><#if mapitem.ATTRIBUTE14??>${mapitem.ATTRIBUTE14}</#if></td>
                                        <td>
                                            <small>${mapitem.APPLY_TIME}</small>
                                        </td>
                                        <td>${mapitem.ATTRIBUTE15}</td>
                                        <td>
                                            <small>${mapitem.LAST_UPDATE_DATE}</small>
                                        </td>
                                        <td><#if mapitem.STATUS??>
                                            <#if mapitem.STATUS='a'>
                                                审批通过
                                            <#elseif mapitem.STATUS='u'>
                                                审批驳回
                                            <#elseif mapitem.STATUS='c'>
                                                审批部分通过
                                            </#if>
                                        <#else >
                                            未审批
                                        </#if></td>
                                        <td></td>
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

        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->