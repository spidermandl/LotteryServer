package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.pojos.mobile.DltBetBean;
import com.evstudio.lottery.pojos.mobile.FootballGameInfo;
import com.evstudio.lottery.pojos.mobile.FootballInfoMix;
import com.evstudio.lottery.services.ClientUserService;
import com.evstudio.lottery.services.DeviceInfoService;
import com.evstudio.lottery.services.Sh11x5Service;
import com.evstudio.lottery.services.UserBetListService;
import com.evstudio.lottery.thread.Sh11x5GooooalTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ericren on 14-8-30.
 */
public class MobileController extends Controller {
    private Logger logger = Logger.getLogger(MobileController.class);

    public void index() {
        renderJson("");
    }

    public void startApp() {
        String reqcontent = "";
        try {
            reqcontent = URLDecoder.decode(
                    getPara("deviceInfo"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        logger.error(reqcontent);
        DeviceInfoService.service.setVersionCode(getPara("versionCode"));
        DeviceInfoService.service.saveRequest("/mobile/startApp", "post", reqcontent);

        String sh11x5NextTime = Sh11x5GooooalTask.task.getStrNextTime();
        String sh11x5NextPeriods = Sh11x5GooooalTask.task.getStrNextPeriods();
        if (null != sh11x5NextPeriods && sh11x5NextPeriods.length() > 3) {
            sh11x5NextPeriods = sh11x5NextPeriods.substring(2);
        }

        Sh11x5 sh11x5Last = Sh11x5Service.service.getLast();
        String sh11x5LastPeriods = sh11x5Last.getStr("periods");
        String sh11x5LastWin = String.valueOf(sh11x5Last.get("winningnumber1")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber2")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber3")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber4")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber5"));

        renderJson("{\"returncode\":\"0\",\"returnmsg\":\"获取信息成功！\",\"sh11x5nexttime\":\""
                + sh11x5NextTime + "\",\"sh11x5nextperiods\":\"" + sh11x5NextPeriods + "\", \"sh11x5lastperiods\":\""
                + sh11x5LastPeriods + "\", \"sh11x5lastwin\":\"" + sh11x5LastWin + "\",\"isNew\":\"1\",\"urlpath\":\"http://www.thefirstlottery.com/tmserver/v/qrverify?id=0\"}");
    }

    public void getSh11x5Next() {
        String sh11x5NextTime = Sh11x5GooooalTask.task.getStrNextTime();
        String sh11x5NextPeriods = Sh11x5GooooalTask.task.getStrNextPeriods();
        if (null != sh11x5NextPeriods && sh11x5NextPeriods.length() > 3) {
            sh11x5NextPeriods = sh11x5NextPeriods.substring(2);
        }

        Sh11x5 sh11x5Last = Sh11x5Service.service.getLast();
        String sh11x5LastPeriods = sh11x5Last.getStr("periods");
        String sh11x5LastWin = String.valueOf(sh11x5Last.get("winningnumber1")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber2")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber3")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber4")) + "," +
                String.valueOf(sh11x5Last.get("winningnumber5"));

        renderJson("{\"returncode\":\"0\",\"returnmsg\":\"获取信息成功！\",\"sh11x5nexttime\":\""
                + sh11x5NextTime + "\",\"sh11x5nextperiods\":\"" + sh11x5NextPeriods + "\", \"sh11x5lastperiods\":\""
                + sh11x5LastPeriods + "\", \"sh11x5lastwin\":\"" + sh11x5LastWin + "\"}");
    }

    public void login() {
        String mobile = getPara("mobile");
        String passwd = getPara("passwd");
        TClientUsers user = ClientUserService.service.login(mobile, passwd);
        if (null != user && !"".equals(user.getStr("userid"))) {
            String userid = user.getStr("userid");

            ClientUserService.service.setOline(user);
            TUserCapitalAccount accountCash = ClientUserService.service.getAccountByUserIdNType(userid, "1");
            TUserCapitalAccount accountAward = ClientUserService.service.getAccountByUserIdNType(userid, "2");

            String returnString = "{\"returncode\":\"0\",\"returnmsg\":\"用户登录成功！\",\"userid\":\"" +
                    userid + "\"" +
                    ",\"cash\":\"" + accountCash.getStr("balance") + "\"" +
                    ",\"award\":\"" + accountAward.getStr("balance") + "\"" +
                    "}";

            System.out.println(returnString);

            renderJson(returnString);
            return;
        }

        renderJson("{\"returncode\":\"9901\",\"returnmsg\":\"用户登录失败，用户名密码不正确！\"}");
    }

    public void register() {
        String mobile = getPara("mobile");
        String passwd = getPara("passwd");
        TClientUsers user = ClientUserService.service.getUserByMobile(mobile);
        if (null != user) {
            render("{\"returnmsg\":\"用户已存在！\",\"returncode\":\"9101\"}");
            return;
        }
        logger.error("username=" + mobile + "|password=" + passwd);
        user = new TClientUsers();
        user.set("mobile", mobile);
        user.set("loginname", mobile);
        user.set("chsname", "");
        user.set("passwd", passwd);

        user = ClientUserService.service.newUser(user);
        logger.error("username=" + mobile + "|password=" + passwd);
        getSession().setAttribute(user.getStr("userid"), user);
        logger.error("username=" + mobile + "|password=" + passwd);

        renderJson("{\"returnmsg\":\"用户注册成功！\",\"returncode\":\"0\",\"userid\":\"" + user.getStr("userid") + "\"}");
    }

    public void doBet() {
        String uid = getPara("mobileuid");
        String bettype = getPara("bettype");
        String betcontent = getPara("betcontent");
        String betcount = getPara("betcount");
        String periods = getPara("periods");

        TUserCapitalAccount account = null;
        TUserCapitalAccount accountAward = null;

        if (null == uid || "".equals(uid)) {
            render("{\"error\":\"error params\",\"errorcode\":\"9001\"}");
            return;
        }
        TClientUsers clientUser = null;

        clientUser = ClientUserService.service.getUserByUserid(uid);
        if (null == clientUser) {
            renderJson("{\"error\":\"not login\",\"errorcode\":\"9002\"}\"}");
            return;
        }

        if( betcontent.indexOf("dltNumber") > 0 ){
            dltPlay();
            renderJson("");
        }

        if ("51".equals(periods)) {
            Gson gson = new Gson();
            ArrayList<FootballGameInfo> betList = gson.fromJson(betcontent, new TypeToken<ArrayList<FootballGameInfo>>() {
            }.getType());

            TUserBetlistJczqHead head = new TUserBetlistJczqHead();
            head.set("head_id", Util.getNewIdByType(2));
            head.set("userid", clientUser.getStr("userid"));
            head.set("createtime", new Date());
            head.set("modifytime", new Date());
            head.set("bettime", new Date());
            head.set("bettype", bettype);
            head.set("betcontent", betcontent);
            head.set("modifyid", clientUser.getStr("userid"));
            head.set("valid", "1");
            head.save();

            ArrayList<Integer> playList = gson.fromJson(bettype, new TypeToken<ArrayList<Integer>>() {
            }.getType());
            int gameCount = betList.size();
            String gamePlayType = getPlayType(gameCount, playList);

            String strPlayType = "51";

            TUserBetlistJczqLine line = new TUserBetlistJczqLine();
            line.set("head_id", head.getStr("head_id"));
            line.set("line_id", Util.getNewIdByType(3));
            StringBuffer strBet = new StringBuffer();

            strBet.append("51");
            int iUp = 0;
            int iDown = 0;
            for (int k = 0; k < gameCount - 1; k++) {
                FootballGameInfo ftInfo = betList.get(k);
                if (ftInfo.selected[0] + ftInfo.selected[1] + ftInfo.selected[2] > 0)
                    iUp = 1;
                if (ftInfo.selected[3] + ftInfo.selected[4] + ftInfo.selected[5] > 0)
                    iDown = 1;
            }
            if (iUp > 0) {
                if (iDown > 0) {
                    strPlayType = "59";
                }
            } else {
                strPlayType = "56";
            }
            strBet.append(strPlayType);

            for (int k = 0; k < gameCount; k++) {
                if (k > 0)
                    strBet.append(",");
                FootballGameInfo ftInfo = betList.get(k);
                String weekDay = ftInfo.gameNumber.substring(0, 2);
                String matchNumber = ftInfo.gameNumber.substring(2, 5);
                if ("周一".equals(weekDay)) {
                    weekDay = "1";
                } else if ("周二".equals(weekDay)) {
                    weekDay = "2";
                } else if ("周三".equals(weekDay)) {
                    weekDay = "3";
                } else if ("周四".equals(weekDay)) {
                    weekDay = "4";
                } else if ("周五".equals(weekDay)) {
                    weekDay = "5";
                } else if ("周六".equals(weekDay)) {
                    weekDay = "6";
                } else if ("周日".equals(weekDay)) {
                    weekDay = "7";
                }

                if (k > 0)
                    strBet.append("-");
                strBet.append(weekDay);
                strBet.append(matchNumber);
                if (ftInfo.selected[0] == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("513");
                    else
                        strBet.append("3");
                } else if (ftInfo.selected[1] == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("511");
                    else
                        strBet.append("1");
                } else if (ftInfo.selected[2] == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("510");
                    else
                        strBet.append("0");
                    ;
                } else if (ftInfo.selected[3] == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("563");
                    else
                        strBet.append("3");
                } else if (ftInfo.selected[4] == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("561");
                    else
                        strBet.append("1");
                } else if (ftInfo.selected[5] == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("560");
                    else
                        strBet.append("0");
                }

                System.out.println(strBet.toString());
            }

            strBet.append("|");
            strBet.append(betcount);

            System.out.println(strBet.toString());
            line.set("bettype", strPlayType);
            line.set("gameinfo", gamePlayType);
            line.set("betcontent", strBet.toString());
            line.set("createtime", new Date());
            line.set("modifytime", new Date());
            line.set("valid", "1");
            line.save();

            account = ClientUserService.service.getAccountByUserIdNType(uid, "1");
            accountAward = ClientUserService.service.getAccountByUserIdNType(uid, "2");
            if (account != null) {
                int cash = Integer.parseInt(account.getStr("balance"));
                cash = cash - 2;
                account.set("balance", String.valueOf(cash));
                account.update();
            }

        } else if ("59".equals(periods)) {
            Gson gson = new Gson();
            ArrayList<FootballInfoMix> betList = gson.fromJson(betcontent, new TypeToken<ArrayList<FootballInfoMix>>() {
            }.getType());

            TUserBetlistJczqHead head = new TUserBetlistJczqHead();
            head.set("head_id", Util.getNewIdByType(2));
            head.set("userid", clientUser.getStr("userid"));
            head.set("createtime", new Date());
            head.set("modifytime", new Date());
            head.set("bettime", new Date());
            head.set("bettype", bettype);
            head.set("betcontent", betcontent);
            head.set("modifyid", clientUser.getStr("userid"));
            head.set("valid", "1");
            head.save();

            ArrayList<Integer> playList = gson.fromJson(bettype, new TypeToken<ArrayList<Integer>>() {
            }.getType());
            int gameCount = betList.size();

            String gamePlayType = getPlayType(gameCount, playList);

            String strPlayType = "51";

            TUserBetlistJczqLine line = new TUserBetlistJczqLine();
            line.set("head_id", head.getStr("head_id"));
            line.set("line_id", Util.getNewIdByType(3));
            StringBuffer strBet = new StringBuffer();

            strBet.append("51");
            int iPlayType1 = 0;
            int iPlayType2 = 0;
            int iPlayType3 = 0;
            int iPlayType4 = 0;
            int iPlayType5 = 0;

            for (int k = 0; k < gameCount - 1; k++) {
                FootballInfoMix ftInfo = betList.get(k);
                if (ftInfo.selected[0] + ftInfo.selected[1] + ftInfo.selected[2] > 0)
                    iPlayType1 = 1;
                if (ftInfo.selected[3] + ftInfo.selected[4] + ftInfo.selected[5] > 0)
                    iPlayType2 = 1;
                for (int m = 6; m < 15; m++) {
                    if (ftInfo.selected[m] == 1) {
                        iPlayType3 = 1;
                        break;
                    }
                }
                for (int m = 15; m < 23; m++) {
                    if (ftInfo.selected[m] == 1) {
                        iPlayType4 = 1;
                        break;
                    }
                }
                for (int m = 23; m < 54; m++) {
                    if (ftInfo.selected[m] == 1) {
                        iPlayType5 = 1;
                        break;
                    }
                }
            }

            if (iPlayType1 + iPlayType2 + iPlayType3 + iPlayType4 + iPlayType5 > 2)
                strPlayType = "59";
            else {
                if (iPlayType1 == 1) {
                    strPlayType = "51";
                } else if (iPlayType2 == 1) {
                    strPlayType = "56";
                } else if (iPlayType3 == 1) {
                    strPlayType = "54";
                } else if (iPlayType4 == 1) {
                    strPlayType = "53";
                } else if (iPlayType5 == 1) {
                    strPlayType = "52";
                }
            }

            strBet.append(strPlayType);

            for (int k = 0; k < gameCount; k++) {
                if (k > 0)
                    strBet.append(",");
                FootballInfoMix ftInfo = betList.get(k);
                String weekDay = ftInfo.gameNumber.substring(0, 2);
                String matchNumber = ftInfo.gameNumber.substring(2, 5);
                if ("周一".equals(weekDay)) {
                    weekDay = "1";
                } else if ("周二".equals(weekDay)) {
                    weekDay = "2";
                } else if ("周三".equals(weekDay)) {
                    weekDay = "3";
                } else if ("周四".equals(weekDay)) {
                    weekDay = "4";
                } else if ("周五".equals(weekDay)) {
                    weekDay = "5";
                } else if ("周六".equals(weekDay)) {
                    weekDay = "6";
                } else if ("周日".equals(weekDay)) {
                    weekDay = "7";
                }

                if (k > 0)
                    strBet.append("-");
                strBet.append(weekDay);
                strBet.append(matchNumber);

                iPlayType1 = 0;
                iPlayType2 = 0;
                iPlayType3 = 0;
                iPlayType4 = 0;
                iPlayType5 = 0;
                if (ftInfo.selected[0] + ftInfo.selected[1] + ftInfo.selected[2] > 0)
                    iPlayType1 = 1;
                if (ftInfo.selected[3] + ftInfo.selected[4] + ftInfo.selected[5] > 0)
                    iPlayType2 = 1;
                for (int m = 6; m < 15; m++) {
                    if (ftInfo.selected[m] == 1) {
                        iPlayType3 = 1;
                        break;
                    }
                }
                for (int m = 15; m < 23; m++) {
                    if (ftInfo.selected[m] == 1) {
                        iPlayType4 = 1;
                        break;
                    }
                }
                for (int m = 23; m < 54; m++) {
                    if (ftInfo.selected[m] == 1) {
                        iPlayType5 = 1;
                        break;
                    }
                }

                if (iPlayType1 == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("51");
                    strBet.append(getPlayCmd(ftInfo.selected, 1));
                }
                if (iPlayType2 == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("56");
                    strBet.append(getPlayCmd(ftInfo.selected, 2));
                }
                if (iPlayType3 == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("54");
                    strBet.append(getPlayCmd(ftInfo.selected, 3));
                }
                if (iPlayType4 == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("53");
                    strBet.append(getPlayCmd(ftInfo.selected, 4));
                }
                if (iPlayType5 == 1) {
                    if ("59".equals(strPlayType))
                        strBet.append("52");
                    strBet.append(getPlayCmd(ftInfo.selected, 5));
                }
                System.out.println(strBet.toString());
            }

            strBet.append("|");
            strBet.append(betcount);

            System.out.println(strBet.toString());
            line.set("bettype", strPlayType);
            line.set("betcontent", strBet.toString());
            line.set("gameinfo", gamePlayType);
            line.set("createtime", new Date());
            line.set("modifytime", new Date());
            line.set("valid", "1");
            line.save();

            account = ClientUserService.service.getAccountByUserIdNType(uid, "1");
            accountAward = ClientUserService.service.getAccountByUserIdNType(uid, "2");
            if (account != null) {
                int cash = Integer.parseInt(account.getStr("balance"));
                cash = cash - 2;
                account.set("balance", String.valueOf(cash));
                account.update();
            }
        } else if ("03".equals(periods)) {
            TUserBetlistDlt dlt = new TUserBetlistDlt();
            dlt.set("line_id", Util.getNewIdByType(4));
            dlt.set("bettype", bettype);
            dlt.set("gameinfo", betcontent);
            String[] betNumber = betcontent.split("\\|");
            System.out.println("bet number 0 is :" + betNumber[0]);
            String[] betReds = betNumber[0].split(",");
            System.out.println("bet number red length is :" + betReds.length);
            System.out.println("bet number 1 is :" + betNumber[1]);
            String[] betBlue = betNumber[1].split(",");
            System.out.println("bet number blue length is :" + betBlue.length);
            String betSave = "03";

            if (betReds.length > 5 || betBlue.length > 2) {
                betSave += "|F|" + betcontent;
            } else {
                betSave += "|" + betcontent;
            }
            dlt.set("betcontent", betSave);
            dlt.set("multiple", betcount);
            dlt.set("periods", periods);
            dlt.set("createtime", new Date());
            dlt.set("modifytime", new Date());
            dlt.set("userid", uid);
            dlt.set("valid", 1);
            dlt.save();

            account = ClientUserService.service.getAccountByUserIdNType(uid, "1");
            accountAward = ClientUserService.service.getAccountByUserIdNType(uid, "2");
            if (account != null) {
                int cash = Integer.parseInt(account.getStr("balance"));
                cash = cash - 2;
                account.set("balance", String.valueOf(cash));
                account.update();
            }

        } else {
            TUserBetlist betlist = new TUserBetlist();
            betlist.set("bettype", bettype);
            betlist.set("betcontent", betcontent);
            betlist.set("multiple", betcount);
            betlist.set("periods", periods);
            UserBetListService.service.saveBet(clientUser, betlist);
            account = ClientUserService.service.getAccountByUserIdNType(uid, "1");
            accountAward = ClientUserService.service.getAccountByUserIdNType(uid, "2");

            if (account != null) {
                int cash = Integer.parseInt(account.getStr("balance"));
                cash = cash - 2 * Integer.parseInt(betcount);
                account.set("balance", String.valueOf(cash));
                account.update();
            }
        }

        renderJson("{\"success\":\"0\"" +
                ",userid\":" +
                uid + "\"" +
                ",\"cash\":\"" + account.getStr("balance") + "\"" +
                ",\"award\":\"" + accountAward.getStr("balance") + "\"" +
                "}");
    }

    public void keepAlive() {
        renderJson("");
    }

    public void getCapitalAccount() {
        renderJson("");
    }

    public void getBetHistory() {
        renderJson("");
    }

    public void joinTheBar() {
        renderJson("");
    }

    public void getLastSh11x5() {
        renderJson("");
    }

    public void getSh11x5Recent10() {
        int icount = 10;

        List<Sh11x5> list = Sh11x5Service.service.getRencentByCount(icount);
        ArrayList<String[]> arrayList = new ArrayList<String[]>();

        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Sh11x5 sh11x5 = (Sh11x5) list.get(i);
                String[] strs = new String[]{
                        sh11x5.getStr("periods"),
                        String.valueOf(sh11x5.get("winningnumber1")),
                        String.valueOf(sh11x5.get("winningnumber2")),
                        String.valueOf(sh11x5.get("winningnumber3")),
                        String.valueOf(sh11x5.get("winningnumber4")),
                        String.valueOf(sh11x5.get("winningnumber5"))
                };
                arrayList.add(strs);
            }
        }
        Gson gson = new Gson();
        String strReturn = gson.toJson(arrayList);
        logger.error(strReturn);
        renderJson(strReturn);
    }

    public void getSh11x5Recent() {
        String recent = getPara("recent");
        int icount = 0;

        try {
            icount = Integer.parseInt(recent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Sh11x5> list = Sh11x5Service.service.getRencentByCount(icount);
        ArrayList<String[]> arrayList = new ArrayList<String[]>();

        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Sh11x5 sh11x5 = (Sh11x5) list.get(i);
                String[] strs = new String[]{
                        sh11x5.getStr("periods"),
                        String.valueOf(sh11x5.get("winningnumber1")),
                        String.valueOf(sh11x5.get("winningnumber2")),
                        String.valueOf(sh11x5.get("winningnumber3")),
                        String.valueOf(sh11x5.get("winningnumber4")),
                        String.valueOf(sh11x5.get("winningnumber5"))
                };
                arrayList.add(strs);
            }
        }
        Gson gson = new Gson();
        String strReturn = gson.toJson(arrayList);
        logger.error(strReturn);
        renderJson(strReturn);
    }

    public void userHistory() {
        String userid = getPara("userid");
        String querytype = getPara("querytype");
        List<TUserBetlist> betList = ClientUserService.service.getUserBetList(userid, querytype);
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        if (null != betList && betList.size() > 0) {
            for (int i = 0; i < betList.size(); i++) {
                TUserBetlist betitem = (TUserBetlist) betList.get(i);
                String[] strs = new String[]{
                        betitem.getStr("periods"),
                        betitem.getStr("bettype"),
                        betitem.getStr("betcontent"),
                        String.valueOf(betitem.get("multiple")),
                        betitem.getStr("betstatus"),
                        betitem.getStr("valid"),
                        betitem.getStr("winmoney"),
                        ""
                };
                arrayList.add(strs);
            }
        }

        Gson gson = new Gson();
        String strReturn = gson.toJson(arrayList);
        logger.error(strReturn);
        renderJson(strReturn);
    }

    public String getPlayType(int gameNumber, ArrayList<Integer> arr) {
        String strReturn = "";
        String strPlay = "";
        for (int i = 0; i < arr.size(); i++) {
            strPlay += String.valueOf(arr.get(i));
            if (i < arr.size() - 1)
                strPlay += ",";
        }
        switch (gameNumber) {
            case 8:
                break;
            case 7:
                break;
            case 6:
                break;
            case 5:
                if ("0,1,2,3".equals(strPlay))
                    strReturn = "08";
                else if ("0".equals(strPlay))
                    strReturn = "02";
                else if ("1".equals(strPlay))
                    strReturn = "03";
                else if ("0,1".equals(strPlay))
                    strReturn = "04";
                else if ("3".equals(strPlay))
                    strReturn = "05";
                else if ("0,1,2".equals(strPlay))
                    strReturn = "06";
                else if ("2,3".equals(strPlay))
                    strReturn = "07";
                break;
            case 4:
                if ("0".equals(strPlay))
                    strReturn = "02";
                else if ("1".equals(strPlay))
                    strReturn = "03";
                else if ("0,1".equals(strPlay))
                    strReturn = "04";
                else if ("3".equals(strPlay))
                    strReturn = "05";
                else if ("0,1,2".equals(strPlay))
                    strReturn = "06";
                break;
            case 3:
                if ("0".equals(strPlay))
                    strReturn = "02";
                else if ("1".equals(strPlay))
                    strReturn = "03";
                else if ("0,1".equals(strPlay))
                    strReturn = "04";
                break;
            case 2:
                strReturn = "02";
                break;
        }

        return strReturn;
    }

    public String getPlayCmd(int[] strSelecteds, int iType) {
        String strRetrun = "";

        switch (iType) {
            case 1:
                //胜负平
                if ("1".equals(String.valueOf(strSelecteds[0]))) {
                    strRetrun += "3";
                }
                if ("1".equals(String.valueOf(strSelecteds[1]))) {
                    strRetrun += "1";
                }
                if ("1".equals(String.valueOf(strSelecteds[2]))) {
                    strRetrun += "0";
                }
                break;
            case 2:
                //让球胜负平
                if ("1".equals(String.valueOf(strSelecteds[3]))) {
                    strRetrun += "3";
                }
                if ("1".equals(String.valueOf(strSelecteds[4]))) {
                    strRetrun += "1";
                }
                if ("1".equals(String.valueOf(strSelecteds[5]))) {
                    strRetrun += "0";
                }
                break;
            case 3:
                //半全场
                if ("1".equals(String.valueOf(strSelecteds[6]))) {
                    strRetrun += "33";
                }
                if ("1".equals(String.valueOf(strSelecteds[7]))) {
                    strRetrun += "31";
                }
                if ("1".equals(String.valueOf(strSelecteds[8]))) {
                    strRetrun += "30";
                }
                if ("1".equals(String.valueOf(strSelecteds[9]))) {
                    strRetrun += "13";
                }
                if ("1".equals(String.valueOf(strSelecteds[10]))) {
                    strRetrun += "11";
                }
                if ("1".equals(String.valueOf(strSelecteds[11]))) {
                    strRetrun += "10";
                }
                if ("1".equals(String.valueOf(strSelecteds[12]))) {
                    strRetrun += "03";
                }
                if ("1".equals(String.valueOf(strSelecteds[13]))) {
                    strRetrun += "01";
                }
                if ("1".equals(String.valueOf(strSelecteds[14]))) {
                    strRetrun += "00";
                }
                break;
            case 4:
                //总进球
                if ("1".equals(String.valueOf(strSelecteds[15]))) {
                    strRetrun += "0";
                }
                if ("1".equals(String.valueOf(strSelecteds[16]))) {
                    strRetrun += "1";
                }
                if ("1".equals(String.valueOf(strSelecteds[17]))) {
                    strRetrun += "2";
                }
                if ("1".equals(String.valueOf(strSelecteds[18]))) {
                    strRetrun += "3";
                }
                if ("1".equals(String.valueOf(strSelecteds[19]))) {
                    strRetrun += "4";
                }
                if ("1".equals(String.valueOf(strSelecteds[20]))) {
                    strRetrun += "5";
                }
                if ("1".equals(String.valueOf(strSelecteds[21]))) {
                    strRetrun += "6";
                }
                if ("1".equals(String.valueOf(strSelecteds[22]))) {
                    strRetrun += "7";
                }
                break;
            case 5:
                //比分
                if ("1".equals(String.valueOf(strSelecteds[23]))) {
                    strRetrun += "10";
                }
                if ("1".equals(String.valueOf(strSelecteds[24]))) {
                    strRetrun += "20";
                }
                if ("1".equals(String.valueOf(strSelecteds[25]))) {
                    strRetrun += "21";
                }
                if ("1".equals(String.valueOf(strSelecteds[26]))) {
                    strRetrun += "30";
                }
                if ("1".equals(String.valueOf(strSelecteds[27]))) {
                    strRetrun += "31";
                }
                if ("1".equals(String.valueOf(strSelecteds[28]))) {
                    strRetrun += "32";
                }
                if ("1".equals(String.valueOf(strSelecteds[29]))) {
                    strRetrun += "40";
                }
                if ("1".equals(String.valueOf(strSelecteds[30]))) {
                    strRetrun += "41";
                }
                if ("1".equals(String.valueOf(strSelecteds[31]))) {
                    strRetrun += "42";
                }
                if ("1".equals(String.valueOf(strSelecteds[32]))) {
                    strRetrun += "50";
                }
                if ("1".equals(String.valueOf(strSelecteds[33]))) {
                    strRetrun += "51";
                }
                if ("1".equals(String.valueOf(strSelecteds[34]))) {
                    strRetrun += "52";
                }
                if ("1".equals(String.valueOf(strSelecteds[35]))) {
                    strRetrun += "90";
                }
                if ("1".equals(String.valueOf(strSelecteds[36]))) {
                    strRetrun += "00";
                }
                if ("1".equals(String.valueOf(strSelecteds[37]))) {
                    strRetrun += "11";
                }
                if ("1".equals(String.valueOf(strSelecteds[38]))) {
                    strRetrun += "22";
                }
                if ("1".equals(String.valueOf(strSelecteds[39]))) {
                    strRetrun += "33";
                }
                if ("1".equals(String.valueOf(strSelecteds[40]))) {
                    strRetrun += "99";
                }
                if ("1".equals(String.valueOf(strSelecteds[41]))) {
                    strRetrun += "01";
                }
                if ("1".equals(String.valueOf(strSelecteds[42]))) {
                    strRetrun += "02";
                }
                if ("1".equals(String.valueOf(strSelecteds[43]))) {
                    strRetrun += "12";
                }
                if ("1".equals(String.valueOf(strSelecteds[44]))) {
                    strRetrun += "03";
                }
                if ("1".equals(String.valueOf(strSelecteds[45]))) {
                    strRetrun += "13";
                }
                if ("1".equals(String.valueOf(strSelecteds[46]))) {
                    strRetrun += "23";
                }
                if ("1".equals(String.valueOf(strSelecteds[47]))) {
                    strRetrun += "04";
                }
                if ("1".equals(String.valueOf(strSelecteds[48]))) {
                    strRetrun += "14";
                }
                if ("1".equals(String.valueOf(strSelecteds[49]))) {
                    strRetrun += "24";
                }
                if ("1".equals(String.valueOf(strSelecteds[50]))) {
                    strRetrun += "05";
                }
                if ("1".equals(String.valueOf(strSelecteds[51]))) {
                    strRetrun += "15";
                }
                if ("1".equals(String.valueOf(strSelecteds[52]))) {
                    strRetrun += "25";
                }
                if ("1".equals(String.valueOf(strSelecteds[53]))) {
                    strRetrun += "09";
                }

                break;
            default:
                break;
        }

        return strRetrun;
    }

    public void dltPlay() {
//        [{"dltNumber":"04,12,22,24,31|03,10","moneyTip":"1注2元","iBeishu":1,"iMoney":2,"iZhu":1,"dltStyle":1}]
//        betcontent=[{"dltNumber":"04,05,21,25,29|03,12","moneyTip":"1?2?","iBeishu":1,"iMoney":2,"iZhu":1,"dltStyle":1}]
//        mobileuid=CU14213780922546  periods=  betcount=1  iszhuijia=false
        String uid = getPara("mobileuid");
        String betCount = getPara("betcount");
        String betContent = getPara("betcontent");

        TClientUsers clientUser = null;

        clientUser = ClientUserService.service.getUserByUserid(uid);


        TUserCapitalAccount account = null;
        TUserCapitalAccount accountAward = null;

        Gson gson = new Gson();
        ArrayList<DltBetBean> list = new ArrayList<DltBetBean>();
        list = gson.fromJson(betContent, new TypeToken<ArrayList<DltBetBean>>() {
        }.getType());

        for( int i = 0; i < list.size(); i ++ ){
            DltBetBean dltBean = list.get(i);
            TUserBetlistDlt dlt = new TUserBetlistDlt();
            dlt.set("line_id", Util.getNewIdByType(4));
            dlt.set("bettype", "3");
            dlt.set("gameinfo", dltBean.getDltNumber());
            String[] betNumber = dltBean.getDltNumber().split("\\|");
            System.out.println("bet number 0 is :" + betNumber[0]);
            String[] betReds = betNumber[0].split(",");
            System.out.println("bet number red length is :" + betReds.length);
            System.out.println("bet number 1 is :" + betNumber[1]);
            String[] betBlue = betNumber[1].split(",");
            System.out.println("bet number blue length is :" + betBlue.length);
            String betSave = "03";

            if (betReds.length > 5 || betBlue.length > 2) {
                betSave += "|F|" + dltBean.getDltNumber();
            } else {
                betSave += "|" + dltBean.getDltNumber();
            }
            dlt.set("betcontent", betSave);
            dlt.set("multiple", betCount);
            dlt.set("periods", "");
            dlt.set("createtime", new Date());
            dlt.set("modifytime", new Date());
            dlt.set("userid", uid);
            dlt.set("valid", 1);
            dlt.save();

            account = ClientUserService.service.getAccountByUserIdNType(uid, "1");
            accountAward = ClientUserService.service.getAccountByUserIdNType(uid, "2");
            if (account != null) {
                int cash = Integer.parseInt(account.getStr("balance"));
                cash = cash - 2;
                account.set("balance", String.valueOf(cash));
                account.update();
            }

        }
    }
}
