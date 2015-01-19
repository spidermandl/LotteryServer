<script language="JavaScript">
    $("#menu000300").addClass("active");
</script>
<div class="page-content">
    <div class="page-header">
        <h1>
            店铺陈列
            <small>
                <i class="icon-double-angle-right"></i>
                店铺陈列下载列表
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
                        店铺陈列下载列表
                    </div>
                    <div class="table-responsive">
                            <table id="prioritylist" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        销售类型
                                    </th>
                                    <th>
                                        文件名称
                                    </th>
                                    <th>更新时间</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        联销
                                    </td>
                                    <td>
                                        联销陈列.doc
                                    </td>
                                    <td>2014-11-02 19:48:31</td>
                                    <td>
                                        <button class="btn btn-sm btn-info"
                                                onclick='$("#fileDownFrame").attr("src","/func/download?filename=20141011092120.doc");'>
                                            <i class="icon-download"></i>
                                            下载
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        专卖
                                    </td>
                                    <td>
                                        专卖陈列.doc
                                    </td>
                                    <td>2014-11-01 11:12:03</td>
                                    <td><button class="btn btn-sm btn-info"
                                                onclick='$("#fileDownFrame").attr("src","/func/download?filename=20141030140701.doc");'>
                                        <i class="icon-download"></i>
                                        下载
                                    </button></td>
                                </tr>
                                </tbody>
                            </table>
                    </div>
                </div>
            </div>
            <iframe id="fileDownFrame" src="" style="display:none; visibility:hidden;"></iframe>
            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->