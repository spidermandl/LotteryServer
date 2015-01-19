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
                申请单查询
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
                                <form action="/priority/applylist" method="post" name="">
                                    <table class="table table-stript table-bordered">
                                    <#if session.account.ORG_TYPE == '100'>
                                        <tr>
                                            <td></td><td></td>
                                            <td class="align-right col-xs-1">
                                                SV
                                            </td>
                                            <td class="col-xs-6">
                                                <div class="col-xs-6">
                                                    <select class="form-control chosen-select" id="svs" name="svorgname">
                                                        <Option value="">&nbsp;&nbsp;</Option>
                                                        <#if svs??>
                                                            <#list svs as item>
                                                                <Option value="${item[0]}" ${item[2]}>${item[1]}</Option>
                                                            </#list>
                                                        </#if>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>
                                    </#if>
                                        <tr>
                                            <td class="align-right col-xs-2">
                                                账期月
                                            </td>
                                            <td class="col-xs-4">
                                                <select class="col-xs-4 chosen-select" id="months" name="month">
                                                    <Option value="">&nbsp;</Option>
                                                <#if months??>
                                                    <#list months as item>
                                                        <Option value="${item.ATTRIBUTE3}" <#if month??><#if month==item.ATTRIBUTE3>selected="selected"</#if></#if>>${item.ATTRIBUTE3}</Option>
                                                    </#list>
                                                </#if>
                                                </select>
                                            </td>
                                            <td class="align-right col-xs-2">
                                                店铺
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
                    <div class="table-header">
                        门店优先级变更申请列表
                    </div>
                    <div class="table-responsive">
                        <form method="post" name="modfiyPriority" action="/priority/batchApprove">
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
                                        编号
                                    </th>
                                    <th>
                                        账期
                                    </th>
                                    <th>
                                        申请人
                                    </th>
                                    <th>
                                        门店名称
                                    </th>
                                    <th>
                                        <i class="icon-time bigger-110 hidden-480"></i>
                                        <small>申请时间</small>
                                    </th>
                                    <th>
                                        原
                                    </th>
                                    <th>
                                        新
                                    </th>
                                    <th>
                                        申请原因
                                    </th>
                                    <th>
                                        备注
                                    </th>
                                    <th>
                                        <small>申请状态</small>
                                    </th>
                                    <th style="white-space: nowrap"></th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if applyList??>
                                    <#list applyList as mapitem>
                                    <tr>
                                        <td class="center">
                                        	<#if mapitem.ATTRIBUTE1??>
                                                <label>
                                                    <span class="lbl"></span>
                                                </label>
                                        	<#else>
                                            <label>
                                                <input type="checkbox" class="ace" id="head_id" name="lineid"
                                                       value="${mapitem.LINE_ID}"/>
                                                <span class="lbl"></span>
                                            </label>
                                            </#if>
                                        </td>
                                        <td>${mapitem.LINE_ID}</td>
                                        <td><#if mapitem.ATTRIBUTE13??>${mapitem.ATTRIBUTE13}</#if></td>
                                        <td><#if mapitem.ATTRIBUTE15??>${mapitem.ATTRIBUTE15}</#if></td>
                                        <td><#if mapitem.ATTRIBUTE14??>${mapitem.ATTRIBUTE14}</#if></td>
                                        <td>
                                            <small><#if mapitem.CREATION_DATE??>${mapitem.CREATION_DATE}</#if></small>
                                        </td>
                                        <td><#if mapitem.PRORITY_OLD??>${mapitem.PRORITY_OLD}</#if></td>
                                        <td><#if mapitem.PRORITY_NEW??>${mapitem.PRORITY_NEW}</#if></td>
                                        <td><#if mapitem.REASON??>${mapitem.REASON}</#if></td>
                                        <td><#if mapitem.REMARK??>${mapitem.REMARK}</#if></td>
                                        <td><#if mapitem.ATTRIBUTE1??>
                                            <#if mapitem.ATTRIBUTE1='Y'>
                                                审批通过
                                            <#elseif mapitem.ATTRIBUTE1='N'>
                                                审批驳回
                                            </#if>
                                        <#else >
                                            未审批
                                        </#if></td>
                                        <td class="center" style="white-space: nowrap">
                                            <#if mapitem.ATTRIBUTE1??>
	                                            <#if mapitem.ATTRIBUTE1='Y'>
	                                            <#elseif mapitem.ATTRIBUTE1='N'>
	                                                <#if mapitem.ATTRIBUTE2??>${mapitem.ATTRIBUTE2}</#if>
	                                            </#if>
	                                        <#else >
	                                        	<#if session.account.ORG_TYPE == '100'>
		                                        	<input type="button" onclick="agree(${mapitem.LINE_ID})" class="btn btn-minier btn-success" value="通过">
	                              				    </input>&nbsp;
	                              				    <input type="button" onclick="refuse(${mapitem.LINE_ID})" class="btn btn-minier btn-danger" value="驳回">
	                              				    </input>
	                                        	</#if>
	                                        </#if>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                                
                            </table>
                            <#if session.account.ORG_TYPE == '100'>
                            <div class="col-xs-2">
                                <button type="submit" class="btn btn-sm btn-danger">
                                    批量通过
                                    <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                </button></div>
                            </#if>
                        </form>
                    </div>
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
                            优先级修改申请(单号：<span id="refuselid"></span>)
                        </div>
                    </div>

                    <div class="modal-body no-padding ">
                        &nbsp;<br/>
                        &nbsp;&nbsp;驳回原因：<input type="text" id="refuseReason"/>&nbsp;<font color="red"><span id="tip"></span></font><br/>&nbsp;

                    <div class="modal-footer no-margin-top">
                        
                        <button id="btnSubmit" class="btn btn-sm btn-success pull-right"
                                >
                            <i class="icon-check"></i>
                            提交
                        </button>
                        <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal"
                                >
                            <i class="icon-remove"></i>
                            返回
                        </button>
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
<form name="approveForm" method="post" action="/priority/approve">
    <input type="hidden" name="formApproveId"/>
</form>