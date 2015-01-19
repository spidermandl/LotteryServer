package com.evstudio.lottery.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.evstudio.lottery.pojos.*;

/**
 * Created by ericren on 14-9-15.
 */
public class Util {
    private Util() {
    }

//    ;
//    private static Logger logger = Logger.getLogger(Util.class);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    private static Random random = new Random(System.currentTimeMillis());
    private static SimpleDateFormat formatOracle = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatToDate = new SimpleDateFormat("yyyy-MM-dd");

    public final static long CONST_WEEK = 3600 * 1000 * 24 * 7;

    public static String getNewIdByType(int iType) {
        StringBuffer sbId = new StringBuffer();
        long timestamp = System.currentTimeMillis();

        switch (iType) {
            case 1:  //New Client User
                sbId.append("CU");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 2:  //New Football Head
                sbId.append("ZH");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 3:  //New Football Line
                sbId.append("ZL");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 4:  //New Football Line
                sbId.append("DL");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 101:  //alipay
                sbId.append("OP");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 998:  //New Football Line
                sbId.append("FI");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 999:  //New Football Line
                sbId.append("NR");
                sbId.append(String.valueOf(timestamp));
                sbId.append(random.nextInt(10));
                break;
            case 1024:
                sbId.append("ZCIC");
                sbId.append(String.valueOf(timestamp).substring(3));
                int iCode = random.nextInt(100);
                while( iCode < 10)
                    iCode = random.nextInt(100);
                sbId.append(String.valueOf(iCode));
            default:
                break;
        }

        return sbId.toString();
    }

    public static boolean checkWin(String usebet, String win, int type) {
        boolean bWin = false;
        String[] uses = usebet.split(",");
        String[] wins = win.split(",");

        Arrays.sort(uses);
        Arrays.sort(wins);
        switch (type) {
            case 3:
                bWin = true;
                for (int i = 0; i < uses.length; i++) {
                    if (win.indexOf(uses[i]) == -1) {
                        bWin = false;
                        break;
                    }
                }
                break;
            case 5:
                bWin = true;
                for (int i = 0; i < wins.length; i++) {
                    if (!wins[i].equals(uses[i])) {
                        bWin = false;
                        break;
                    }
                }
                break;
            case 7:
                bWin = true;
                for (int i = 0; i < wins.length; i++) {
                    if (usebet.indexOf(wins[i]) == -1) {
                        bWin = false;
                        break;
                    }
                }
                break;
            case 8:
                bWin = true;
                for (int i = 0; i < wins.length; i++) {
                    if (usebet.indexOf(wins[i]) == -1) {
                        bWin = false;
                        break;
                    }
                }
                break;
            default:
                break;
        }

        return bWin;
    }

    public static String formatDate(Date date) {
        String str = "";
        if (null != date) {
            str = format.format(date);
        }
        return str;
    }

    public static String getNow() {
        return formatOracle.format(new Date());
    }

    public static String getToday() {
        return formatToDate.format(new Date());
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    public static Date getDateFromString(String dateStr) {
        try {
            return formatToDate.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getThisYxjMonth() {
        String sql = "select distinct(attribute3) from sv_shop_prority_head where attribute3 is not null order by attribute3 desc";

        SvShopProrityHead month = SvShopProrityHead.dao.findFirst(sql);

        return month.getStr("ATTRIBUTE3");
    }

    public static String getThisXslMonth() {
        SimpleDateFormat thisMonthFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = thisMonthFormat.format(new Date());

        String sql = "select * from cux_sxl_month where m_start <= '" + now + "' and '" + now + "' < n_end";

        CuxSxlMonth month = CuxSxlMonth.dao.findFirst(sql);

        return month.getStr("XSL_MONTH");
//        return "2014-09";
    }

    public static List<SvShopProrityHead> getYxjMonthList(){
        String sql = "select distinct(attribute3) from sv_shop_prority_head where attribute3 is not null order by attribute3 desc";
        return SvShopProrityHead.dao.find(sql);
    }

    public static List<CuxSxlMonth> getXslMonthList(){
        String sql = "select * from cux_sxl_month order by xsl_month desc";

        return CuxSxlMonth.dao.find(sql);
    }

    public static String getXslMonthByYxjMonth(String yxjMonth){
        String sql = "select *from cux_sxl_month where xsl_month>'" + yxjMonth + "' order by xsl_month asc";
        CuxSxlMonth month = CuxSxlMonth.dao.findFirst(sql);

        return month.getStr("XSL_MONTH");
    }

    public static String getYxjMonthByXslMonth(String xslMonth) {
        String sql = "select distinct(attribute3) from sv_shop_prority_head where attribute3 is not null and attribute3<'" + xslMonth + "' order by attribute3 desc";
        SvShopProrityHead month = SvShopProrityHead.dao.findFirst(sql);

        return month.getStr("ATTRIBUTE3");
    }

    public static int weekCountOfXsl(String strPeriod, String strPriority) {
        int iReturn = 0;
        if ("4".equals(strPriority)) {
            iReturn = 1;
        } else if ("3".equals(strPriority)) {
            iReturn = 2;
        } else if ("2".equals(strPriority) || "1".equals(strPriority)) {
            CuxSxlMonth cuxMonth = null;
            cuxMonth = CuxSxlMonth.dao.findFirst("select * from cux_sxl_month where xsl_month = ?", strPeriod);
            if (null != cuxMonth) {
                iReturn = getWeekBetweenDate(cuxMonth.getStr("M_START"), cuxMonth.getStr("N_END"));
            }
        }

        return iReturn;
    }

    public static int getWeekBetweenDate(String beginDate, String endDate) {
        int iReturn = 4;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar before = Calendar.getInstance();
        Calendar after = Calendar.getInstance();
        try {
            before.setTime(sdf.parse(beginDate));
            after.setTime(sdf.parse(endDate));
            int week = before.get(Calendar.DAY_OF_WEEK);
            before.add(Calendar.DATE, -week);
            week = after.get(Calendar.DAY_OF_WEEK);
            after.add(Calendar.DATE, 7 - week);
            int interval = (int) ((after.getTimeInMillis() - before
                    .getTimeInMillis()) / CONST_WEEK);
            iReturn = interval - 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iReturn;
    }

    public static void main(String[] arg0) {
        String[] str = {"03", "05", "07", "02", "01"};
        String win = "07,06,04,03,09";
        String usebet = "03,09,11";
        //01,02,04,05,08,09,10,11
        int useType = 3;

//        System.out.println( Util.checkWin(usebet,win,useType));
        for (int i = 0; i < 100; i++) {
            System.out.println(Util.getNewIdByType(1024));
            try {
                Thread.sleep(random.nextInt(1500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
