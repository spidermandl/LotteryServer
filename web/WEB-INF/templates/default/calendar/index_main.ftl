<script language="JavaScript">
$("#menu000200").addClass("active");
$("#menu000200").addClass("open");
$("#menu000201").addClass("active");
</script>
<style type="text/css">
    .label-default-grey {
        background-color: #abbac3!important;
    }
</style>
<div class="page-content">
    <div class="page-header">
        <h1 class="col-xs-9">
            行事历
            <small>
                <i class="icon-double-angle-right"></i>
                行事历查询
            </small>
        </h1>
    </div>
    <!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
            <#if session.account.ORG_TYPE == '100'>
                <form action="/calendar/feedback" method="post">
                    <input type="hidden" name="svFb" value="${sv}">
                    <input type="hidden" name="svNameFb" value="${svName}">
                    <input type="text" class="col-xs-2" name="comment" value="${comment}">&nbsp;
                    <button type="submit" class="btn btn-sm btn-success">
                        行事历反馈
                    </button>
                </form>
                <form id="svForm" action="/calendar/index" method="post">
                    <#--<div class="row">-->
                        &nbsp;<select class="col-xs-2" id="sv" name="sv">
                        <#if svs??>
                            <#list svs as item>
                                <Option value="${item[0]},${item[1]}" ${item[2]}>${item[1]}</Option>
                            </#list>
                        </#if>
                    </select>
                    <#--</div>-->
                </form>
            <#else>
                <h5>
                    SM行事历反馈：<font color="red">${comment}</font>
                </h5>
            </#if>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="space"></div>

                    <div id="calendar"></div>
                </div>

            </div>

            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</div><!-- /.page-content -->