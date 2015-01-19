package com.evstudio.lottery.controller;

import com.evstudio.lottery.pojos.TClientUsers;
import com.evstudio.lottery.services.ClientUserService;
import com.google.gson.Gson;
import com.jfinal.core.Controller;

import java.util.HashMap;

/**
 * Created by eric on 15/1/12.
 */
public class ClientUserController extends Controller {
    public void index() {

    }

    public void userInfo() {
        String mobile = getPara("mobile");
        String uid = getPara("uid");

        HashMap<String, String> hashMap = new HashMap<String, String>();

        TClientUsers user =
                ClientUserService.service.getUserByUserid(uid);
        if (null != user) {
            hashMap.put("userId", user.getStr("userid"));
            hashMap.put("userName", user.getStr("chsname") == null ? "" : user.getStr("chsname"));
            hashMap.put("mobile", user.getStr("mobile") == null ? "" : user.getStr("mobile"));
            hashMap.put("email", user.getStr("email") == null ? "" : user.getStr("email"));
            hashMap.put("idCard", user.getStr("idcard") == null ? "" : user.getStr("idcard"));
            hashMap.put("bankType", user.getStr("bank") == null ? "" : user.getStr("bank"));
            hashMap.put("bankCode", user.getStr("bankcard") == null ? "" : user.getStr("bankcard"));
            hashMap.put("result", "");
            hashMap.put("resultCode", "0");
        } else {
            hashMap.put("result", "用户不存在!");
            hashMap.put("resultCode", "9101");
        }
        Gson gson = new Gson();
        System.out.println(gson.toJson(hashMap));
        renderJson(gson.toJson(hashMap));
    }

    public void modifyPassword() {
        String userid = getPara("userId");
        String oldPwd = getPara("oldPassword");
        String newPwd = getPara("newPassword");
        HashMap<String, String> hashMap = new HashMap<String, String>();

        TClientUsers user =
                ClientUserService.service.getUserByUserid(userid);
        if (null != user) {
            if (user.getStr("passwd").equals(oldPwd)) {
                user.set("passwd", newPwd);
                user.update();
                hashMap.put("result", "success");
                hashMap.put("resultCode", "0");
                hashMap.put("uid", userid);
            } else {
                hashMap.put("result", "用户密码不正确！");
                hashMap.put("resultCode", "9102");
            }
        } else {
            hashMap.put("result", "用户不存在!");
            hashMap.put("resultCode", "9101");
        }


        Gson gson = new Gson();
        System.out.println(gson.toJson(hashMap));
        renderJson(gson.toJson(hashMap));
    }

    public void saveUser() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String userid = getPara("userId");
        String userName = getPara("userName");
        String mobile = getPara("mobile");
        String idCard = getPara("idCard");
        String bankType = getPara("bankType");
        String bankCode = getPara("bankCode");
        String email = getPara("email");

        TClientUsers user =
                ClientUserService.service.getUserByUserid(userid); //ClientUserService.service.getUserByMobile(mobile);
        if (null != user) {
            user.set("chsname", userName);
            user.set("mobile", mobile);
            user.set("idcard", idCard);
            user.set("bank", bankType);
            user.set("bankcard", bankCode);
            user.set("email", email);
            user.update();

            hashMap.put("result", "success");
            hashMap.put("resultCode", "0");
            hashMap.put("uid", userid);
        } else {
            hashMap.put("result", "用户不存在!");
            hashMap.put("resultCode", "9101");
        }

        Gson gson = new Gson();
        System.out.println(gson.toJson(hashMap));
        renderJson(gson.toJson(hashMap));
    }
}
