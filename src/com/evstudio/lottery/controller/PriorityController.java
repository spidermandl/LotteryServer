package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.services.OptionsServices;
import com.evstudio.lottery.services.PriorityServices;
import com.evstudio.lottery.services.ShopServices;
import com.evstudio.lottery.services.UserServices;
import com.google.gson.Gson;
import com.jfinal.core.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericren on 14-10-14.
 */
public class PriorityController extends Controller {
    public void index() {
        render("");
    }

    public void list() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String shopcode = getPara("shopcode");
        String searchMonth = getPara("searchmonth");

        String orgType = account.getStr("ORG_TYPE");
        List<SvShopSalesMsg> list = null;
        if (null != orgType && "100".equals(orgType)) {
            list = PriorityServices.services.getShopSalesBySMName(
                    account.getStr("LOGIN_NAME"),shopcode,searchMonth
            );
        } else {
            list = PriorityServices.services.getShopSalesBySvName(
                    account.getStr("LOGIN_NAME"),shopcode,searchMonth
            );
        }
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SvShopSalesMsg dto = list.get(i);
                dto.set("ATTRIBUTE15", PriorityServices.services.getNewPriorityByCodeNMonth(dto.getStr("SHOP_CODE"),
                        dto.getStr("PERIOD")));
                list.set(i, dto);
            }
        }

        if (null == list) list = new ArrayList<SvShopSalesMsg>();

        if (null != shopcode)
            setAttr("shopcode",shopcode);
        if (null != searchMonth)
            setAttr("searchMonth",searchMonth);
        if (null != orgType && "100".equals(orgType)) {
            setAttr("shops", OptionsServices.services.getSmCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
        } else {
            setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
        }
        setAttr("months", OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), searchMonth));
        setAttr("priorityList", list);
        render("/WEB-INF/templates/default/priority/oriList.ftl");
    }

    public void getListBySearch() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");

        render("");
    }

    public void beginPriorityListSearch() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");
        if (null != orgType && "100".equals(orgType)) {
            setAttr("svs", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
            setAttr("months", OptionsServices.services.getPriorityMonthBySMName(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
            render("/WEB-INF/templates/default/priority/smSearch.ftl");
            return;
        }

        setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
        setAttr("months", OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), ""));

        render("/WEB-INF/templates/default/priority/svSearch.ftl");
    }

    public void svSearch() {
        String shopcode = getPara("shopcode");
        //String searchMonth = getPara("searchmonth");
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        List<SvShopSalesMsg> list = null;

        list = PriorityServices.services.getShopSalesByCodeNMonth(shopcode, Util.getThisYxjMonth(), account.getStr("LOGIN_NAME"));
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SvShopSalesMsg dto = list.get(i);
                dto.set("ATTRIBUTE15", PriorityServices.services.getNewPriorityByCodeNMonth(dto.getStr("SHOP_CODE"),
                        dto.getStr("PERIOD")));
                list.set(i, dto);
            }
        }

        setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
