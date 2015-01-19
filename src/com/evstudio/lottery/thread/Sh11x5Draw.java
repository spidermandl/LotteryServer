package com.evstudio.lottery.thread;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.TUserBetlist;
import com.evstudio.lottery.pojos.TUserCapitalAccount;
import com.evstudio.lottery.services.UserBetListService;

import java.util.List;

/**
 * Created by ericren on 14-9-26.
 */
public class Sh11x5Draw implements Runnable {

    private String lotteryType = "";
    private String lotteryPeriods = "";
    private String lotteryWinning = "";

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLotteryPeriods() {
        return lotteryPeriods;
    }

    public void setLotteryPeriods(String lotteryPeriods) {
        this.lotteryPeriods = lotteryPeriods;
    }

    public String getLotteryWinning() {
        return lotteryWinning;
    }

    public void setLotteryWinning(String lotteryWinning) {
        this.lotteryWinning = lotteryWinning;
    }

    public Sh11x5Draw(String type, String periods, String winning) {
        super();
        lotteryType = type;
        lotteryPeriods = periods;
        lotteryWinning = winning;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (null == lotteryPeriods || "".equals(lotteryPeriods)) {
            return;
        }

        List<TUserBetlist> listBet = UserBetListService.service.getListByPeriods(lotteryPeriods);
        if (null == listBet) {
            return;
        }

        for (int i = 0; i < listBet.size(); i++) {
            TUserBetlist userBet = listBet.get(i);
            String valid = userBet.getStr("valid");
            if (null != valid) {
                if ("1".equals(valid)) {
                    userBet.set("valid", "3");
                    userBet.update();
                } else if ("2".equals(valid)) {
                    int betType = Integer.parseInt(userBet.getStr("bettype"));
                    if(Util.checkWin( userBet.getStr("betcontent"), lotteryWinning, betType) ){
                        userBet.set("betstatus","1");
                        int winmoney = 0;
                        switch (betType){
                            case 1:
                                winmoney = 13;
                                break;
                            case 3:
                                winmoney = 19;
                                break;
                            case 5:
                                winmoney = 540;
                                break;
                            case 7:
                                winmoney = 26;
                                break;
                            case 8:
                                winmoney = 9;
                                break;
                            default:
                                break;
                        }
                        winmoney = winmoney * userBet.getInt("multiple");
                        userBet.set("winmoney",String.valueOf(winmoney));
                        userBet.update();
                        TUserCapitalAccount account = TUserCapitalAccount.dao.findFirst("select * from t_user_capital_account where userid = ? and accounttype = '1' ",
                                userBet.getStr("userid"));
                        if( null != account){
                            int balance = Integer.parseInt(account.getStr("balance"));
                            balance += winmoney;
                            account.set("balance",String.valueOf(balance));
                            account.update();
                        }
                    }else{
                        userBet.set("betstatus","0");
                        userBet.update();
                    }
                }
            }
        }
    }
}
