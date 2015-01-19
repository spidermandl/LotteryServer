<script language="JavaScript">
    $("#menu000100").addClass("active");
    $("#menu000100").addClass("open");
    $("#menu000102").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            门店优先级
            <small>
                <i class="icon-double-angle-right"></i>
                门店优先级变更审批
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
                        门店优先级变更申请(申请ID：${approveId})
                    </div>
                    <div class="table-responsive">
                        <form method="post" name="modfiyPriority" action="/priority/approveForm">
                            <input type="hidden" name="approveId" value="${approveId}"/>
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th class="center">
                                        <label>
                                            <input type="checkbox" class="ace" checked/>
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th>门店编号</th>
                                    <th>门店名称</th>
                                    <th>
                                        <small>
                                            <i class="icon-time"></i>
                                            申请时间
                                        </small>
                                    </th>
                                    <th>
                                        <small>原</small>
                                    </th>
                                    <th>
                                        <small>新</small>
                                    </th>
                                    <th>
                                        <small>申请原因</small>
                                    </th>
                                    <th>备注</th>
                                    <th>是否通过</th>
                                    <th>原因</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if approveList??>
                                    <#list approveList as mapitem>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" id="line_id" name="lineid" checked
                                                       value="${mapitem.LINE_ID}"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>

                                        <td>${mapitem.SHOP_CODE}</td>
                                        <td>${mapitem.ATTRIBUTE15}</td>
                                        <td>
                                            <small>${mapitem.CREATION_DATE}</small>
                                        </td>
                                        <td><#if mapitem.PRORITY_OLD??>${mapitem.PRORITY_OLD}</#if></td>
                                        <td>
                                        ${mapitem.PRORITY_NEW}
                                        </td>
                                        <td>
                                        ${mapitem.REASON}
                                        </td>
                                        <td>
                                            <#if mapitem.REMARK??>
                                        ${mapitem.REMARK}
                                            </#if>
                                        </td>
                                        <td>
                                            <select name="approvedIt">
                                                <option value="Y">通过</option>
                                                <option value="N">不通过</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="text" name="reason" value="">
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                            <div class="form-actions center">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    确定
                                    <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                </button>
                            </div>

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