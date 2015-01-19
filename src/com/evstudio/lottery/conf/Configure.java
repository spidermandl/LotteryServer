package com.evstudio.lottery.conf;

import com.evstudio.lottery.controller.*;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.thread.Sh11x5Thread;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * Created by ericren on 14-6-25.
 */
public class Configure extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        loadPropertyFile("a_little_config.txt");                // 加载少量必要配置，随后可用getProperty(...)获取值
        me.setDevMode(getPropertyToBoolean("devMode", true));
        me.setEncoding("utf-8");
        me.setViewType(ViewType.FREE_MARKER);                            // 设置视图类型为Jsp，否则默认为FreeMarker
//        FreeMarkerRender.getConfiguration().setClassicCompatible( true );
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", DefaultController.class);
        me.add("/user", SysUserController.class);
        me.add("/sh11x5", Sh11x5Controller.class);
        me.add("/mobile", MobileController.class);
        me.add("/v", VerifyController.class);
        me.add("/nr", QijiController.class);
        me.add("/pay",PaymentController.class);
        me.add("/clientUser",ClientUserController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        me.add(c3p0Plugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("syydj", Syydj.class);    // 映射表到模型
        arp.addMapping("sh11x5", Sh11x5.class);
        arp.addMapping("dictsyxw1", DictSyxw1.class);
        arp.addMapping("t_sys_users", TSysUsers.class);
        arp.addMapping("t_client_users", TClientUsers.class);
        arp.addMapping("t_user_capital_account", TUserCapitalAccount.class);
        arp.addMapping("appversion", Appversion.class);
        arp.addMapping("t_user_start_app_info", TUserStartAppInfo.class);
        arp.addMapping("t_online_users", TOlineUsers.class);
        arp.addMapping("t_user_betlist", TUserBetlist.class);
        arp.addMapping("t_user_betlist_jczq_head", TUserBetlistJczqHead.class);
        arp.addMapping("t_user_betlist_jczq_line", TUserBetlistJczqLine.class);
        arp.addMapping("t_user_betlist_dlt", TUserBetlistDlt.class);
        arp.addMapping("t_user_betlist_sfc14n9", TUserBetlistSfc14n9.class);

        arp.addMapping("t_qiji_nightride_user_filepath", TQijiNightrideUserFilepath.class );
        arp.addMapping("t_qiji_nightride_user_filepath", TQijiNightrideUserFilepath.class );
        arp.addMapping("t_qiji_nightride_users", TQijiNightrideUsers.class );
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new SessionInViewInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
//        System.out.println( " new ContextPathHandler(\"ctx_base\") =" +  new ContextPathHandler("ctx_base") );
        me.add(new ContextPathHandler("ctx_base"));
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();

        Sh11x5Thread.thread.run();
//        try {
////            System.out.println( "JFinal.me().getContextPath()=" + JFinal.me().getContextPath());
//            FreeMarkerRender.getConfiguration().setSharedVariable("ctx_base", JFinal.me().getContextPath());
//        } catch (TemplateModelException e) {
//            throw new RuntimeException(e);
//        }

    }

    /**
     * 建议使用 JFinal 手册推荐的方式启动项目
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     */
    public static void main(String[] args) {
        JFinal.start("web", 8090, "/", 5);
    }
}
