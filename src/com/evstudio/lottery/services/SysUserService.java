package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.TSysUsers;

import java.util.List;

/**
 * Created by ericren on 14-8-6.
 */
public class SysUserService {
    public static SysUserService service = new SysUserService();

    public TSysUsers login(String username, String passwd) {
        TSysUsers user = null;
        List<TSysUsers> list = null;

        list = TSysUsers.dao.find("select * from t_sys_users where loginname=? and passwd=? and valid='1' ", username, passwd );
        if( null != list && list.size() == 1){
            user = list.get(0);
        }

        return user;
    }
}
