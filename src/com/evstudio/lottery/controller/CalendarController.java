package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.services.CuxServices;
import com.evstudio.lottery.services.OptionsServices;
import com.evstudio.lottery.services.ShopServices;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericren on 14-8-25.
 */
public class CalendarController extends Controller {
    public void index() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");

        List<CuxXslLine> list = null;
        String sql = "";
        if ("100".equals(orgType)) {
            String svParam = getPara("sv");
            String sv = "";
            String svName = "";
            if (null !=  svParam && svParam.contains(",")) {
                sv = svParam.split(",")[0];
                svName = svParam.split(",")[1];
            }
            List<String[]> svs = OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), sv);
            setAttr("svs", svs);
            if (null == sv || "".equals(sv)) {
                sv = svs.get(0)[0];
                svName = svs.get(0)[1];
                list = CuxServices.services.getCuxLineByOrgId(svs.get(0)[0]);
            } else {
                list = CuxServices.services.getCuxLineByOrgId(sv);
            }
            sql = "select *from cux_xsl_head where role_id=" + sv + " and xsl_y='" + Util.getThisXslMonth() + "'";
            setAttr("sv", sv);
            setAttr("svName", svName);
        } else {
            list = CuxServices.services.getCuxLineByAccount(account);
            sql = "select *from cux_xsl_head where role_id=" + account.getBigDecimal("ORG_ID") + " and xsl_y='" + Util.getThisXslMonth() + "'";
        }

        CuxXslHead head = CuxXslHead.dao.findFirst(sql);
        if (null == head || null == head.getStr("COMMENTS")) {
            setAttr("comment","");
        }
        else {
            setAttr("comment", head.getStr("COMMENTS"));
        }


        setAttr("shops", OptionsServices.services.getCodeByOrgIdWithPriority(
                String.valueOf(account.getBigDecimal("ORG_ID")),
                ""));
        setAttr("cuxXsl", list);
        render("/WEB-INF/templates/default/calendar/index.ftl");
    }

    public void feedback(){
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");

        if ("100".equals(orgType)) {
            String comment = getPara("comment");
            String xslMonth = Util.getThisXslMonth();
            String sv = getPara("svFb");
            String svName = getPara("svNameFb");

            String sql = "select *from cux_xsl_head where role_id=" + sv + " and xsl_y='" + xslMonth + "'";
            CuxXslHead head = CuxXslHead.dao.findFirst(sql);
            if (null != head) {
                head.set("COMMENTS", comment);
                head.update();
            }
            else {
                head = new CuxXslHead();
                Timestamp tsNow = new Timestamp(System.currentTimeMillis());
                head.set("XSL_ID", CuxServices.services.getCuxXslHeadNext());
                head.set("XSL_Y", xslMonth);
                head.set("COMMENTS", comment);
                head.set("CREATE_DATE", tsNow);
                head.set("UPDATE_DATE", tsNow);
                head.set("CREATE_BY", account.getBigDecimal("ACCOUNT_ID"));
                head.set("UPDATE_BY", account.getBigDecimal("ACCOUNT_ID"));
                head.set("ATTRIBUTE_1", "N");
                head.set("ATTRIBUTE_2", svName);
                head.set("ROLE_ID", Integer.valueOf(sv));
                head.save();
            }
        }
        index();
    }

    public void plan() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        List<CuxXslLine> list = null;

        list = CuxServices.services.getCuxLineByAccount(account);

        setAttr("shops", OptionsServices.services.getCodeByOrgIdWithPriority(
                String.valueOf(account.getBigDecimal("ORG_ID")),
                ""));
        setAttr("cuxXsl", list);

        if (null == CuxServices.services.getXslHeadByAccountNMonth(account, Util.getThisXslMonth())) {
            setAttr("firstXsl", Util.getThisXslMonth());
            setAttr("lastXsl", Util.getYxjMonthByXslMonth(Util.getThisXslMonth()));

        } else {
            setAttr("firstXsl", "0");
            setAttr("lastXsl", "");
        }

        render("/WEB-INF/templates/default/calendar/plan.ftl");
    }

    public void searchByDate() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String beginDate = getPara("bdate");
        String endDate = getPara("edate");

        renderJson();
    }

    public void saveCalendar() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");

        String storeName = getPara("storename");
        String calDate = getPara("date");
        String calBeginHour = getPara("bHour");
        String comments = getPara("comments");

        if (null != calBeginHour)
            calBeginHour = calBeginHour.substring(0, 5);
        String calEndHour = getPara("eHour");
        if (null != calEndHour)
            calEndHour = calEndHour.substring(0, 5);

        String[] store = storeName.split(",");

        CuxXslHead head = CuxServices.services.getThisMonth(account);
        CuxXslLine line = new CuxXslLine();
        line.set("XSL_ID", String.valueOf(head.getBigDecimal("XSL_ID")));
        line.set("C_DATE", new Timestamp(Util.getDateFromString(calDate).getTime()));
        line.set("STORE_CODE", store[0]);
        line.set("STORE_YXJ", ShopServices.services.getPriorityByShopCode(store[0]));
        line.set("STORE_SJ", calBeginHour + "-" + calEndHour);
        line.set("C_WEEK", Util.getWeekOfDate(Util.getDateFromString(calDate)));
        line.set("CREATE_BY", account.getBigDecimal("ORG_ID"));
        if (store.length > 1) {
            line.set("ATTRIBUTE_2", store[1]);
            line.set("ATTRIBUTE_3", "1");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
        } else {
            line.set("ATTRIBUTE_2", "");
            if ("会议".equals(store[0])) {
                line.set("ATTRIBUTE_3", "2");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
            } else if ("培训".equals(store[0])) {
                line.set("ATTRIBUTE_3", "3");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
            } else if ("DT店铺".equals(store[0])) {
                line.set("ATTRIBUTE_3", "4");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
            } else if ("开撤店".equals(store[0])) {
                line.set("ATTRIBUTE_3", "5");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
            } else if ("休息".equals(store[0])) {
                line.set("ATTRIBUTE_3", "6");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
            } else if ("其他".equals(store[0])) {
                line.set("ATTRIBUTE_3", "7");//1,巡店;2,会议;3,培训;4,DT;5,开撤;6,休息;
            }
        }
        line.set("ATTRIBUTE_4", comments);
        line.set("ATTRIBUTE_1", "0");
        line.set("XSL_LINE_ID", CuxServices.services.getCuxXslLineNext());
        line.save();

        line.set("ATTRIBUTE_9", calBeginHour);
        line.set("ATTRIBUTE_10", calEndHour);

        renderJson(line);
    }

    /**
     *
     */
    public void deleteCalendar() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String lineid = getPara("lineid");
        boolean bSuccess = CuxXslLine.dao.deleteById(lineid);

        renderText("");
    }

    /**
     *
     */
    public void history() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");

        String month = getPara("month");
        boolean monthNull = (null == month || "".equals(month));
        String sv = getPara("sv");
        boolean svNull = (null == sv || "".equals(sv));

        List<CuxSxlMonth> months = Util.getXslMonthList();
        if (monthNull)
            month = months.get(0).getStr("XSL_MONTH");

        setAttr("month", month);
        setAttr("months", months);

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select *from cux_xsl_head where xsl_y='" + month + "'");

        List<String[]> shops = new ArrayList<String[]>();

        if (null != orgType && "100".equals(orgType)) {
            List<String[]> svs = OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), sv);
            if (null == svs || svs.size() == 0) {
                render("/WEB-INF/templates/default/calendar/report.ftl");
                return;
            }
            if (svNull)
                sv = svs.get(0)[0];

            sbSql.append(" and role_id=").append(sv);
            setAttr("svs", svs);
            shops = OptionsServices.services.getCodeByOrgIdWithPriority(sv, "");
        } else {
            sbSql.append(" and role_id=" + account.getBigDecimal("ORG_ID"));
            shops = OptionsServices.services.getCodeByOrgIdWithPriority(String.valueOf(account.getBigDecimal("ORG_ID")), "");
        }

        CuxXslHead head = CuxXslHead.dao.findFirst(sbSql.toString());

        if (null == head || null == shops) {
            render("/WEB-INF/templates/default/calendar/report.ftl");
            return;
        }

        List<String[]> params = new ArrayList<String[]>();
        for (int i = 0; i < shops.size(); i++) {
            String[] shop = shops.get(i);
            String[] param = new String[8];
            param[0] = head.getStr("XSL_Y");
            param[1] = head.getStr("ATTRIBUTE_2");
            param[2] = shop[0];
            param[3] = shop[1];
            param[4] = shop[3];
            String sql = "select count(*) from cux_xsl_line where xsl_id='" + head.getNumber("XSL_ID") + "' and store_code='" + shop[0] + "'";
            CuxXslLine line = CuxXslLine.dao.findFirst(sql);
            int weeknum = Util.weekCountOfXsl(month, param[4]);
            param[5] = String.valueOf(weeknum);
            if (null == line || null == line.getNumber("COUNT(*)")) {
                param[6] = "0";
                param[7] = String.valueOf(0 - weeknum);
            } else {
                param[6] = String.valueOf(line.getNumber("COUNT(*)"));
                param[7] = String.valueOf(line.getNumber("COUNT(*)").intValue() - weeknum);
            }

            params.add(param);
        }

        setAttr("params", params);


        render("/WEB-INF/templates/default/calendar/report.ftl");
    }

    public void xslMonth() {
        List<CuxSxlMonth> xslMonths = Util.getXslMonthList();
        setAttr("xslMonths", xslMonths);

        render("/WEB-INF/templates/default/calendar/xslMonth.ftl");
    }

    public void getXslMonthDetail() {
        String StrReturn = "";
        String month = getPara("month");
        if (null != month && !"".equals(month)) {
            String sql = "select *from cux_sxl_month where xsl_month='" + month + "'";
            CuxSxlMonth xslMonth = CuxSxlMonth.dao.findFirst(sql);
            if (null != xslMonth) {
                StrReturn = xslMonth.getStr("M_START") + "," + xslMonth.getStr("N_END");
            }
        }
        renderText(StrReturn);
    }

    public void saveXslMonth() {
        String month = getPara("months");
        String start = getPara("start");
        String end = getPara("end");
        boolean monthNull = null == month || "".equals(month);
        boolean startNull = null == start || "".equals(start);
        boolean endNull = null == end || "".equals(end);
        if (!monthNull && !startNull && !endNull) {
            String sql = "select *from cux_sxl_month where xsl_month='" + month + "'";
            CuxSxlMonth xslMonth = CuxSxlMonth.dao.findFirst(sql);
            if (null == xslMonth) {
                xslMonth = new CuxSxlMonth();
                xslMonth.set("XSL_MONTH", month);
                xslMonth.set("M_START", start);
                xslMonth.set("N_END", end);
                xslMonth.set("CLASS_ID", Db.queryBigDecimal("select CUX_SXL_MONTH_S.nextval from dual").intValue());
                xslMonth.save();
            } else {
                xslMonth.set("M_START", start);
                xslMonth.set("N_END", end);
                xslMonth.update();
            }
        }

        xslMonth();
    }

}
