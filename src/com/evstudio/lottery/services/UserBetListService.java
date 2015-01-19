package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.TClientUsers;
import com.evstudio.lottery.pojos.TUserBetlist;

import java.util.Date;
import java.util.List;

/**
 * Created by ericren on 14-9-11.
 */
public class UserBetListService {
    public static UserBetListService service = new UserBetListService();

    public void saveBet( TClientUsers user, TUserBetlist betlist ){
        betlist.set("userid",user.getStr("userid"));
        betlist.set("bettime", new Date() );
        betlist.set("createtime", new Date() );
        if( "51".equals(betlist.get("periods")))
            betlist.set("valid",3);
        else
            betlist.set("valid", "1");


        betlist.save();
    }

    public List<TUserBetlist> getListByPeriods( String periods ){
        List<TUserBetlist> list = null;

        list = TUserBetlist.dao.find("select * from t_user_betlist where periods = ?", periods );

        return list;
    }
}
