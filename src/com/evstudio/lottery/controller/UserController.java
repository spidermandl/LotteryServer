package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.MD5Util;
import com.evstudio.lottery.pojos.CuxSvOrgMapping;
import com.evstudio.lottery.pojos.FndPrvLoginAccount;
import com.evstudio.lottery.pojos.ScmOrgRelation;
import com.evstudio.lottery.services.UserServices;
import com.jfinal.core.Controller;

import java.sql.Timestamp;

/**
 * Created by ericren on 14-8-18.
 */
public class UserController extends Controller {
    public void index() {
        render("");
    }

    /**
     * 用户登录
     */
    public void login() {
        String loginname = getPara("loginname");
        String passwd = getPara("passwd");
        if (null != loginname && passwd != null) {
            FndPrvLoginAccount account = UserServices.services.login(loginname, passwd);
            if (null != account) {
                setSessionAttr("account", account);

                ScmOrgRelation orgRelation = UserServices.services.getOrgByUser(account);
                if (null == orgRelation) {
                    orgRelation = new ScmOrgRelation();
                }
                setSessionAttr("org", orgRelation);

                CuxSvOrgMapping cuxSvOrgMapping = UserServices.services.getOrgMappingByUser(account);
                if (null == cuxSvOrgMapping) {
                    cuxSvOrgMapping = new CuxSvOrgMapping();
                }
                setSessionAttr("orgmapping", cuxSvOrgMapping);

                render("/WEB-INF/templates/default/index/index.ftl");
                return;
            }
        }

        setAttr("errormsg", "用户名密码输入不正确，请重新输入！");
        render("/WEB-INF/templates/default/login.ftl");
    }

    public void submitChangePasswd(){
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        Timestamp tsNow = new Timestamp(System.currentTimeMillis());
        String newPass = getPara("newpasswd");
        account.set("LOGIN_PWD", MD5Util.MD5(newPass));
        account.set("PWD_UPDATE_DATE", tsNow);
        boolean isSuccess= account.update();

        if (isSuccess) {
            setAttr("tip","密码修改成功");
        } else {
            setAttr("tip","密码修改失败");
        }

        render("/WEB-INF/templates/default/user/change_passwd.ftl");
    }

    public void changePasswd(){
        render("/WEB-INF/templates/default/user/change_passwd.ftl");
    }

    /**
     * 用户退出
     */
    public void logout() {
        getSession().invalidate();
        redirect("/");
    }
}
