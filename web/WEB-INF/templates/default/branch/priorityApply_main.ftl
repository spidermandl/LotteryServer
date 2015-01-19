<div class="page-content">
    <div class="page-header">
        <h1>
            门店优先级修改申请
            <small>
                <i class="icon-double-angle-right"></i>
                described
            </small>
        </h1>
    </div>
    <!-- /.page-header -->

    <div class="row">
        <div class="col-xs-12">
            <!-- PAGE CONTENT BEGINS -->
            <h4 class="pink">
                <i class="icon-edit blue"></i>
                <a href="#modal-table" role="button" class="green" data-toggle="modal"> 添加新的申请 </a>
            </h4>
            <div class="table-header">
                门店优先级列表
            </div>

            <div class="table-responsive">
            <table id="sample-table-2" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th class="center">
                    <label>
                        <input type="checkbox" class="ace"/>
                        <span class="lbl"></span>
                    </label>
                </th>
                <th>门店编号</th>
                <th>门店名称</th>
                <th>
                    <i class="icon-time bigger-110 hidden-480"></i>
                    申请时间
                </th>
                <th class="hidden-480">原始优先级</th>
                <th class="hidden-480">申请变更优先级</th>
                <th>申请原因</th>
                <th>申请状态</th>
                <th></th>
            </tr>
            </thead>

            <tbody>
            <tr>
                <td class="center">
                    <label>
                        <input type="checkbox" class="ace"/>
                        <span class="lbl"></span>
                    </label>
                </td>

                <td>
                    1125000011
                </td>
                <td>上海浦东凌河鞋柜</td>
                <td>2014-08-20 12:00</td>
                <td class="hidden-480">
                    <span class="label label-sm label-danger">1</span>
                </td>
                <td class="hidden-480">
                    <span class="label label-sm label-yellow">2</span>
                </td>
                <td>门口修路</td>
                <td><span class="label label-sm label-info">申请中</span></td>
                <td>
                    <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
                        <a class="blue" href="#">
                            <i class="icon-zoom-in bigger-130"></i>
                        </a>

                        <a class="green" href="#">
                            <i class="icon-pencil bigger-130"></i>
                        </a>

                        <a class="red" href="#">
                            <i class="icon-trash bigger-130"></i>
                        </a>
                    </div>

                    <div class="visible-xs visible-sm hidden-md hidden-lg">
                        <div class="inline position-relative">
                            <button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown">
                                <i class="icon-caret-down icon-only bigger-120"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="icon-zoom-in bigger-120"></i>
																				</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="icon-edit bigger-120"></i>
																				</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="icon-trash bigger-120"></i>
																				</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
            </table>
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
                            门店优先级列表
                        </div>
                    </div>

                    <div class="modal-body no-padding">
                        <#--<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">-->
                            <#--<thead>-->
                            <#--<tr>-->
                                <#--<th>门店编号</th>-->
                                <#--<th>门店名称</th>-->
                                <#--<th>当前优先级</th>-->
                            <#--</tr>-->
                            <#--</thead>-->
                            <#--<tbody>-->
                            <#--<tr>-->
                                <#--<td>-->
                                    <#--1125000047-->
                                <#--</td>-->
                                <#--<td>上海光新乐购鞋柜</td>-->
                                <#--<td>2014-08-22 12:00</td>-->
                                <#--<td><span class="label label-sm label-yellow">2</span></td>-->
                            <#--</tr>-->
                            <#--</tbody>-->
                        <#--</table>-->
                    </div>

                    <div class="modal-footer no-margin-top">
                        <button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
                            <i class="icon-remove"></i>
                            取消
                        </button>

                        <button class="btn btn-sm btn-success pull-right" data-dismiss="modal">
                            <i class="icon-ok"></i>
                            确定
                        </button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
    </div>
</div>