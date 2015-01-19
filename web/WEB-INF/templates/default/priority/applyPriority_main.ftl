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
                门店优先级优先级修改申请
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
                            <h4>门店优先级修改申请，申请单号：<#if head_id??>${head_id}</#if></h4>
                        </div>

                        <div class="widget-body">
                           <div class="widget-header">
                            <h5><font color="red"><#if xsl??>行事历账期已安排，不能修改优先级<#else>共选择了&nbsp;${priorityListSelected?size}&nbsp;家店铺，其中&nbsp;${priorityListSelected?size-priorityList?size}&nbsp;家已提交过申请</#if></font></h5>
                        </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="table-header">
                        门店优先级修改申请(会计月：<#if applyMonth??>${applyMonth}</#if>)
                    </div>
                    <div class="table-responsive">
                        <form method="post" name="modfiyPriority" action="/priority/submitApply">
                            <input type="hidden" name="head_id" value="<#if head_id??>${head_id}</#if>">
                            <input type="hidden" name="applyMonth" value="<#if applyMonth??>${applyMonth}</#if>">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
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
                                    <th>原优先级</th>
                                    <th>新优先级</th>
                                    <th>申请原因</th>
                                    <th>备注</th>
                                </tr>
                                </thead>

                                <tbody>
                                <#if priorityList??>
                                    <#list priorityList as mapitem>
                                    <tr>
                                        <td><input type="hidden" name="cbShop"
                                                   value="${mapitem.SHOP_CODE}"/>
                                        ${mapitem.BRAND}</td>
                                        <td><input type="hidden" name="shopName"
                                                   value="${mapitem.SHOP_NAME}"/>
                                            <small>${mapitem.SHOP_NAME}</small>
                                        </td>
                                        <td>${mapitem.PERIOD}</td>

                                        <#if mapitem.PRORITY_ORDER??>
                                            <input type="hidden" name="oldPriority" value="${mapitem.PRORITY_ORDER}" />
                                            <#switch mapitem.PRORITY_ORDER?number>
                                                <#case 1>
                                                    <td>
                                                        <span class="label label-sm label-danger">1</span>
                                                    </td>
                                                    <td>
                                                        <select name="newpriority" id="id_newpriority_${mapitem_index}">
                                                            <Option value="2">2</Option>
                                                            <Option value="3">3</Option>
                                                            <Option value="4">4</Option>
                                                        </select>
                                                    </td>
                                                    <#break>
                                                <#case 2>
                                                    <td>
                                                        <span class="label label-sm label-yellow">2</span>
                                                    </td>
                                                    <td>
                                                        <select name="newpriority" id="id_newpriority_${mapitem_index}">
                                                            <Option value="1">1</Option>
                                                            <Option value="3">3</Option>
                                                            <Option value="4">4</Option>
                                                        </select>
                                                    </td>
                                                    <#break>
                                                <#case 3>
                                                    <td>
                                                        <span class="label label-sm label-warning">3</span>
                                                    </td>
                                                    <td>
                                                        <select name="newpriority" id="id_newpriority_${mapitem_index}">
                                                            <Option value="1">1</Option>
                                                            <Option value="2">2</Option>
                                                            <Option value="4">4</Option>
                                                        </select>
                                                    </td>
                                                    <#break>
                                                <#case 4>
                                                    <td>
                                                        <span class="label label-sm label-success">4</span>
                                                    </td>
                                                    <td>
                                                        <select name="newpriority" id="id_newpriority_${mapitem_index}">
                                                            <Option value="1">1</Option>
                                                            <Option value="2">2</Option>
                                                            <Option value="3">3</Option>
                                                        </select>
                                                    </td>
                                                    <#break>
                                            </#switch>

                                        <#else>
                                            <td></td>
                                            <td>
                                                <select name="newpriority" id="id_newpriority_${mapitem_index}">
                                                    <Option value="1">1</Option>
                                                    <Option value="2">2</Option>
                                                    <Option value="3">3</Option>
                                                    <Option value="4">4</Option>
                                                </select>
                                            </td>
                                        </#if>
                                        <td>
                                            <select name="reason" id="id_reason_${mapitem_index}">
                                                <Option value="商场重新装修">商场重新装修</Option>
                                                <Option value="门口修路">门口修路</Option>
                                                <Option value="周边居民搬迁">周边居民搬迁</Option>
                                                <Option value="客户投诉">客户投诉</Option>
                                                <Option value="其他">其他</Option>
                                            </select>
                                        </td>
                                        <td>
                                            <input name="comments" type="text" id="id_comments_${mapitem_index}">
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                            <div class="form-actions center">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    提交申请
                                    <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                </button></div>
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