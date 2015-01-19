package com.evstudio.lottery.services;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.ScmOrgRelation;
import com.evstudio.lottery.pojos.SvShopProrityLine;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by ericren on 14-10-14.
 */
public class ShopServices {
    public static ShopServices services = new ShopServices();

    public String getShopNameByCode(String shopCode ){
        String str = "";
        List<ScmOrgRelation> list = null;
        list = ScmOrgRelation.dao.find("select * from scm_org_relation where org_code = ?", shopCode);
        if( null != list && list.size() == 1){
            ScmOrgRelation sor = list.get(0);
            str = sor.getStr("ORG_NAME");
        }

        return str;
    }

    public String getPriorityByShopCode(String shopCode){
//        return getPriorityByShopCodeNMonth( shopCode, Util.getYxjMonthByXslMonth(Util.getThisXslMonth()));
        return "";
    }

    public String getPriorityByShopCodeNMonth(String shopCode, String month ){
        String strPriority = "0";
        List<SvShopProrityLine> list = null;
        String strSql = "select line.* from sv_shop_prority_head head, sv_shop_prority_line line where " +
                "head.apply_number = line.apply_number " +
                "and " +
                " head.attribute3 = ? and line.attribute1 = 'Y' " +
                "and line.shop_code = ?";
        list = SvShopProrityLine.dao.find(strSql,month,shopCode);
        if( null != list && list.size() > 0  ){
            SvShopProrityLine line = list.get(0);
            strPriority = line.getStr("PRORITY_NEW");
        }else{
            strSql = "select prority_order from SV_SHOP_SALES_MSG where shop_code = ? and period = ?";
            List<String> listPriority = null;
            listPriority = Db.query(strSql, shopCode, month);
            if( null != listPriority && listPriority.size() > 0  ){
                strPriority = listPriority.get(0);
            }
        }

        return strPriority;
    }

}
