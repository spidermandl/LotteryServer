package com.evstudio.lottery.services;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.TClientUsers;
import com.evstudio.lottery.pojos.TOlineUsers;
import com.evstudio.lottery.pojos.TUserBetlist;
import com.evstudio.lottery.pojos.TUserCapitalAccount;

import java.util.Date;
import java.util.List;

/**
 * Created by ericren on 14-9-11.
 */
public class ClientUserService {
    public static ClientUserService service = new ClientUserService();

    public TClientUsers login(String mobile, String passwd) {
        TClientUsers user = null;
        List<TClientUsers> list = null;

        list = TClientUsers.dao.find("select * from t_client_users where loginname=? and passwd=? and valid='1' ", mobile, passwd);
        if (null != list && list.size() == 1) {
            user = list.get(0);
        }

        return user;
    }

    public TClientUsers getUserByMobile(String mobile) {
        TClientUsers user = null;

        user = TClientUsers.dao.findFirst("select * from t_client_users where mobile = '" + mobile + "' and valid = '1'");

        return user;
    }

    public TClientUsers getUserByUserid(String userid) {
        TClientUsers user = null;

        user = TClientUsers.dao.findFirst("select * from t_client_users where userid = '" + userid + "' and valid = '1'");

        return user;
    }

    public TClientUsers newUser(TClientUsers user) {
        TClientUsers newone = user;

        newone.set("userid", Util.getNewIdByType(1));
        newone.set("logintimes", 0);
        newone.set("createtime", new Date());
        newone.set("updatetime", new Date());
        newone.set("valid", "1");

        newone.save();

        return newone;
    }

    public void setOline(TClientUsers user) {
        TOlineUsers onlineuser = new TOlineUsers();
        onlineuser.set("userid", user.getStr("userid"));
        onlineuser.set("updatetime", new Date());
        onlineuser.set("logintime", new Date());
        onlineuser.set("state", "1");
        onlineuser.save();
    }

    public boolean checkOnline(TClientUsers user) {
        boolean bOline = false;

        TOlineUsers onlineuser = null;
        onlineuser = TOlineUsers.dao.findFirst("select * from t_online_users where userid = ? and TIMESTAMPDIFF(MINUTE, now(), updatetime ) < 10 and state = '1' order by update desc");
        if (null == onlineuser) {

        } else {
            onlineuser.set("updatetime", new Date());
            onlineuser.save();
            bOline = true;
        }

        return bOline;
    }

    public List<TUserBetlist> getUserBetList(String userid, String querytype) {
        String sql = "select * from t_user_betlist where userid = '" + userid + "'";
        if (null != querytype && !"0".equals(querytype)) {
            sql = sql + " and betstatus = '" + querytype + "'";
        }
        sql += " order by id desc";

        return TUserBetlist.dao.find(sql);
    }

    public TUserCapitalAccount getAccountByUserIdNType(String userid, String type) {
        TUserCapitalAccount account = null;
        String sql = "select * from t_user_capital_account where userid = ? and accounttype = ?";
        account = TUserCapitalAccount.dao.findFirst(sql, userid, type);
        if (account == null) {
            account = new TUserCapitalAccount();
            account.set("userid", userid);
            account.set("accounttype", type);
            account.set("balance", "20");
            account.set("createtime", new Date());
            account.set("modifytime", new Date());
            account.set("valid", "1");
            account.save();
        }
        return account;
    }
}
