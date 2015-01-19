package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.DictSyxw1;
import com.evstudio.lottery.pojos.Sh11x5;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ericren on 14-8-6.
 */
public class Sh11x5Service {
    /**
     *
     */
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");


    /**
     *
     */
    public static Sh11x5Service service = new Sh11x5Service();

    public List<Sh11x5> getTodayList() {
        Date currentTime = new Date();
        List<Sh11x5> list = null;

        list = Sh11x5.dao.find("select * from sh11x5 where drawyear=? and drawdate=?  order by dayperiods ",
//                "2014", "0804");
                yearFormat.format(currentTime), dateFormat.format(currentTime));
        System.out.println(System.currentTimeMillis());
        return list;
    }

    public List<Sh11x5> getRencentByCount( int count ) {
        List<Sh11x5> list = null;

        list = Sh11x5.dao.find("select * from sh11x5 order by periods desc limit 0, " + count );

        return list;
    }


    /**
     * @param sh11x5s
     * @return
     */
    public List<String[]> getPercentOfWinning(List<Sh11x5> sh11x5s) {
        NumberFormat numberFormat = new DecimalFormat("0.00");
        List<String[]> listResult = new ArrayList<String[]>();

        if (null != sh11x5s && sh11x5s.size() > 0) {
            for (int i = 0; i < sh11x5s.size(); i++) {
                String[] strP = new String[5];
                Sh11x5 sh11x5 = sh11x5s.get(i);

                int[] ints = {((Integer) sh11x5.get("winningnumber1")).intValue(),
                        ((Integer) sh11x5.get("winningnumber2")).intValue(),
                        ((Integer) sh11x5.get("winningnumber3")).intValue(),
                        ((Integer) sh11x5.get("winningnumber4")).intValue(),
                        ((Integer) sh11x5.get("winningnumber5")).intValue()};

                for (int j = 4; j > 0; j--) {
                    int iOri = ints[j];
                    for (int k = j - 1; k >= 0; k--) {
                        if (iOri > ints[k] || iOri > (11 - k))
                            ints[j]--;
                    }
                }

                for (int j = 0; j < 5; j++) {
                    try {
                        strP[j] = numberFormat.format(new Double(ints[j] / (11.0 - j) * 100.0));
                    } catch (Exception e) {

                    }
                }

                listResult.add(strP);
            }
        }

        return listResult;
    }

    /**
     * @param date
     * @return
     */
    public int[] getCountByDate(Date date) {
        List<Sh11x5> list = null;

        list = Sh11x5.dao.find("select * from sh11x5 where drawyear=? and drawdate=?  order by dayperiods ",
                yearFormat.format(date), dateFormat.format(date));

        return getCountByList(list);
    }

    /**
     * @param list
     * @return
     */
    public int[] getCountByList(List<Sh11x5> list) {
        int[] intResult = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                intResult[((Integer) list.get(i).get("winningnumber1")).intValue() - 1]++;
                intResult[((Integer) list.get(i).get("winningnumber2")).intValue() - 1]++;
                intResult[((Integer) list.get(i).get("winningnumber3")).intValue() - 1]++;
                intResult[((Integer) list.get(i).get("winningnumber4")).intValue() - 1]++;
                intResult[((Integer) list.get(i).get("winningnumber5")).intValue() - 1]++;
            }
        }

        return intResult;
    }

    /**
     * @param sh11x5
     * @return
     */
    public int getSh11x5FromDict(Sh11x5 sh11x5) {
        int iResult = 0;
        List<DictSyxw1> list = null;
        list = DictSyxw1.dao.find("select * from dictsyxw1 where number1 = ? and number2 =? and number3 = ? and number4 = ? and number5 = ?",
                ((Integer) sh11x5.get("winningnumber1")).intValue(),
                ((Integer) sh11x5.get("winningnumber2")).intValue(),
                ((Integer) sh11x5.get("winningnumber3")).intValue(),
                ((Integer) sh11x5.get("winningnumber4")).intValue(),
                ((Integer) sh11x5.get("winningnumber5")).intValue());
        if (null != list && list.size() == 1)
            iResult = ((Integer) list.get(0).get("id")).intValue();

        return iResult;
    }

    /**
     * @param firstnumber
     * @return
     */
    public List<Sh11x5> getFirstNumberList(String firstnumber) {
        List<Sh11x5> list = null;
        list = Sh11x5.dao.find("select * from sh11x5 where winningnumber1 = ?", firstnumber);
        return list;
    }

    /**
     * @param lastnumber
     * @return
     */
    public List<Sh11x5> getLastNumberList(String lastnumber) {
        List<Sh11x5> list = null;
        list = Sh11x5.dao.find("select * from sh11x5 where winningnumber5 = ?", lastnumber);
        return list;
    }

    public Sh11x5 getNext(Sh11x5 sh11x5) {
        Sh11x5 thisSh11x5 = null;
        String periods = sh11x5.getStr("periods");
        String drawdate = sh11x5.getStr("drawdate");
        String drawyear = sh11x5.getStr("drawyear");
        int dayperiods = ((Integer) sh11x5.get("dayperiods")).intValue();

        List<Sh11x5> sh11x5s = null;
        if (dayperiods < 90)
            dayperiods++;
        else {
            dayperiods = 1;
            System.out.println(periods.substring(0, 8));
            Date date = null;
            try {
                date = format.parse(periods.substring(0, 8));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果

            drawdate = dateFormat.format(date);
            drawyear = yearFormat.format(date);
        }

        sh11x5s = Sh11x5.dao.find("select * from sh11x5 where drawdate = ? and drawyear = ? and dayperiods = ?", drawdate, drawyear, dayperiods);
        if (null != sh11x5s && sh11x5s.size() == 1) {
            thisSh11x5 = sh11x5s.get(0);
        }

        return thisSh11x5;
    }

    public void save(Sh11x5 dto){
        dto.save();
    }

    public Sh11x5 getLast(){
        Sh11x5 sh11x5 = null;

        sh11x5 = Sh11x5.dao.findFirst("select * from sh11x5 order by periods desc limit 0 , 1");

        return sh11x5;
    }

}