//        setAttr("months", OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), searchMonth));
        setAttr("priorityList", list);

        render("/WEB-INF/templates/default/priority/svSearch.ftl");
    }

    public void applyModifyOriority() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String[] ids = getParaValues("cbMsgId");
        List<SvShopSalesMsg> list = null;
        String applyNumber = "";
        String applyMonth = "";
        String yxjMonth = Util.getThisYxjMonth();
        String xslMonth = Util.getXslMonthByYxjMonth(yxjMonth);
        CuxXslHead xslHead = null;

        list = PriorityServices.services.getSaleMsgsByIds(ids);
        if (null == list)
        	list = new ArrayList<SvShopSalesMsg>();
        List<SvShopSalesMsg> listTemp = new ArrayList<SvShopSalesMsg>();
        
        for (int i=0; i < list.size();i++) {
        	SvShopSalesMsg svmsg = list.get(i);

            String shopCode = svmsg.getStr("SHOP_CODE");
            String sql = "select *from cux_xsl_head where XSL_Y='" + xslMonth + "' and ROLE_ID=" +account.getBigDecimal("ORG_ID");
            xslHead = CuxXslHead.dao.findFirst(sql);
            setAttr("xsl", xslHead);
            if (null != xslHead) {
                break;
            }

            sql = "select *from sv_shop_prority_head where ATTRIBUTE3='" + yxjMonth + "' and APPLY_PERSON_ID='" + account.getBigDecimal("ACCOUNT_ID").intValue() + "'";
            List<SvShopProrityHead> prorityHeadList = SvShopProrityHead.dao.find(sql);
            if (prorityHeadList.size() != 0) {
                StringBuffer sbId = new StringBuffer();
                for (int j = 0; j < prorityHeadList.size(); j ++) {
                    if (j != prorityHeadList.size() - 1) {
                        sbId.append(prorityHeadList.get(j).getNumber("APPLY_NUMBER")).append(",");
                    }
        			else {
                        sbId.append(prorityHeadList.get(j).getNumber("APPLY_NUMBER"));
                    }
                }
                sql = "select *from sv_shop_prority_line where APPLY_NUMBER in (" + sbId.toString() + ") and SHOP_CODE='" + shopCode + "'";
                List<SvShopProrityLine> prorityLineList = SvShopProrityLine.dao.find(sql);
                if (prorityLineList.size() != 0) {
                    continue;
                }
            }
            listTemp.add(svmsg);
        }

        if (null != listTemp && listTemp.size() > 0) {
            SvShopSalesMsg svmsg = listTemp.get(0);
            applyMonth = svmsg.getStr("PERIOD");
        }

        applyNumber = String.valueOf(PriorityServices.services.getSvShopProrityHeadNext());

        setAttr("priorityListSelected", list);
        setAttr("priorityList", listTemp);
        setAttr("head_id", applyNumber);
        setAttr("applyMonth", applyMonth);

        render("/WEB-INF/templates/default/priority/applyPriority.ftl");
    }

    public void applySmModifyOriority() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String[] ids = getParaValues("cbMsgId");
        List<SvShopSalesMsg> list = null;
        String applyNumber = "";
        String applyMonth = "";
        String yxjMonth = Util.getThisYxjMonth();
        String xslMonth = Util.getXslMonthByYxjMonth(yxjMonth);
        CuxXslHead xslHead = null;
        String svXsl = "";

        list = PriorityServices.services.getSaleMsgsByIds(ids);
        if (null == list)
            list = new ArrayList<SvShopSalesMsg>();
        List<SvShopSalesMsg> listTemp = new ArrayList<SvShopSalesMsg>();
        String has = null;

        for (int i=0; i < list.size();i++) {
            SvShopSalesMsg svmsg = list.get(i);

            String shopCode = svmsg.getStr("SHOP_CODE");
            String sv = svmsg.getStr("SV");

            String sql = "select org_id from scm_org_relation where org_name='" + sv + "'";
            ScmOrgRelation sor = ScmOrgRelation.dao.findFirst(sql);
            if (null == sor) {
                continue;
            }
            int orgId = sor.getNumber("ORG_ID").intValue();

            sql = "select *from cux_xsl_head where XSL_Y='" + xslMonth + "' and ROLE_ID=" + orgId;
            xslHead = CuxXslHead.dao.findFirst(sql);
            //setAttr("xsl", xslHead);
            if (null != xslHead) {
                if (!svXsl.contains(sv))
                    svXsl = svXsl + sv + ",";
                continue;
            }
            has = "";

            sql = "select *from sv_shop_prority_head where ATTRIBUTE3='" + yxjMonth + "' and attribute1 in('" + orgId + "','" + account.getBigDecimal("ORG_ID") + "')";
            List<SvShopProrityHead> prorityHeadList = SvShopProrityHead.dao.find(sql);
            if (prorityHeadList.size() != 0) {
                StringBuffer sbId = new StringBuffer();
                for (int j = 0; j < prorityHeadList.size(); j ++) {
                    if (j != prorityHeadList.size() - 1) {
                        sbId.append(prorityHeadList.get(j).getNumber("APPLY_NUMBER")).append(",");
                    }
                    else {
                        sbId.append(prorityHeadList.get(j).getNumber("APPLY_NUMBER"));
                    }
                }
                sql = "select *from sv_shop_prority_line where APPLY_NUMBER in (" + sbId.toString() + ") and SHOP_CODE='" + shopCode + "'";
                List<SvShopProrityLine> prorityLineList = SvShopProrityLine.dao.find(sql);
                if (prorityLineList.size() != 0) {
                    continue;
                }
            }
            listTemp.add(svmsg);
        }

        if (null != listTemp && listTemp.size() > 0) {
            SvShopSalesMsg svmsg = listTemp.get(0);
            applyMonth = svmsg.getStr("PERIOD");
        }

        applyNumber = String.valueOf(PriorityServices.services.getSvShopProrityHeadNext());

        if (svXsl.endsWith(",")) {
            svXsl = svXsl.substring(0,svXsl.length() - 1);
        }

        if (svXsl.length() > 0) {
            setAttr("svXsl",svXsl);
        }
        if (null != has) {
            setAttr("has",has);
        }
        setAttr("priorityListSelected", list);
        setAttr("priorityList", listTemp);
        setAttr("head_id", applyNumber);
        setAttr("applyMonth", applyMonth);

        render("/WEB-INF/templates/default/priority/applySmPriority.ftl");
    }

    public void submitSmApply() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String head_id = getPara("head_id");
        String applyMonth = getPara("applyMonth");
        String[] ids = getParaValues("cbShop");
        String[] oldPriority = getParaValues("oldPriority");
        String[] newPriority = getParaValues("newpriority");
        String[] reasons = getParaValues("reason");
        String[] comments = getParaValues("comments");
        Timestamp tsNow = new Timestamp(System.currentTimeMillis());

        CuxSvOrgMapping orgMapping = (CuxSvOrgMapping) getSessionAttr("orgmapping");


        if (null != ids && ids.length > 0) {
            SvShopProrityHead head = new SvShopProrityHead();
            head.set("APPLY_NUMBER", Integer.parseInt(head_id));
            head.set("APPLY_PERSON_ID", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
            head.set("APPROVE_PERSON_ID", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
            head.set("APPLY_TIME", tsNow);
            head.set("APPROVE_TIME", tsNow);
            head.set("CREATION_DATE", tsNow);
            head.set("CREATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
            head.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
            head.set("LAST_UPDATE_DATE", tsNow);
            head.set("ATTRIBUTE1", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
            head.set("ATTRIBUTE3", applyMonth);
            head.save();


            for (int i = 0; i < ids.length; i++) {
                SvShopProrityLine line = new SvShopProrityLine();
                line.set("LINE_ID", PriorityServices.services.getSvShopProrityLineNext());
                line.set("APPLY_NUMBER", Integer.parseInt(head_id));
                line.set("SHOP_CODE", ids[i]);

                line.set("PRORITY_OLD", oldPriority[i]);
                line.set("PRORITY_NEW", newPriority[i]);
                line.set("REASON", reasons[i]);
                line.set("REMARK", comments[i]);
                line.set("USER_ID", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
                line.set("CREATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
                line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
                line.set("CREATION_DATE", tsNow);
                line.set("LAST_UPDATE_DATE", tsNow);
                line.set("ATTRIBUTE1", "Y");
                line.set("ROLE_ID", orgMapping.getStr("ROLE_ID"));
                    line.set("ORG_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
                line.save();
            }
        }

        redirect("./applylist");
    }

    public void submitApply() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String head_id = getPara("head_id");
        String applyMonth = getPara("applyMonth");
        String[] ids = getParaValues("cbShop");
        String[] oldPriority = getParaValues("oldPriority");
        String[] newPriority = getParaValues("newpriority");
        String[] reasons = getParaValues("reason");
        String[] comments = getParaValues("comments");

        CuxSvOrgMapping orgMapping = (CuxSvOrgMapping) getSessionAttr("orgmapping");

        int smId = 0;

        FndPrvLoginAccount parent = UserServices.services.getParentByUser(account);
        if (null != parent)
            smId = parent.getBigDecimal("ACCOUNT_ID").intValue();

        if (null != ids && ids.length > 0) {
            SvShopProrityHead head = new SvShopProrityHead();
            head.set("APPLY_NUMBER", Integer.parseInt(head_id));
            head.set("APPLY_PERSON_ID", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
            head.set("APPROVE_PERSON_ID", String.valueOf(smId));
            head.set("APPLY_TIME", new Timestamp(System.currentTimeMillis()));
//            head.set("CREATION_DATE", new Date(new java.util.Date().getTime() ));
            head.set("CREATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
            head.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
//            head.set("LAST_UPDATE_DATE", new Date(new java.util.Date().getTime() ));
            head.set("ATTRIBUTE1", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
            head.set("ATTRIBUTE3", applyMonth);
            head.save();


            for (int i = 0; i < ids.length; i++) {
                SvShopProrityLine line = new SvShopProrityLine();
                line.set("LINE_ID", PriorityServices.services.getSvShopProrityLineNext());
                line.set("APPLY_NUMBER", Integer.parseInt(head_id));
                line.set("SHOP_CODE", ids[i]);

                line.set("PRORITY_OLD", oldPriority[i]);
                line.set("PRORITY_NEW", newPriority[i]);
                line.set("REASON", reasons[i]);
                line.set("REMARK", comments[i]);
                line.set("USER_ID", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
                line.set("CREATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
                line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
//                line.set("CREATION_DATE", new Date(new java.util.Date().getTime() ));
//                line.set("LAST_UPDATE_DATE", new Date(new java.util.Date().getTime() ));
                line.set("ROLE_ID", orgMapping.getStr("ROLE_ID"));
                line.set("ORG_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
                line.save();
            }
        }

        redirect("./applylist");
    }

    public void applyList() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        List<SvShopProrityHead> list = PriorityServices.services.getApplyProrityListByUser(account);
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SvShopProrityHead dto = list.get(i);
                FndPrvLoginAccount user = FndPrvLoginAccount.dao.findById(
                        Integer.parseInt(dto.getStr("APPLY_PERSON_ID"))
                );
                if (null != user)
                    dto.set("ATTRIBUTE14", user.getStr("LOGIN_NAME"));
                dto.set("ATTRIBUTE15", String.valueOf(
                        PriorityServices.services.getApplyLineCountByHead(dto)
                ));
                list.set(i, dto);
            }
        }
        setAttr("applyHeadList", list);
        render("/WEB-INF/templates/default/priority/applyHeadList.ftl");
    }
    
    public void applylist(){
    	FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");
        String month = getPara("month");
        String shopcode = getPara("shopcode");
        String sv = getPara("svorgname");
        List<SvShopProrityHead> months = Util.getYxjMonthList();
        boolean monthNull = null == month || "".equals(month);
        boolean svNull = null == sv || "".equals(sv);
        boolean shopNull = (null == shopcode || "".equals(shopcode));
        setAttr("month",month);
        setAttr("months",months);

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select *from sv_shop_prority_head where");
        if ("100".equals(orgType)) {
            List<String[]> shops = OptionsServices.services.getCodeByOrgId(sv, shopcode);
            setAttr("shops", shops);

            if (svNull) {
                sbSql.append(" apply_person_id in(select account_id from fnd_prv_login_account where org_id in(select org_id from scm_org_relation where parent_id=" + account.getBigDecimal("ORG_ID") + "))");
            } else {
                sbSql.append(" apply_person_id in(select account_id from fnd_prv_login_account where org_id=" + sv + ")");
            }

            List<String[]> svs = OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), sv);
            setAttr("svs", svs);
        }
        else {
            sbSql.append(" apply_person_id=").append(account.getBigDecimal("ACCOUNT_ID"));
            setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
        }
        if (!monthNull) {
            sbSql.append(" and attribute3='").append(month).append("'");
        }
        sbSql.append(" order by attribute3 desc,apply_time desc");


    	List<SvShopProrityLine> linelist = new ArrayList<SvShopProrityLine>();
    	List<SvShopProrityHead> headlist = SvShopProrityHead.dao.find(sbSql.toString());
//    	List<SvShopProrityHead> headlist = PriorityServices.services.getSmApplyProrityListByUser(account);
    	if (null != headlist && headlist.size() > 0) {
            for (int i = 0; i < headlist.size(); i++) {
                SvShopProrityHead dto = headlist.get(i);
                String sql = "select *from sv_shop_prority_line where apply_number=" + dto.getNumber("APPLY_NUMBER");
                if (!shopNull)
                    sql = sql + " and shop_code='" + shopcode + "'";
                List<SvShopProrityLine> list = SvShopProrityLine.dao.find(sql);
                if (null == list || 0 == list.size()) 
                	continue;
                
                FndPrvLoginAccount user = FndPrvLoginAccount.dao.findById(
                        Integer.parseInt(dto.getStr("APPLY_PERSON_ID"))
                );
                for (int j = 0; j < list.size(); j++) {
                	SvShopProrityLine line = list.get(j);
                	line.set("ATTRIBUTE14", ShopServices.services.getShopNameByCode(line.getStr("SHOP_CODE")));
                	line.set("ATTRIBUTE13", dto.getStr("ATTRIBUTE3"));
                	if (null != user)
                		line.set("ATTRIBUTE15", user.getStr("LOGIN_NAME"));
//                	list.set(j, line);
                    linelist.add(line);
                }

            }
        }

    	setAttr("applyList", linelist);
    	render("/WEB-INF/templates/default/priority/applyLineList.ftl");
    }


    public void smSearch() {
        String svOrgName = getPara("svorgname");
        String shopcode = getPara("shopcode");
//        String searchMonth = getPara("searchmonth");
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        List<SvShopSalesMsg> list = null;

        list = PriorityServices.services.getSmShopSalesByCodeNMonth(String.valueOf(account.getBigDecimal("ORG_ID")),
                shopcode, Util.getThisYxjMonth(), svOrgName);

        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SvShopSalesMsg dto = list.get(i);
                dto.set("ATTRIBUTE15", PriorityServices.services.getNewPriorityByCodeNMonth(dto.getStr("SHOP_CODE"),
                        dto.getStr("PERIOD")));
                list.set(i, dto);
            }
        }

        setAttr("svs", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), svOrgName));
        setAttr("shops", OptionsServices.services.getCodeByOrgId(svOrgName, shopcode));
//        setAttr("months", OptionsServices.services.getPriorityMonthBySMName(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
        setAttr("priorityList", list);

        render("/WEB-INF/templates/default/priority/smSearch.ftl");
    }

    /**
     *
     */
    public void smGetSvOptions() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String svOrgName = getPara("svorgname");
        if (null == svOrgName)
            svOrgName = "";

        ArrayList<String[]> list = OptionsServices.services.getCodeByOrgId(
                String.valueOf(account.getBigDecimal("ORG_ID")),
                svOrgName);

        Gson gson = new Gson();
        String gReturn = gson.toJson(list);
        renderJson(gReturn);
    }

    /**
     *
     */
    public void smGetSvShopOptions() {
        String svOrgName = getPara("svorgname");
        String shopcode = getPara("shopcode");
        if (null == svOrgName)
            svOrgName = "";
        if (null == shopcode)
            shopcode = "";

        ArrayList<String[]> list = OptionsServices.services.getCodeByOrgId(
                svOrgName,
                shopcode);

        Gson gson = new Gson();
        String gReturn = gson.toJson(list);
        renderJson(gReturn);
    }

    /**
     *
     */
    public void smGetSvShopMonthOptions() {
        String svOrgName = getPara("svorgname");
        String shopcode = getPara("shopcode");
        String searchMonth = getPara("searchmonth");
        if (null == svOrgName)
            svOrgName = "";
        if (null == shopcode)
            shopcode = "";
        if (null == searchMonth)
            searchMonth = "";

        ArrayList<String[]> list = OptionsServices.services.getPriorityMonthByShopCode(
                shopcode,
                searchMonth);

        Gson gson = new Gson();
        String gReturn = gson.toJson(list);
        renderJson(gReturn);
    }

    public void smGetSvShopOptionsBySvName() {
        String svOrgName = getPara("svorgname");
        String sqlTmp = "select *from scm_org_relation where org_name='" + svOrgName + "'";
        ScmOrgRelation sor = ScmOrgRelation.dao.findFirst(sqlTmp);
        String shopcode = getPara("shopcode");
        if (null == svOrgName)
            svOrgName = "";
        if (null == shopcode)
            shopcode = "";

        ArrayList<String[]> list = OptionsServices.services.getCodeByOrgId(
                String.valueOf(sor.getNumber("ORG_ID")),
                shopcode);

        Gson gson = new Gson();
        String gReturn = gson.toJson(list);
        renderJson(gReturn);
    }

    public void getApplyDetailByHead() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String headId = getPara("applyHeadId");
        ArrayList<String[]> arrList = new ArrayList<String[]>();

        List<SvShopProrityLine> list = PriorityServices.services.getApplyLineByHeadId(headId);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String[] strs = new String[10];
                SvShopProrityLine line = list.get(i);
                strs[0] = String.valueOf(line.getBigDecimal("LINE_ID"));
                strs[1] = line.getStr("SHOP_CODE");
                strs[2] = ShopServices.services.getShopNameByCode(strs[1]);
                strs[3] = Util.formatDate(line.getDate("CREATION_DATE"));
                strs[4] = line.getStr("PRORITY_OLD");
                strs[5] = line.getStr("PRORITY_NEW");
                strs[6] = line.getStr("REASON");
                strs[7] = line.getStr("REMARK");
                strs[8] = line.getStr("ATTRIBUTE1");
                strs[9] = line.getStr("ATTRIBUTE2");
                arrList.add(strs);
            }
        }
        Gson gson = new Gson();
        String strReturn = gson.toJson(arrList);

        renderJson(strReturn);
    }

    public void approve() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String approveId = getPara("formApproveId");
        ArrayList<String[]> arrList = new ArrayList<String[]>();

        List<SvShopProrityLine> list = PriorityServices.services.getApplyLineByHeadId(approveId);
        for (int i = 0; i < list.size(); i++) {
            SvShopProrityLine line = list.get(i);
            line.set("ATTRIBUTE15", ShopServices.services.getShopNameByCode(line.getStr("SHOP_CODE")));
            list.set(i, line);
        }
        setAttr("approveList", list);
        setAttr("approveId", approveId);
        render("/WEB-INF/templates/default/priority/approveHeadList.ftl");
    }
    
    public void batchApprove() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String[] lineIds = getParaValues("lineid");
        if (null != lineIds) {
            for (int i = 0; i < lineIds.length; i++) {
                SvShopProrityLine line = PriorityServices.services.getLineByLineId(lineIds[i]);
                if (null != line) {
                    line.set("ATTRIBUTE1", "Y");
                    line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
                    line.set("LAST_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
                    line.update();
                }
            }
        }
        redirect("./applylist");
    }
    
    public void singleApprove() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String lineid = getPara("pid");
        String rlt = getPara("rlt");
        String rsn = getPara("rsn");

        SvShopProrityLine line = PriorityServices.services.getLineByLineId(lineid);
        if (null != line) {
            line.set("ATTRIBUTE1", rlt);
            if (null != rsn)
            	line.set("ATTRIBUTE2", rsn);
            line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
            line.set("LAST_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
            line.update();
        }
        redirect("./applylist");
    }

    public void approveForm() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String approveId = getPara("approveId");
        String[] lineIds = getParaValues("lineid");
        String[] approvedIts = getParaValues("approvedIt");
        String[] reasons = getParaValues("reason");
        String approved = "a";
        for (int i = 0; i < lineIds.length; i++) {
            if (null != approvedIts[i]
                    && "a".equals(approved)
                    && "N".equals(approvedIts[i]))
                approved = "u";
            if ("u".equals(approved)
                    && "Y".equals(approvedIts[i]))
                approved = "c";

            SvShopProrityLine line = PriorityServices.services.getLineByLineId(lineIds[i]);
            if (null != line) {
                line.set("ATTRIBUTE1", approvedIts[i]);
                line.set("ATTRIBUTE2", reasons[i]);
                line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
                line.set("LAST_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
                line.update();
            }
        }
        SvShopProrityHead head = PriorityServices.services.getHeadByHeadId(approveId);
        if (null != head) {
            head.set("APPROVE_RESULT", approved);
            head.set("STATUS", approved);
            head.set("APPROVE_TIME", new Timestamp(System.currentTimeMillis()));
            head.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
            head.set("LAST_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
            head.update();
        }

        redirect("./applyList");
    }
}
