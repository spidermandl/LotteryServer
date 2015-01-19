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
                            <h4>店名：<#if shopName??>${shopName}</#if></h4>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main no-padding">
                            <#if record??>
                                <#if record.CHECKOUT_TIME??>
                                <form id="signForm" action="/sign/signIn" method="post">
                                <#else>
                                <form id="signForm" action="/sign/signOut" method="post">
                                    <input type="hidden" name="recordid" value="${record.ID}"/>
                                </#if>
                            <#else>
                            <form id="signForm" action="/sign/signIn" method="post">
                            </#if>
                                <input type="hidden" name="shopCode" id="shopcode"
                                       <#if shopCode??>value="${shopCode}"</#if>/>
                                <input type="hidden" name="shopName" id="shopname"
                                       <#if shopName??>value="${shopName}"</#if>/>
                                <input type="hidden" name="lati" id="lati"/>
                                <input type="hidden" name="longi" id="longi"/>
                                <input type="hidden" name="addr" id="addr" />
                            <#--<input type="hidden" name="address" id="address"/>-->
                                <div class="form-actions center">
                                <#if record??>
                                    <#if record.CHECKOUT_TIME??>
                                        <button id="signin" type="button" class="btn btn-sm btn-info">
                                            签到
                                            <i class="icon-signin icon-on-right bigger-110"></i>
                                        </button>
                                    <#else>
                                        <button id="signout" type="button" class="btn btn-sm btn-info">
                                            签退
                                            <i class="icon-signout icon-on-right bigger-110"></i>
                                        </button>
                                    </#if>
                                <#else>
                                    <button id="signin" type="button" class="btn btn-sm btn-info">
                                        签到
                                        <i class="icon-signin icon-on-right bigger-110"></i>
                                    </button>
                                </#if>
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
                        今日签到签退详情
                    </div>
                    <div class="table-responsive">
                        <table id="prioritylist" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    门店名称
                                </th>
                                <th>
                                    签到时间
                                </th>
                                <th>
                                    签到地址
                                </th>
                                <th>
                                    签退时间
                                </th>
                                <th>
                                    签退地址
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <#if record??>
                            <tr>
                                <td>
                                    <#if record.SHOP_NAME??>${record.SHOP_NAME}</#if>
                                </td>
                                <td>
                                    <#if record.CHECKIN_TIME??>${record.CHECKIN_TIME}</#if>
                                </td>
                                <td>
                                    <#if record.SIGN_ADDR??>${record.SIGN_ADDR}</#if>
                                </td>
                                <td>
                                    <#if record.CHECKOUT_TIME??>${record.CHECKOUT_TIME}</#if>
                                </td>
                                <td>
                                    <#if record.SIGN_OUT_ADDR??>${record.SIGN_OUT_ADDR}</#if>
                                </td>
                            </tr>
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