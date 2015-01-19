<div class="page-content">
    <div class="page-header">
        <h1>
            修改密码
        </h1>
    </div>
    <!-- /.page-header -->
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box">
                <div class="widget-header">
                    <h4>请输入新密码</h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<#if tip??><font color="red">${tip}</font></#if>
                </div>

                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form action="/user/submitChangePasswd" method="post">
                            <table class="table table-stript table-bordered">
                                <tr>
                                    <td class="align-right col-xs-3">
                                        新密码：
                                    </td>
                                    <td class="col-xs-8">
                                        <div class="col-xs-8">
                                            <input class="col-xs-4" type="password" id="newpasswd" name="newpasswd"/>
                                            &nbsp;&nbsp;&nbsp;<font color="red"><span id="tipa"></span></font>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="align-right col-xs-3">
                                        确认新密码：
                                    </td>
                                    <td class="col-xs-8">
                                        <div class="col-xs-8">
                                            <input class="col-xs-4" type="password" id="confirm" name="confirm"/>
                                            &nbsp;&nbsp;&nbsp;<font color="red"><span id="tipb"></span></font>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <div class="form-actions center">
                                <button id="btnSubmit" type="submit" class="btn btn-sm btn-success" disabled>
                                    提交
                                    <i class="icon-arrow-right icon-on-right bigger-110"></i>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- /.row -->
</div>