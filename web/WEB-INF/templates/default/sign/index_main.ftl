<script language="JavaScript">
    $("#menu000400").addClass("active");
    $("#menu000400").addClass("open");
    $("#menu000401").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            巡店
            <small>
                <i class="icon-double-angle-right"></i>
                签到签退
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
                            <h4>选择店铺</h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                                <form action="/sign/doSign" method="post">
                                    <table class="table table-stript table-bordered">
                                        <tr>
                                            <td class="align-right col-xs-2">
                                                店铺代码
                                            </td>
                                            <td class="col-xs-4">
                                                <div class="col-xs-4">
                                                    <select class="chosen-select" id="shops" name="shopCode">
                                                    <#if shops??>
                                                        <#list shops as item>
                                                            <Option value="${item[0]},${item[1]}" ${item[2]}>${item[0]}
                                                                ,${item[1]}</Option>
                                                        </#list>
                                                    </#if>
                                                    </select>
                                                </div>
                                            </td>
                                            <#--<td class="align-right col-xs-2">-->
                                                <#--查询会计月-->
                                            <#--</td>-->
                                            <#--<td class="col-xs-4">-->
                                                <#--<select class="form-control col-xs-4" id="months" name="searchmonth">-->
                                                    <#--<Option value=""></Option>-->
                                                <#--<#if months??>-->
                                                    <#--<#list months as item>-->
                                                        <#--<Option value="${item[0]}" ${item[2]}>${item[1]}</Option>-->
                                                    <#--</#list>-->
                                                <#--</#if>-->
                                                <#--</select>-->
                                            <#--</td>-->
                                        </tr>
                                    </table>
                                    <div class="form-actions center">
                                        <button type="submit" class="btn btn-sm btn-success">
                                            确定
                                            <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->