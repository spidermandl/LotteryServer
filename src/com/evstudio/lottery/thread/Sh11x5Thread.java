package com.evstudio.lottery.thread;

import com.evstudio.lottery.pojos.Sh11x5;
import com.evstudio.lottery.services.Sh11x5Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

/**
 * Created by ericren on 14-8-11.
 */
public class Sh11x5Thread implements Runnable {
    public static Sh11x5Thread thread = new Sh11x5Thread();
    /**
     *
     */
    private String periods = "";
    private long sleepTime = 10;
    private String strNextTime = "";
    private static final long PERIOD_DAY = 60 * 1000;

    @Override
    public void run() {
        boolean bRun = true;
        if ("".equals(periods)) {
            Sh11x5 sh11x5 = Sh11x5Service.service.getLast();
            if (null != sh11x5 ) {
                periods = sh11x5.getStr("periods");
                System.out.println(periods);
            }
        }

        Timer timer = new Timer();
        Date date = new Date();
        java.sql.Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
        date = timestamp;
        Sh11x5GooooalTask newtask = new Sh11x5GooooalTask();
        Sh11x5GooooalTask.task = newtask;
        timer.schedule(newtask, date);
    }
}
