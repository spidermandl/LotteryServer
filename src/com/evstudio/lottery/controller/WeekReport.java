package com.evstudio.lottery.controller;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.*;
import com.evstudio.lottery.services.OptionsServices;
import com.evstudio.lottery.services.ShopServices;
import com.evstudio.lottery.services.WeekReportServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jfinal.core.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericren on 14-11-3.
 */
public class WeekReport extends Controller {
    public void index(){

    }

    public void thisWeek(){
        FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
        String month = Util.getThisXslMonth();
        List<String[]> months = OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), "");
        if (months.size() > 0) {
        	month = months.get(0)[0];
        }
        
        String sql = "select *from sv_work_report_head where ORG_ID='"+account.getBigDecimal("ORG_ID")+"' and C_MOUTH='"+month+"'";
//        String sql = "select *from sv_work_report_head where ORG_ID='"+account.getBigDecimal("ORG_ID")+"' and C_MOUTH='2014-08'";
        
        List<Integer> reportnum = new ArrayList<Integer>();
        List<SvWorkReportHead> reportlist = new ArrayList<SvWorkReportHead>();
        List<SvWorkReportHead> headList = SvWorkReportHead.dao.find(sql);
        
        for (int i=0; i < headList.size(); i++) {
        	SvWorkReportHead head = headList.get(i);
        	sql = "select *from sv_work_report_line where APPLY_NUMBER='"+head.get("APPLY_NUMBER")+"' and END_DATE is null";
        	List<SvWorkReportLine> lineList = SvWorkReportLine.dao.find(sql);
        	if (0 != lineList.size()){
        		reportlist.add(head);
        		reportnum.add(lineList.size());
        	}
        }
        
        setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
        setAttr("months", OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), ""));
        setAttr("reportlist", reportlist);
        setAttr("reportnum", reportnum);
        
        render("/WEB-INF/templates/default/week/weekReport.ftl");
    }
    
    public void reportAdd(){
    	
    	String month = Util.getThisXslMonth();
    	String shop = getPara("shop");
    	if (null != shop && shop.contains(",")) {
    		shop = shop.split(",")[0];
    	}
    	FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
    	setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shop));
    	setAttr("month", month);
    	
    	String sql = "select *from sv_work_report_head where shop_code='" + shop + "' and c_mouth='" + month + "'";
    	List<SvWorkReportHead> headList = SvWorkReportHead.dao.find(sql);
    	if (headList.size() != 0) {
			if (null == headList.get(0).getStr("SHOP_DJ"))
				setAttr("shoplevel", ShopServices.services.getPriorityByShopCode(shop));
			else
    			setAttr("shoplevel", headList.get(0).getStr("SHOP_DJ"));
    		setAttr("pgdate", headList.get(0).getStr("ATTRIBUTE_2"));
    	}
    	
    	sql = "select distinct(D) from loook_t";
    	List<LoookT> list = LoookT.dao.find(sql);
    	
    	StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i ++) {
			sb.append(list.get(i).getStr("D")).append(":").append(list.get(i).getStr("D"));
			if (i != list.size() - 1) {
				sb.append(";");
			}
		}
		setAttr("pros", sb.toString());
		if (list.size()!=0) {
			sql = "select *from loook_t where D='" + list.get(0).getStr("D") + "'";
			list = LoookT.dao.find(sql);
			sb = new StringBuffer();
			for (int i = 0; i < list.size(); i ++) {
				sb.append(list.get(i).getStr("H")).append(":").append(list.get(i).getStr("H"));
				if (i != list.size() - 1) {
					sb.append(";");
				}
			}
		}
		setAttr("prodetails", sb.toString());
    	
    	render("/WEB-INF/templates/default/week/weekReportAdd.ftl");
    }


	public void reportDetail(){

		String month = getPara("month");
		String shopcode = getPara("shop");
		String sql = "select *from scm_org_relation where org_code='" + shopcode + "'";
		ScmOrgRelation shop = ScmOrgRelation.dao.findFirst(sql);

		FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
		setAttr("shop", shop);
		setAttr("month", month);

		sql = "select *from sv_work_report_head where shop_code='" + shopcode + "' and c_mouth='" + month + "'";
		List<SvWorkReportHead> headList = SvWorkReportHead.dao.find(sql);
		if (headList.size() != 0) {
			if (null == headList.get(0).getStr("SHOP_DJ"))
				setAttr("shoplevel", ShopServices.services.getPriorityByShopCode(shopcode));
			else
				setAttr("shoplevel", headList.get(0).getStr("SHOP_DJ"));
			setAttr("pgdate", headList.get(0).getStr("ATTRIBUTE_2"));

			sql = "select *from sv_work_report_line where apply_number='" + headList.get(0).get("APPLY_NUMBER") + "' order by line_id asc";
			List<SvWorkReportLine> lineList = SvWorkReportLine.dao.find(sql);
			setAttr("records",lineList);
		}

		render("/WEB-INF/templates/default/week/weekReportDetail.ftl");
	}
    
    public void getpros(){
    	String sql = "select * from loook_t where D='" + getPara("pro") + "'";
    	List<LoookT> list = LoookT.dao.find(sql);
    	
    	StringBuffer sb = new StringBuffer();
    	
		for (int i = 0; i < list.size(); i ++) {
			sb.append("<option value='").append(list.get(i).getStr("H")).append("'>").append(list.get(i).getStr("H")).append("</option>");
		}
    	
    	renderText(sb.toString());
    }
    
    public void saveHead(){
    	FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
    	Timestamp tsNow = new Timestamp(System.currentTimeMillis());
    	String shop = getPara("shop");
    	String[] shops = shop.split(",");
    	String month = getPara("month");
    	//String shoplevel = getPara("shoplevel");
    	String pgdate = getPara("pgdate");
    	String sql = "select *from sv_work_report_head where shop_code='" + shops[0] + "' and c_mouth='" + month + "'";
    	SvWorkReportHead head = SvWorkReportHead.dao.findFirst(sql);
    	if (head != null) {
    		//head.set("SHOP_DJ", shoplevel);
    		head.set("ATTRIBUTE_2", pgdate);
    		head.update();
    	} else {
    		head = new SvWorkReportHead();
    		head.set("APPLY_NUMBER", WeekReportServices.services.getSvWorkReportHeadNext());
    		head.set("ORG_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
    		head.set("CREATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
    		head.set("LAST_UPDATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
    		head.set("CREATION_DATE", tsNow);
            head.set("LAST_UPDATE_DATE", tsNow);
            head.set("ATTRIBUTE_2", pgdate);
            head.set("C_MOUTH", month);
            head.set("SHOP_CODE", shops[0]);
            head.set("SHOP_NAME", shops[1]);
//            head.set("SHOP_DJ", shoplevel);
            
            head.save();
    	}
    	reportAdd();
    }
    
    public void operJsondata(){
        Timestamp tsNow = new Timestamp(System.currentTimeMillis());
        String month = Util.getThisXslMonth();
        
    	FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
    	CuxSvOrgMapping orgMapping = (CuxSvOrgMapping) getSessionAttr("orgmapping");
    	String oper = getPara("oper");
    	
    	String shop = getPara("shop");
    	String[] shops = shop.split(",");
    	String sql = "select *from sv_work_report_head where shop_code='" + shops[0] + "' and c_mouth='" + month + "'";
    	SvWorkReportHead head = SvWorkReportHead.dao.findFirst(sql);
    	int headid = 0;
    	boolean isHeadExsit = false;
    	if (head != null) {
    		headid = head.getBigDecimal("APPLY_NUMBER").intValue();
    		isHeadExsit = true;
//    		head.set("SHOP_DJ", shoplevel);
//    		head.set("ATTRIBUTE_2", pgdate);
//    		head.update();
    	}
    	
        if (oper.equals("del")) {
        	String id = getPara("id");
        	SvWorkReportLine.dao.deleteById(id);
        }
        else if (oper.equals("add")) {
        	if (null == head) {
        		headid = WeekReportServices.services.getSvWorkReportHeadNext();
        		head = new SvWorkReportHead();
        		head.set("APPLY_NUMBER", headid);
        		head.set("ORG_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
        		head.set("CREATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
        		head.set("LAST_UPDATED_BY", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
        		head.set("CREATION_DATE", tsNow);
                head.set("LAST_UPDATE_DATE", tsNow);
                head.set("C_MOUTH", month);
                head.set("SHOP_CODE", shops[0]);
                head.set("SHOP_NAME", ShopServices.services.getShopNameByCode(shops[0]));
                
                isHeadExsit = head.save();
        	}
        	
        	if (isHeadExsit) {
        		SvWorkReportLine line = new SvWorkReportLine();
        		line.set("LINE_ID", WeekReportServices.services.getSvWorkReportLineNext());
        		
        		line.set("APPLY_NUMBER", String.valueOf(headid));
        		line.set("QUESTION_VALUE", getPara("questionValue"));
        		line.set("QUESTION_DESCRIBE", getPara("questionDescribe"));
        		line.set("SOVLE_CASE", getPara("sovle"));
        		line.set("NEED_RESOURCE", getPara("needResource"));
        		line.set("USER_ID", String.valueOf(account.getBigDecimal("ACCOUNT_ID").intValue()));
        		line.set("ROLE_ID", orgMapping.getStr("ROLE_ID"));
        		line.set("ORG_ID", String.valueOf(account.getBigDecimal("ORG_ID").intValue()));
        		line.set("CREATION_DATE", tsNow);
        		line.set("CREATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
        		line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
        		line.set("LAST_UPDATE_DATE", tsNow);
        		line.set("ATTRIBUTE11", getPara("attr11"));
        		line.set("ATTRIBUTE12", getPara("attr12"));
        		line.set("ATTRIBUTE13", getPara("attr13"));
        		line.set("ATTRIBUTE14", getPara("attr14"));
        		line.set("ATTRIBUTE15", getPara("attr15"));
        		line.set("STORE_RESULT", getPara("storeResult"));
        		line.set("END_DATE", getPara("sdate"));
        		line.save();
        	}
        	
        }
        else if (oper.equals("edit")) {
        	String id = getPara("id");
        	
    		SvWorkReportLine line = SvWorkReportLine.dao.findById(id);
    		
    		line.set("QUESTION_VALUE", getPara("questionValue"));
    		line.set("QUESTION_DESCRIBE", getPara("questionDescribe"));
    		line.set("SOVLE_CASE", getPara("sovle"));
    		line.set("NEED_RESOURCE", getPara("needResource"));
    		line.set("LAST_UPDATED_BY", account.getBigDecimal("ACCOUNT_ID").intValue());
    		line.set("LAST_UPDATE_DATE", tsNow);
    		line.set("ATTRIBUTE11", getPara("attr11"));
    		line.set("ATTRIBUTE12", getPara("attr12"));
    		line.set("ATTRIBUTE13", getPara("attr13"));
    		line.set("ATTRIBUTE14", getPara("attr14"));
    		line.set("ATTRIBUTE15", getPara("attr15"));
    		line.set("STORE_RESULT", getPara("storeResult"));
    		line.set("END_DATE", getPara("sdate"));
    		line.update();
        	
        	
        }
        else{
        	// todo
        }
    	render("/week/reportAdd");
    }
    
    public void getJsondata(){
    	
    	JsonObject jsonObj = new JsonObject();
    	// 根据jqGrid对JSON的数据格式要求给jsonObj赋值
    	jsonObj.addProperty("page", 1);                // 当前页
    	
    	String shop = getPara("shop");
    	if (shop.contains(",")) {
    		shop = shop.split(",")[0];
    	}
    	String month = Util.getThisXslMonth();
    	
    	String sql = "select *from sv_work_report_head where shop_code='" + shop + "' and c_mouth='" + month + "'";
    	List<SvWorkReportHead> headList = SvWorkReportHead.dao.find(sql);
    	if (headList.size() == 0) {
    		jsonObj.addProperty("records", 0);
    	}
    	else {
    		sql = "select *from sv_work_report_line where apply_number='" + headList.get(0).get("APPLY_NUMBER") + "' order by line_id asc";
    		List<SvWorkReportLine> lineList = SvWorkReportLine.dao.find(sql);
    		JsonArray rows = new JsonArray();
    		for (int i=0;i<lineList.size();i++) {
    			SvWorkReportLine line = lineList.get(i);
    			
				JsonObject cell = new JsonObject();
				cell.addProperty("id", line.getNumber("LINE_ID"));
				cell.addProperty("questionValue", line.getStr("QUESTION_VALUE"));
				cell.addProperty("questionDescribe", line.getStr("QUESTION_DESCRIBE"));
				if (line.getStr("ATTRIBUTE11").equals("Y")) {
					cell.addProperty("attr11", "是");
				}
				else if (line.getStr("ATTRIBUTE11").equals("N")) {
					cell.addProperty("attr11", "否");
				}
				//cell.addProperty("attr11", line.getStr("ATTRIBUTE11"));
				cell.addProperty("sovle", line.getStr("SOVLE_CASE"));
				cell.addProperty("needResource", line.getStr("NEED_RESOURCE"));
				cell.addProperty("storeResult", line.getStr("STORE_RESULT"));
				cell.addProperty("attr13", line.getStr("ATTRIBUTE13"));
				cell.addProperty("attr14", line.getStr("ATTRIBUTE14"));
				cell.addProperty("attr15", line.getStr("ATTRIBUTE15"));
				cell.addProperty("attr12", line.getStr("ATTRIBUTE12"));
				cell.addProperty("sdate", line.getStr("END_DATE"));
				rows.add(cell);
    			
    		}
    		jsonObj.add("rows", rows);
    		jsonObj.addProperty("records", rows.size());
    	}
    	

        // 自控制台打印输出，以检验json对象生成是否正确
        System.out.println("要返回的json对象：\n" + jsonObj.toString());
        
    	renderJson(jsonObj.toString());
    }
    
    public void testAdd(){
    	FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
    	setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), ""));
    	setAttr("months", OptionsServices.services.getPriorityMonthBySvName(account.getStr("LOGIN_NAME"), ""));
    	
    	String sql = "select distinct(D) from loook_t";
    	List<LoookT> list = LoookT.dao.find(sql);
    	setAttr("prolsit", list);
    	if (list.size() > 0) {
    		sql = "select *from loook_t where D='" + list.get(0) + "'";
    		list = LoookT.dao.find(sql);
    		setAttr("proDetaillsit", list);
    	}
    	
    	render("/WEB-INF/templates/default/week/jqgrid.html");
    }
    
    public void getDetailByPro(){
    	String pro = getPara("pro");
    	String sql = "select *from loook_t where D='" + pro + "'";
    	List<LoookT> list = LoookT.dao.find(sql);
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i < list.size(); i ++) {
    		LoookT t = list.get(i);
    		if (null != t.getStr("H")) {
    			sb.append("<option value=\"" + t.getStr("H") + "\">").append(t.getStr("H")).append("</option>");
    		}
    	}
    	renderText(sb.toString());
    }

    public void reportHistory(){
    	FndPrvLoginAccount account = (FndPrvLoginAccount) getSessionAttr("account");
		String orgType = account.getStr("ORG_TYPE");

		String svOrgName = getPara("svorgname");
		String shopcode = getPara("shopcode");
    	String month = getPara("searchmonth");
		List<CuxSxlMonth> months = Util.getXslMonthList();

		boolean svNull = (null == svOrgName || "".equals(svOrgName));
		boolean shopNull = (null == shopcode || "".equals(shopcode));
		boolean monthNull = (null == month || "".equals(month));

    	StringBuffer sbSql = new StringBuffer();
    	sbSql.append("select *from sv_work_report_head where ");


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
				sbSql.append("org_id in(").append(strSv).append(")");
			} else {
				sbSql.append("org_id='").append(svOrgName).append("'");
			}

			if (!shopNull) {
				sbSql.append(" and shop_code='" + shopcode + "'");
			}

			setAttr("svs", svs);
			setAttr("shops", shops);
		} else {
			sbSql.append("org_id='" + account.getBigDecimal("ORG_ID")).append("'");
			if (!shopNull) {
				sbSql.append(" and shop_code='" + shopcode + "'");
			}

			setAttr("shops", OptionsServices.services.getCodeByOrgId(String.valueOf(account.getBigDecimal("ORG_ID")), shopcode));
		}


    	if (!monthNull) {
    		sbSql.append(" and C_MOUTH='").append(month).append("'");
    	}
    	sbSql.append(" order by apply_number desc");

        List<SvWorkReportHead> headList = SvWorkReportHead.dao.find(sbSql.toString());
        
        setAttr("months", months);
        setAttr("month", month);
        setAttr("reportlist", headList);
        
        render("/WEB-INF/templates/default/week/weekReportHistory.ftl");
    }

    public void reportSearch(){

        renderText("");
    }

    public void doThisWeek(){
        renderText("");
    }
}
