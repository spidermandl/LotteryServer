package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.services.*;
import com.google.gson.Gson;
import com.jfinal.core.Controller;

import java.util.ArrayList;
import java.util.List;

public class ReportController extends Controller {
    public void index() {

    }

    public void dayForm() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");
        String day = getPara("day");
        String sv = getPara("sv");
        List<String[]> svs = new ArrayList<String[]>();

        if (orgType.equals("100")) {
            if (null == sv) {
                svs = OptionsServices.services.getCodeByOrgIdAndSelectedByName(String.valueOf(account.getBigDecimal("ORG_ID")), "");
            } else {
                svs = OptionsServices.services.getCodeByOrgIdAndSelectedByName(String.valueOf(account.getBigDecimal("ORG_ID")), sv);
            }
        } else if (orgType.equals("200")) {
//            String[] svTmp = new String[3];
//            svTmp[0] = String.valueOf(account.getBigDecimal("ORG_ID"));
//            svTmp[1] = account.getStr("LOGIN_NAME");
//            svTmp[2] = "selected";
//            svs.add(svTmp);
            svs = OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), sv);
        }
        setAttr("svs", svs);

        if (null == day) {
            render("/WEB-INF/templates/default/report/dayForm.ftl");
            return;
        } else {
            setAttr("day", day);
            day = day + " 00:00:00";
        }


        if (null == sv && 0 != svs.size()) {
            sv = svs.get(0)[1];
        }

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select djf.* from sv_shop_day_jxb_form djf,sv_shop_day_form df");
        sbSql.append(" where djf.shop_jxb=df.shop_jxb");
        if (orgType.equals("100"))
            sbSql.append(" and df.sv_code='" + sv + "'");
        else
            sbSql.append(" and df.shop_code='" + sv + "'");
        sbSql.append(" and to_char(djf.shop_day_date,'yyyy-MM-dd HH24:mi:ss')='" + day + "'");
        sbSql.append(" order by djf.shop_day_date desc");
        SvShopDayJxbForm dayJxbForm = SvShopDayJxbForm.dao.findFirst(sbSql.toString());
        if (null == dayJxbForm) {
            render("/WEB-INF/templates/default/report/dayForm.ftl");
            return;
        }
        dayJxbForm.set("SHOP_XLJXBPJDJ", formatNumber(dayJxbForm.getStr("SHOP_XLJXBPJDJ"), false));
        dayJxbForm.set("SHOP_JXBCXPXSJEZJXBJXSJEDBL", formatNumber(dayJxbForm.getStr("SHOP_JXBCXPXSJEZJXBJXSJEDBL"), true));
        dayJxbForm.set("SHOP_JXBCXPDML", formatNumber(dayJxbForm.getStr("SHOP_JXBCXPDML"), true));
        dayJxbForm.set("SHOP_JXBCXPRXSSL", formatNumber(dayJxbForm.getStr("SHOP_JXBCXPRXSSL"), true));
        dayJxbForm.set("SHOP_JXBLDL", formatNumber(dayJxbForm.getStr("SHOP_JXBLDL"), true));
        dayJxbForm.set("SHOP_JXBCXPMDFGL", formatNumber(dayJxbForm.getStr("SHOP_JXBCXPMDFGL"), true));

        setAttr("dayJabForm", dayJxbForm);

        String sql = "select *from sv_shop_day_form where ";
        if (orgType.equals("100"))
            sql += "sv_code='" + sv + "'";
        else
            sql += "shop_code='" + sv + "'";
        sql += " and to_char(shop_day_date,'yyyy-MM-dd HH24:mi:ss')='" + day + "'";
        List<SvShopDayForm> dayForms = SvShopDayForm.dao.find(sql);
        if (null != dayForms) {
            for (int i = 0; i < dayForms.size(); i++) {
                SvShopDayForm dayForm = dayForms.get(i);
                dayForm.set("SHOP_SALE_MONEY", formatNumber(dayForm.getStr("SHOP_SALE_MONEY"), false));
                dayForm.set("SHOP_XSJEZB", formatNumber(dayForm.getStr("SHOP_XSJEZB"), false));
                dayForm.set("SHOP_BZLJXSJE", formatNumber(dayForm.getStr("SHOP_BZLJXSJE"), false));
                dayForm.set("SHOP_BZLJXSJEZB", formatNumber(dayForm.getStr("SHOP_BZLJXSJEZB"), false));
                dayForm.set("SHOP_XLXSJE", formatNumber(dayForm.getStr("SHOP_XLXSJE"), false));
                dayForm.set("SHOP_XLXSSL", formatNumber(String.valueOf(dayForm.getNumber("SHOP_XLXSSL")), false));
                dayForm.set("SHOP_BZLJXLXSSL", formatNumber(dayForm.getStr("SHOP_BZLJXLXSSL"), false));
                dayForm.set("SHOP_CXPXSE", formatNumber(dayForm.getStr("SHOP_CXPXSE"), false));
                dayForm.set("SHOP_XLPJDJ", formatNumber(dayForm.getStr("SHOP_XLPJDJ"), false));
                dayForm.set("SHOP_BZLJXSWCL", formatNumber(dayForm.getStr("SHOP_BZLJXSWCL"), true));
                dayForm.set("SHOP_XLXSJEZB", formatNumber(dayForm.getStr("SHOP_XLXSJEZB"), true));
                dayForm.set("SHOP_XPZXSEBL", formatNumber(dayForm.getStr("SHOP_XPZXSEBL"), true));
                dayForm.set("SHOP_DML", formatNumber(dayForm.getStr("SHOP_DML"), true));
                dayForm.set("SHOP_DSL", formatNumber(dayForm.getStr("SHOP_DSL"), true));
                dayForm.set("SHOP_CXPXSEZZXSEBL", formatNumber(dayForm.getStr("SHOP_CXPXSEZZXSEBL"), true));
                dayForm.set("SHOP_RXSPXSSL", formatNumber(dayForm.getStr("SHOP_RXSPXSSL"), true));
                dayForm.set("SHOP_CXPFGL", formatNumber(dayForm.getStr("SHOP_CXPFGL"), true));
                dayForm.set("SHOP_CXPDML", formatNumber(dayForm.getStr("SHOP_CXPDML"), true));
                dayForm.set("SHOP_LDL", formatNumber(dayForm.getStr("SHOP_LDL"), true));
            }
        }
        setAttr("dayForms", dayForms);

        render("/WEB-INF/templates/default/report/dayForm.ftl");
    }

    public void getWeeksByXslMonth() {
        String month = getPara("month");
        String sql = "";
        if (null == month || "".equals(month)) {
            sql = "select distinct(SHOP_WEEK) from sv_shop_week_jxb_form order by shop_week desc";
        } else {
            String sqlTmp = "select *from cux_sxl_month where xsl_month='" + month + "'";
            CuxSxlMonth csm = CuxSxlMonth.dao.findFirst(sqlTmp);
            sql = "select distinct(SHOP_WEEK) from sv_shop_week_jxb_form";
            sql += " where creation_date>=TO_DATE('" + csm.getStr("M_START") + "','yyyy-MM-dd') and creation_date<=TO_DATE('" + csm.getStr("N_END") + "','yyyy-MM-dd')";
            sql += " order by shop_week desc";
        }
        List<SvShopWeekJxbForm> weekJxbForms = SvShopWeekJxbForm.dao.find(sql);
        List<String[]> list = new ArrayList<String[]>();
        if (null != weekJxbForms) {
            for (int i = 0; i < weekJxbForms.size(); i++) {
                String[] tmp = new String[1];
                tmp[0] = weekJxbForms.get(i).getStr("SHOP_WEEK");
                list.add(tmp);
            }
        }

        Gson gson = new Gson();
        String gReturn = gson.toJson(list);
        renderJson(gReturn);
    }

    public void weekForm() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");
        String week = getPara("week");
        String svOrgName = getPara("svorgname");
        String shopcode = getPara("shopcode");
        String month = getPara("searchmonth");
        setAttr("month", month);
        setAttr("week", week);
        List<CuxSxlMonth> months = Util.getXslMonthList();
        setAttr("months", months);

        boolean svNull = (null == svOrgName || "".equals(svOrgName));
        boolean shopNull = (null == shopcode || "".equals(shopcode));
        boolean monthNull = (null == month || "".equals(month));
        boolean weekNull = (null == week || "".equals(week));

        List<String[]> svs = new ArrayList<String[]>();
        List<String[]> weeks = new ArrayList<String[]>();

        String sql = "select distinct(SHOP_WEEK) from sv_shop_week_jxb_form";
        if (!monthNull) {
            String sqlTmp = "select *from cux_sxl_month where xsl_month='" + month + "'";
            CuxSxlMonth csm = CuxSxlMonth.dao.findFirst(sqlTmp);
            sql += " where creation_date>=TO_DATE('" + csm.getStr("M_START") + "','yyyy-MM-dd') and creation_date<=TO_DATE('" + csm.getStr("N_END") + "','yyyy-MM-dd')";
        }
        sql += " order by shop_week desc";
        List<SvShopWeekJxbForm> weekJxbForms = SvShopWeekJxbForm.dao.find(sql);
        if (null != weekJxbForms) {
            for (int i = 0; i < weekJxbForms.size(); i++) {
                String[] weekTmp = new String[2];
                weekTmp[0] = weekJxbForms.get(i).getStr("SHOP_WEEK");

                if (null != week && week.equals(weekTmp[0])) {
                    weekTmp[1] = "selected";
                } else {
                    weekTmp[1] = "";
                }
                weeks.add(weekTmp);
            }
        }

        setAttr("weeks", weeks);
        if (0 == weeks.size()) {
            render("/WEB-INF/templates/default/report/weekForm.ftl");
            return;
        }


        if (null == week) {
            week = weeks.get(0)[0];
        }

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select wjf.* from sv_shop_week_jxb_form wjf,sv_shop_week_form wf");
        if (orgType.equals("100")) {
            svs = OptionsServices.services.getCodeByOrgIdAndSelectedByName(String.valueOf(account.getBigDecimal("ORG_ID")), svOrgName);
            if (svNull) {
                svOrgName = svs.get(0)[1];
            }
            sbSql.append(" where wjf.shop_jxb=wf.shop_jxb and wf.sv_code='" + svOrgName + "'");
            String sqlTmp = "select *from scm_org_relation where org_name='" + svOrgName + "'";
            ScmOrgRelation sor = ScmOrgRelation.dao.findFirst(sqlTmp);
            List<String[]> shops = OptionsServices.services.getCodeByOrgId(String.valueOf(sor.getNumber("ORG_ID")), shopcode);
            setAttr("shops", shops);
            setAttr("svs", svs);
        } else {
            sbSql.append(" where wjf.shop_jxb=wf.shop_jxb and wf.sv_code='" + account.getStr("LOGIN_NAME") + "'");
            setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
        }
        if (!weekNull)
            sbSql.append(" and wjf.shop_week='" + week + "'");

        sbSql.append(" order by wjf.shop_week desc");
        SvShopWeekJxbForm weekJxbForm = SvShopWeekJxbForm.dao.findFirst(sbSql.toString());
        if (null == weekJxbForm) {
            render("/WEB-INF/templates/default/report/weekForm.ftl");
            return;
        }
        weekJxbForm.set("SHOP_XLJXBPJDJ", formatNumber(weekJxbForm.getStr("SHOP_XLJXBPJDJ"), false));
        weekJxbForm.set("SHOP_JXBCXPXSJEZJXBJXSJEDBL", formatNumber(weekJxbForm.getStr("SHOP_JXBCXPXSJEZJXBJXSJEDBL"), true));
        weekJxbForm.set("SHOP_JXBCXPDML", formatNumber(weekJxbForm.getStr("SHOP_JXBCXPDML"), true));
        weekJxbForm.set("SHOP_JXBCXPZXSSL", formatNumber(weekJxbForm.getStr("SHOP_JXBCXPZXSSL"), true));
        weekJxbForm.set("SHOP_JXBLDL", formatNumber(weekJxbForm.getStr("SHOP_JXBLDL"), true));
        weekJxbForm.set("SHOP_JXBCXPMDFGL", formatNumber(weekJxbForm.getStr("SHOP_JXBCXPMDFGL"), true));

        setAttr("weekJxbForm", weekJxbForm);

        if (orgType.equals("100")) {
            sql = "select *from sv_shop_week_form where sv_code='" + svOrgName + "'";
        } else {
            sql = "select *from sv_shop_week_form where sv_code='" + account.getStr("LOGIN_NAME") + "'";
        }
        sql += " and shop_week='" + week + "'";
        if (!shopNull)
            sql += " and shop_code='" + shopcode + "'";
        List<SvShopWeekForm> weekForms = SvShopWeekForm.dao.find(sql);
        if (null != weekForms) {
            for (int i = 0; i < weekForms.size(); i++) {
                SvShopWeekForm weekForm = weekForms.get(i);
                weekForm.set("SHOP_XSJE", formatNumber(weekForm.getStr("SHOP_XSJE"), false));
                weekForm.set("SHOP_XSJEZB", formatNumber(weekForm.getStr("SHOP_XSJEZB"), false));
                weekForm.set("SHOP_XLXSJE", formatNumber(weekForm.getStr("SHOP_XLXSJE"), false));
                weekForm.set("SHOP_XLXSSL", formatNumber(weekForm.getStr("SHOP_XLXSSL"), false));
                weekForm.set("SHOP_XLPJDJ", formatNumber(weekForm.getStr("SHOP_XLPJDJ"), false));
                weekForm.set("SHOP_CXPXSE", formatNumber(weekForm.getStr("SHOP_CXPXSE"), false));
                weekForm.set("SHOP_RXXSJE", formatNumber(weekForm.getStr("SHOP_RXXSJE"), false));
                weekForm.set("SHOP_RXXSSL", formatNumber(weekForm.getStr("SHOP_RXXSSL"), false));
                weekForm.set("SHOP_SZRXXSJE", formatNumber(weekForm.getStr("SHOP_SZRXXSJE"), false));
                weekForm.set("SHOP_SZRXXSSL", formatNumber(weekForm.getStr("SHOP_SZRXXSSL"), false));

                weekForm.set("SHOP_XSZBWCL", formatNumber(weekForm.getStr("SHOP_XSZBWCL"), true));
                weekForm.set("SHOP_XLXSJEZB", formatNumber(weekForm.getStr("SHOP_XLXSJEZB"), true));
                weekForm.set("SHOP_XPZXSEBL", formatNumber(weekForm.getStr("SHOP_XPZXSEBL"), true));
                weekForm.set("SHOP_DML", formatNumber(weekForm.getStr("SHOP_DML"), true));
                weekForm.set("SHOP_DSL", formatNumber(weekForm.getStr("SHOP_DSL"), true));
                weekForm.set("SHOP_CXPXSEZZXSEBL", formatNumber(weekForm.getStr("SHOP_CXPXSEZZXSEBL"), true));
                weekForm.set("SHOP_ZCXPXSSL", formatNumber(weekForm.getStr("SHOP_ZCXPXSSL"), true));
                weekForm.set("SHOP_CXPFGL", formatNumber(weekForm.getStr("SHOP_CXPFGL"), true));
                weekForm.set("SHOP_CXPDML", formatNumber(weekForm.getStr("SHOP_CXPDML"), true));
                weekForm.set("SHOP_SZQBL", formatNumber(weekForm.getStr("SHOP_SZQBL"), true));
                weekForm.set("SHOP_SSZQBL", formatNumber(weekForm.getStr("SHOP_SSZQBL"), true));
                weekForm.set("SHOP_SZLZL", formatNumber(weekForm.getStr("SHOP_SZLZL"), true));
                weekForm.set("SHOP_SSZLZL", formatNumber(weekForm.getStr("SHOP_SSZLZL"), true));
                weekForm.set("SHOP_LDL", formatNumber(weekForm.getStr("SHOP_LDL"), true));
            }
        }
        setAttr("weekForms", weekForms);

        render("/WEB-INF/templates/default/report/weekForm.ftl");
    }


    private String formatNumber(String number, boolean isPercent) {

        if (null == number) {
            return "";
        }

        if (!number.contains(".")) {
            if (isPercent)
                number += "%";
            return number;
        }

        int index = number.indexOf(".");
        if (index + 3 < number.length()) {
            number = number.substring(0, index + 3);
        }

        if (isPercent)
            number += "%";
        return number;
    }

}
