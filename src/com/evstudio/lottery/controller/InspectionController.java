package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.services.InspectionServices;
import com.evstudio.lottery.services.OptionsServices;
import com.evstudio.lottery.services.ShopServices;
import com.evstudio.lottery.services.WeekReportServices;
import com.jfinal.core.Controller;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ericren on 14-8-25.
 */
public class InspectionController extends Controller {
    public void index() {

    }

    public void sign() {
        render("/WEB-INF/templates/default/inspection/sign.ftl");
    }

    public void assess() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");

        setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
        setAttr("months", OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), ""));
        render("/WEB-INF/templates/default/inspection/index.ftl");
    }

    public void doInspection() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String shopCode = getPara("shopcode");
        setAttr("pgdate",Util.getToday());
        if (null == shopCode) {
        	redirect("./assess");
        }
        else {
        	List<LookT> listLook1 = LookT.dao.find("select * from look_t where D = 'DWHJ' order by A ");
        	List<LookT> listLook2 = LookT.dao.find("select * from look_t where D = 'DNHJ' order by A ");
        	List<LookT> listLook3 = LookT.dao.find("select * from look_t where D = 'GKFW' order by A ");
        	List<LookT> listLook4 = LookT.dao.find("select * from look_t where D = 'YYGL' order by A ");
        	List<LookT> listLook5 = LookT.dao.find("select * from look_t where D = 'KCGL' order by A ");
            List<LookT> listLook6 = LookT.dao.find("select * from look_t where D = 'ZBGZ' order by A ");
        	
        	ScmOrgRelation shop = ScmOrgRelation.dao.findFirst("select * from SCM_ORG_RELATION where ORG_CODE = ?", shopCode);
        	setAttr("shopCode", shop.getStr("ORG_CODE"));
        	setAttr("shopName", shop.getStr("ORG_NAME"));
        	setAttr("checkId", InspectionServices.services.getCuxStoreCheckHeadNext());
        	setAttr("shopLevel", ShopServices.services.getPriorityByShopCode(shopCode));
//        setAttr("checkTime", Util.getNow());
        	setAttr("items1", listLook1);
        	setAttr("items2", listLook2);
        	setAttr("items3", listLook3);
        	setAttr("items4", listLook4);
        	setAttr("items5", listLook5);
            setAttr("items6", listLook6);
        	
        	render("/WEB-INF/templates/default/inspection/assess.ftl");
        }
    }
    
    public void detailInspection(){
    	int id = getParaToInt("id");
    	CuxStoreCheckHead head = CuxStoreCheckHead.dao.findById(id);
    	String sql = "select *from cux_store_check_line where check_head_id="+id+" order by check_line_id asc";
    	List<CuxStoreCheckLine> lineList = CuxStoreCheckLine.dao.find(sql);
    	setAttr("head", head);
    	setAttr("lineList", lineList);
    	render("/WEB-INF/templates/default/inspection/detail.ftl");
    }

    public void addInspection() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String checkDate = getPara("checkDate");
        String month = Util.getThisXslMonth();
        Timestamp tsNow = new Timestamp(System.currentTimeMillis());

        CuxStoreCheckHead head = new CuxStoreCheckHead();
        int headid = getParaToInt("checkId");
        StringBuffer sb = new StringBuffer();

        head.set("CHECK_HEAD_ID", headid);
        head.set("STORE_CODE", getPara("shopCode"));
        head.set("STORE_LEVEL", getPara("shopLevel"));
        head.set("MAG_LEVEL", getPara("ownerType"));
        String[] keyOptions = getParaValues("keyOptions");
        for (int i = 0; i < keyOptions.length; i++) {
            if (i < keyOptions.length - 1) {
                sb.append(keyOptions[i]).append(",");
            } else {
                sb.append(keyOptions[i]);
            }
        }
        head.set("ZDPG", sb.toString());
        String[] keyProblems = getParaValues("keyProblems");
        sb = new StringBuffer();
        for (int i = 0; i < keyProblems.length; i++) {
            if (i < keyProblems.length - 1) {
                sb.append(keyProblems[i]).append(",");
            } else {
                sb.append(keyProblems[i]);
            }
        }
        head.set("PGWT", sb.toString());
        String[] theKeyFirst = getParaValues("theKeyFirst");
        sb = new StringBuffer();
        for (int i = 0; i < theKeyFirst.length; i++) {
            if (i < theKeyFirst.length - 1) {
                sb.append(theKeyFirst[i]).append(",");
            } else {
                sb.append(theKeyFirst[i]);
            }
        }
        head.set("BHGYXPX", sb.toString());
        String[] theKeyOption = getParaValues("theKeyOption");
        sb = new StringBuffer();
        for (int i = 0; i < theKeyOption.length; i++) {
            if (i < theKeyOption.length - 1) {
                sb.append(theKeyOption[i]).append(",");
            } else {
                sb.append(theKeyOption[i]);
            }
        }
        head.set("RBBHG", sb.toString());
        head.set("ATTRIBUTE_1", "Y");
        head.set("ATTRIBUTE_2", getPara("shopName"));
        head.set("CHECK_DATE", checkDate);
        head.set("USER_ID", account.getStr("LOGIN_NAME"));
        head.set("CREATE_DATE", tsNow);
        head.set("UPDATE_DATE", tsNow);
        head.set("UPDATE_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
        head.set("CREATE_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
        head.set("ORG_ID", account.getBigDecimal("ORG_ID").intValue());

        boolean isHeadSaved = head.save();

        if (isHeadSaved) {
        	//保存或更新周工作汇报头信息
        	String sql = "select *from sv_work_report_head where c_mouth='" + month + "' and shop_code='" + getPara("shopCode") + "'";
        	SvWorkReportHead reportHead = SvWorkReportHead.dao.findFirst(sql);
        	if (null == reportHead) {
        		reportHead = new SvWorkReportHead();
        		reportHead.set("APPLY_NUMBER", WeekReportServices.services.getSvWorkReportHeadNext());
        		reportHead.set("ORG_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
        		reportHead.set("CREATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
        		reportHead.set("LAST_UPDATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
        		reportHead.set("CREATION_DATE", tsNow);
        		reportHead.set("LAST_UPDATE_DATE", tsNow);
        		reportHead.set("ATTRIBUTE_2", checkDate);
        		reportHead.set("C_MOUTH", month);
        		reportHead.set("SHOP_DJ", getPara("shopLevel"));
        		reportHead.set("SHOP_CODE", getPara("shopCode"));
        		reportHead.set("SHOP_NAME", getPara("shopName"));
                
        		reportHead.save();
        	}
        	else {
        		reportHead.set("LAST_UPDATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
        		reportHead.set("LAST_UPDATE_DATE", tsNow);
        		reportHead.set("ATTRIBUTE_2", reportHead.getStr("ATTRIBUTE_2") + "," + checkDate);
        		
        		reportHead.update();
        	}
        	
        	
        	//保存明细
        	String[] itemB = getParaValues("itemB");
        	String[] itemC = getParaValues("itemC");
        	String[] itemD = getParaValues("itemD");
        	String[] itemYes = getParaValues("itemYes");
        	String[] itemInput = getParaValues("itemInput");
        	for (int i = 0; i < itemB.length; i++) {
        		CuxStoreCheckLine line = new CuxStoreCheckLine();
        		line.set("CHECK_LINE_ID", InspectionServices.services.getCuxStoreCheckLineNext());
        		line.set("CHECK_HEAD_ID", headid);
        		line.set("CHECK_TYPE", itemD[i]);
        		line.set("TYPE_CODE", itemB[i]);
        		line.set("TYPE_NAME", itemC[i]);
        		line.set("TYPE_Y", itemYes[i]);
        		line.set("COMMENTS", itemInput[i]);
        		line.set("CREATE_DATE", tsNow);
        		line.set("UPDATE_DATE", tsNow);
        		line.set("UPDATE_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
        		line.set("CREATE_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
        		line.save();
        	}
        }
        redirect("./history");
    }

    public void history() {
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String orgType = account.getStr("ORG_TYPE");
        String today = Util.getToday();

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select *from cux_store_check_head where ");
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
            sbSql.append("check_date='" + today + "'");
        } else if (startNull && !endNull) {
            sbSql.append("check_date<='" + end + "'");
        } else if (!startNull && endNull) {
            sbSql.append("check_date>='" + start + "' and check_date<='" + today + "'");
        } else {
            sbSql.append("check_date>='" + start + "' and check_date<='" + end + "'");
        }

        if (null != orgType && "100".equals(orgType)) {
            List<String[]> svs = OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), svOrgName);
            List<String[]> shops = OptionsServices.services.getCodeByOrgId(svOrgName, shopcode);
            if (svNull) {
                StringBuffer sbSv = new StringBuffer();
                for (int i = 0; i < svs.size(); i++) {
                    sbSv.append(svs.get(i)[0]).append(",");
                }
                String strSv = sbSv.toString();
                if (strSv.endsWith(","))
                    strSv = strSv.substring(0, strSv.length() - 1);
                sbSql.append(" and org_id in(").append(strSv).append(")");
            } else {
                sbSql.append(" and org_id=").append(svOrgName);
            }

            if (!shopNull) {
                sbSql.append(" and store_code='" + shopcode + "'");
            }

            setAttr("svs", svs);
            setAttr("shops", shops);
        } else {
            sbSql.append(" and org_id=" + account.getBigDecimal("ORG_ID"));
            if (!shopNull) {
                sbSql.append(" and store_code='" + shopcode + "'");
            }

            setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
        }

        sbSql.append(" order by check_head_id desc");

        List<CuxStoreCheckHead> records = CuxStoreCheckHead.dao.find(sbSql.toString());
        setAttr("start", start);
        setAttr("end", end);
        setAttr("records", records);

        render("/WEB-INF/templates/default/inspection/history.ftl");
    }
}
