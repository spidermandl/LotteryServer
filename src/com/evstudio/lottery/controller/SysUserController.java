package com.evstudio.lottery.controller;

import com.evstudio.lottery.pojos.Sh11x5;
import com.evstudio.lottery.pojos.TSysUsers;
import com.evstudio.lottery.services.Sh11x5Service;
import com.evstudio.lottery.services.SysUserService;
import com.jfinal.core.Controller;

import java.util.List;

/**
 * Created by ericren on 14-8-6.
 */
public class SysUserController extends Controller {
    public void login(){
        String strNav = "";

        TSysUsers user = null;

        String loginname = getPara("loginname");
        String passwd = getPara("passwd");

        user = SysUserService.service.login( loginname,  passwd );
        if( null == user ){
            setAttr("errormsg","用户名密码不正确，请重新输入！");
            render("/WEB-INF/templates/default/login.ftl");
            return;
        }

        setSessionAttr("user", user);
        setAttr("user", user);

        setAttr("sysNav", strNav );
        render("/WEB-INF/templates/default/index/index.ftl");
    }
}
