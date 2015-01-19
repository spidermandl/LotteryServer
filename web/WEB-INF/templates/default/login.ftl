<#include "./common/header.ftl">
<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center">
                        <h1>
                            <i class="icon-leaf green"></i>
                            <span class="red">第一彩票</span>
                            <span class="white">后台管理系统</span>
                        </h1>
                        <h4 class="blue">&copy; evstudio</h4>
                    </div>
                    <div class="space-6"></div>
                    <div class="position-relative">
                        <div id="login-box" class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header blue lighter bigger">
                                        <i class="icon-coffee green"></i>
                                        请输入您的登录信息
                                    </h4>

                                    <div class="space-6"></div>
                                <#if errormsg??><span class="text-danger">${errormsg}</span></#if>
                                    <form action="${ctx_base}/user/login" method="post">
                                        <fieldset>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control"
                                                                   placeholder="用户名" name="loginname"/>
															<i class="icon-user"></i>
														</span>
                                            </label>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control"
                                                                   placeholder="密码" name="passwd"/>
															<i class="icon-lock"></i>
														</span>
                                            </label>

                                            <div class="space"></div>
                                            <div class="clearfix">
                                                <label class="inline">
                                                    <input type="checkbox" class="ace"/>
                                                    <span class="lbl">记住我</span>
                                                </label>
                                                <button type="submit"
                                                        class="width-35 pull-right btn btn-sm btn-primary">
                                                    <i class="icon-key"></i>
                                                    登录
                                                </button>
                                            </div>
                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>
                                </div>
                                <!-- /widget-main -->

                                <div class="toolbar clearfix">
                                    <div>
                                        <a href="#" onclick="show_box('forgot-box'); return false;"
                                           class="forgot-password-link">
                                            <i class="icon-arrow-left"></i>
                                            忘记密码
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <!-- /widget-body -->
                        </div>
                        <!-- /login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger">
                                        <i class="icon-key"></i>
                                        取回密码
                                    </h4>

                                    <div class="space-6"></div>
                                    <p>
                                        输入Email地址
                                    </p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control"
                                                                   placeholder="Email"/>
															<i class="icon-envelope"></i>
														</span>
                                            </label>

                                            <div class="clearfix">
                                                <button type="button"
                                                        class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="icon-lightbulb"></i>
                                                    发送！
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                                <!-- /widget-main -->

                                <div class="toolbar center">
                                    <a href="#" onclick="show_box('login-box'); return false;"
                                       class="back-to-login-link">
                                        返回登录
                                        <i class="icon-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                            <!-- /widget-body -->
                        </div>
                        <!-- /forgot-box -->
                    </div>
                    <!-- /position-relative -->
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
    </div>
</div>
<!-- /.main-container -->
<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
    function show_box(id) {
        jQuery('.widget-box.visible').removeClass('visible');
        jQuery('#' + id).addClass('visible');
    }
</script>
</body>
<#include "./common/footer.ftl">
