package com.evstudio.lottery.controller;

import com.evstudio.lottery.pojos.Sh11x5;
import com.evstudio.lottery.pojos.TSysUsers;
import com.evstudio.lottery.services.Sh11x5Service;
import com.evstudio.lottery.services.SysNavService;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericren on 14-8-6.
 */
public class Sh11x5Controller extends Controller {
    public void index() {
        List<Sh11x5> listSh11x5 = null;
        List<String[]> listPrecent = null;
        int[] intCount = null;

        ArrayList<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        String[] strNav = {"11选5", "上海11选5"};

        TSysUsers user = getSessionAttr("user");
        if (null != user) {
            setAttr("user", user);
        }

        listSh11x5 = Sh11x5Service.service.getTodayList();

        listPrecent = Sh11x5Service.service.getPercentOfWinning(listSh11x5);
        for (int i = 0; i < listSh11x5.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("1", listSh11x5.get(i));
            map.put("2", listPrecent.get(i));
            mapList.add(map);
        }

        intCount = Sh11x5Service.service.getCountByList(listSh11x5);

        setAttr("sh11x5", mapList);
        setAttr("iCount", intCount);

        setAttr("sysNav", SysNavService.service.generateNavHtml(strNav));
        render("/WEB-INF/templates/default/syxw/sh11x5.ftl");
    }

    public void history() {
        render("");
    }

    public void als1() {
        String strSh11x5 = getPara("sh11x5");
        List<Sh11x5> list = null;
        int[] ints = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        if (null == strSh11x5) {
            renderText("Error Parameter!!");
            return;
        }

        String[] items = strSh11x5.split(",");
        list = Sh11x5Service.service.getFirstNumberList(items[0]);
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                Sh11x5 sh11x5 = list.get(i);
                sh11x5 = Sh11x5.dao.getNext(sh11x5);
                if (null != sh11x5)
                    ints[sh11x5.getInt("winningnumber1") - 1]++;
//                ints[sh11x5.getInt("winningnumber2") - 1]++;
//                ints[sh11x5.getInt("winningnumber3") - 1]++;
//                ints[sh11x5.getInt("winningnumber4") - 1]++;
//                ints[sh11x5.getInt("winningnumber5") - 1]++;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ints.length; i++) {
            stringBuffer.append(String.valueOf(ints[i]));
            stringBuffer.append(",");
        }

        renderText(stringBuffer.toString());
    }

    public void als2() {
        String strSh11x5 = getPara("sh11x5");
        List<Sh11x5> list = null;
        int[] ints = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        if (null == strSh11x5) {
            renderText("Error Parameter!!");
            return;
        }

        String[] items = strSh11x5.split(",");
        list = Sh11x5Service.service.getLastNumberList(items[0]);
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                Sh11x5 sh11x5 = list.get(i);
                sh11x5 = Sh11x5.dao.getNext(sh11x5);
                if (null != sh11x5)
                    ints[sh11x5.getInt("winningnumber1") - 1]++;
//                ints[sh11x5.getInt("winningnumber2") - 1]++;
//                ints[sh11x5.getInt("winningnumber3") - 1]++;
//                ints[sh11x5.getInt("winningnumber4") - 1]++;
//                ints[sh11x5.getInt("winningnumber5") - 1]++;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < ints.length; i++) {
            stringBuffer.append(String.valueOf(ints[i]));
            stringBuffer.append(",");
        }

        renderText(stringBuffer.toString());
    }
}
