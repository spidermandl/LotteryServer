package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by ericren on 14-6-25.
 * <p/>
 * mysql> desc sh11x5;
 * +----------------+--------------+------+-----+---------+----------------+
 * | Field          | Type         | Null | Key | Default | Extra          |
 * +----------------+--------------+------+-----+---------+----------------+
 * | id             | int(11)      | NO   | PRI | NULL    | auto_increment |
 * | periods        | varchar(32)  | YES  |     | NULL    |                |
 * | publishdate    | date         | YES  |     | NULL    |                |
 * | dayperiods     | int(11)      | YES  |     | NULL    |                |
 * | winningnumber1 | int(11)      | YES  |     | NULL    |                |
 * | winningnumber2 | int(11)      | YES  |     | NULL    |                |
 * | winningnumber3 | int(11)      | YES  |     | NULL    |                |
 * | winningnumber4 | int(11)      | YES  |     | NULL    |                |
 * | winningnumber5 | int(11)      | YES  |     | NULL    |                |
 * | number1        | int(11)      | YES  |     | NULL    |                |
 * | number2        | int(11)      | YES  |     | NULL    |                |
 * | number3        | int(11)      | YES  |     | NULL    |                |
 * | number4        | int(11)      | YES  |     | NULL    |                |
 * | number5        | int(11)      | YES  |     | NULL    |                |
 * | number6        | int(11)      | YES  |     | NULL    |                |
 * | number7        | int(11)      | YES  |     | NULL    |                |
 * | number8        | int(11)      | YES  |     | NULL    |                |
 * | number9        | int(11)      | YES  |     | NULL    |                |
 * | number10       | int(11)      | YES  |     | NULL    |                |
 * | number11       | int(11)      | YES  |     | NULL    |                |
 * | drawyear       | varchar(4)   | YES  |     | NULL    |                |
 * | drawdate       | varchar(4)   | YES  |     | NULL    |                |
 * | drawtime       | time         | YES  |     | NULL    |                |
 * | importtime     | datetime     | YES  |     | NULL    |                |
 * | comments       | varchar(200) | YES  |     | NULL    |                |
 * | createid       | varchar(16)  | YES  |     | NULL    |                |
 * | modifyid       | varchar(16)  | YES  |     | NULL    |                |
 * | modifytime     | datetime     | YES  |     | NULL    |                |
 * | valid          | varchar(4)   | YES  |     | NULL    |                |
 * +----------------+--------------+------+-----+---------+----------------+
 * 29 rows in set (0.00 sec)
 */
public class Sh11x5 extends Model<Sh11x5> {
    public static Sh11x5 dao = new Sh11x5();

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
            System.out.println( periods.substring(0, 8) );
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date date = null;
            try {
                date = format.parse(periods.substring(0, 8) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
            date = calendar.getTime();   //这个时间就是日期往后推一天的结果

            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
            drawdate = dateFormat.format(date);
            drawyear = yearFormat.format(date);
        }

        sh11x5s = dao.find("select * from sh11x5 where drawdate = ? and drawyear = ? and dayperiods = ?", drawdate, drawyear, dayperiods);
        if (null != sh11x5s && sh11x5s.size() == 1) {
            thisSh11x5 = sh11x5s.get(0);
        }

        return thisSh11x5;
    }
}
