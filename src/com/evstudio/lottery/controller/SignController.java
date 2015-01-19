package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.HttpInvoker;
import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.services.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfinal.core.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SignController extends Controller {

    public void index() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");

        String sql = "select * from T_SHOP_CHECK_RECORD where checkout_time is null" +
                " and create_id='" + account.getBigDecimal("ORG_ID").intValue() + "'" +
                " order by id desc";

        TShopCheckRecord tShopCheckRecord = TShopCheckRecord.dao
                .findFirst(sql);
        if (tShopCheckRecord != null) {
            String option = String.valueOf(tShopCheckRecord.getNumber("SHOP_CODE")) + "," +
                    tShopCheckRecord.getStr("SHOP_NAME");
            System.out.println( option  );
            ArrayList<String[]> arrayList = new ArrayList<String[]>();
            String[] strs = {String.valueOf(tShopCheckRecord.getNumber("SHOP_CODE")), tShopCheckRecord.getStr("SHOP_NAME"), ""};
            arrayList.add(strs);
            setAttr("shops", arrayList);
        } else {
            setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
        }

        render("/WEB-INF/templates/default/sign/index.ftl");
    }

    public void doSign() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String today = Util.getToday();

        String shop = getPara("shopCode");
        if (null == shop) {
            redirect("/sign/index");
            return;
        }

        String shopCode = null;
        String shopName = null;
        if (shop.contains(",")) {
            shopCode = shop.split(",")[0];
            shopName = shop.split(",")[1];
        } else {
            shopCode = shop;
        }
        setAttr("shopCode", shopCode);
        setAttr("shopName", shopName);

        String sql = "select * from t_shop_check_record where shop_code=" + shopCode +
                " and create_id='" + account.getBigDecimal("ORG_ID").intValue() + "' order by id desc";
        TShopCheckRecord record = TShopCheckRecord.dao.findFirst(sql);

        setAttr("record", record);

        render("/WEB-INF/templates/default/sign/sign.ftl");
    }

    public void signIn() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String thisXslMonth = Util.getThisXslMonth();
        Timestamp tsNow = new Timestamp(System.currentTimeMillis());

        String shopCode = getPara("shopCode");
        String shopName = getPara("shopName");
        String lati = getPara("lati");
        String longi = getPara("longi");
        if (null == shopCode) {
            redirect("/sign/index");
            return;
        }

        String addrJson = "";
        try {
            addrJson = HttpInvoker.getAddressByLocation(lati,longi );

            JsonParser jsonparer = new JsonParser();
            JsonObject jsonObject =  jsonparer.parse(addrJson).getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("result");
            JsonElement jsonElement = jsonObject.get("formatted_address");
            addrJson = jsonElement.getAsString();
        }catch (Exception e){
            e.printStackTrace();
            addrJson = "";
        }

        TShopCheckRecord record = new TShopCheckRecord();
        record.set("ID", CheckRecordServices.services.getTShopCheckRecordNext());
        record.set("SHOP_CODE", shopCode);
        record.set("PERIODS", thisXslMonth);
        record.set("SHOP_NAME", shopName);
        record.set("LATITUDE", lati);
        record.set("LONGITUDE", longi);
        record.set("SIGN_ADDR", addrJson );
        record.set("CHECKDATE", Util.getToday());
        record.set("CHECKIN_TIME", tsNow);
        record.set("CREATE_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
        record.set("CREATETIME", tsNow);
        record.set("UPDATE_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
        record.set("UPDATETIME", tsNow);

        record.save();

        setAttr("shopCode", shopCode);
        setAttr("shopName", shopName);

        setAttr("record", record);
        render("/WEB-INF/templates/default/sign/sign.ftl");
    }

    public void signOut() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String recordid = getPara("recordid");
        String lati = getPara("lati");
        String longi = getPara("longi");

        if (null == recordid) {
            redirect("/sign/doSign");
            return;
        }

        String addrJson = "";
        try {
            addrJson = HttpInvoker.getAddressByLocation(lati,longi );

            JsonParser jsonparer = new JsonParser();
            JsonObject jsonObject =  jsonparer.parse(addrJson).getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("result");
            JsonElement jsonElement = jsonObject.get("formatted_address");
            addrJson = jsonElement.getAsString();
        }catch (Exception e){
            e.printStackTrace();
            addrJson = "";
        }

        Timestamp tsNow = new Timestamp(System.currentTimeMillis());
        TShopCheckRecord record = CheckRecordServices.services.getTShopCheckRecordById(recordid);
        record.set("LATITUDE_SIGNOUT", lati);
        record.set("LONGITUDE_SIGNOUT", longi);
        record.set("SIGN_OUT_ADDR", addrJson );
        record.set("CHECKOUT_TIME", tsNow);
        record.set("UPDATE_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
        record.set("UPDATETIME", tsNow);
        record.update();

        setAttr("shopCode", record.getNumber("SHOP_CODE"));
        setAttr("shopName", record.getStr("SHOP_NAME"));

        setAttr("record", record);
        render("/WEB-INF/templates/default/sign/sign.ftl");
    }

    public void history() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");
        String today = Util.getToday();

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select *from t_shop_check_record where ");
        String start = getPara("start");
        String end = getPara("end");
        String svOrgName = getPara("svorgname");
        String shopcode = getPara("shopcode");

        boolean svNull = (null == svOrgName || "".equals(svOrgName));
        boolean shopNull = (null == shopcode || "".equals(shopcode));
        boolean startNull = (null == start || "".equals(start));
        boolean endNull = (null == end || "".equals(end));
        if (startNull && endNull) {
            start = today;
            end = today;
            sbSql.append("checkdate='" + today + "'");
        } else if (startNull && !endNull) {
            sbSql.append("rownum<100 and checkdate<='" + end + "'");
        } else if (!startNull && endNull) {
            sbSql.append("checkdate>='" + start + "' and checkdate<='" + today + "'");
        } else {
            sbSql.append("checkdate>='" + start + "' and checkdate<='" + end + "'");
        }

        if (null != orgType && "100".equals(orgType)) {
            List<String[]> svs = OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), svOrgName);
            List<String[]> shops = OptionsServices.services.getCodeByOrgId(svOrgName, shopcode);
            if (svNull) {
                StringBuffer sbSv = new StringBuffer();
                for (int i = 0; i < svs.size(); i++) {
                    sbSv.append("'").append(svs.get(i)[0]).append("',");
                }
                String strSv = sbSv.toString();
                if (strSv.endsWith(","))
                    strSv = strSv.substring(0, strSv.length() - 1);
                sbSql.append(" and create_id in(").append(strSv).append(")");
            } else {
                sbSql.append(" and create_id='").append(svOrgName).append("'");
            }

            if (!shopNull) {
                sbSql.append(" and shop_code=" + shopcode);
            }

            setAttr("svs", svs);
            setAttr("shops", shops);
        } else {
            sbSql.append(" and create_id='" + account.getBigDecimal("ORG_ID") + "'");
            if (!shopNull) {
                sbSql.append(" and shop_code=" + shopcode);
            }

            setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
        }

        sbSql.append(" order by id desc");

        List<TShopCheckRecord> records = TShopCheckRecord.dao.find(sbSql.toString());
        //取地址
        if (null != records) {
            for (int i = 0; i < records.size(); i++) {
                TShopCheckRecord record = records.get(i);
                ScmOrgRelation sor = ScmOrgRelation.dao.findFirst(
                        "select * from scm_org_relation where org_code = '" +
                                record.getNumber("SHOP_CODE") + "'");
                if (null != sor) {
                    record.set("COMMENTS", sor.getStr("ATTRIBUTE_15"));
                    records.set(i, record);
                }
            }
        }
        //--END 取地址--
        setAttr("start", start);
        setAttr("end", end);
        setAttr("records", records);

        render("/WEB-INF/templates/default/sign/signHistory.ftl");
    }

}
