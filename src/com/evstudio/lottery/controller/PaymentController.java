package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.TClientUsers;
import com.evstudio.lottery.pojos.TUserCapitalAccount;
import com.evstudio.lottery.services.ClientUserService;
import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

import java.util.HashMap;

/**
 * Created by eric on 15/1/12.
 */
public class PaymentController extends Controller {
    private Logger logger = Logger.getLogger(PaymentController.class);
    public void index(){}

    public void aliPayOrder(){
        String uid = getPara("userid");
        String orderPrice = getPara( "price" );
        String goodName = getPara( "goodName" );

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("result", "success");
        hashMap.put("resultCode", "0");
        hashMap.put("orderId", Util.getNewIdByType(101));
        hashMap.put("uid", uid);

        Gson gson = new Gson();
        System.out.println(gson.toJson(hashMap));
        renderJson(gson.toJson(hashMap));
    }

    public void aliPay(){
        Gson gson = new Gson();
        System.out.println( gson.toJson( getParaMap() ) );
    }


    public void queryPayResult(){
        String uid = getPara("userid");
        String orderId = getPara("orderid");

        Gson gson = new Gson();
        System.out.println( gson.toJson( getParaMap() ) );

        TClientUsers user =
                ClientUserService.service.getUserByUserid(uid); //ClientUserService.service.getUserByMobile(mobile);
        ClientUserService.service.setOline(user);

        TUserCapitalAccount accountCash = ClientUserService.service.getAccountByUserIdNType(user.getStr("userid"), "1");
        TUserCapitalAccount accountAward = ClientUserService.service.getAccountByUserIdNType(user.getStr("userid"), "2");

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("result", "success");
        hashMap.put("resultCode", "0");
        hashMap.put("orderId", Util.getNewIdByType(101));
        hashMap.put("uid", uid);
        hashMap.put("cash", accountCash.getStr("balance"));
        hashMap.put("award", accountAward.getStr("balance"));

        System.out.println(gson.toJson(hashMap));
        renderJson(gson.toJson(hashMap));
    }
}
