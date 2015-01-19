package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.FndPrvLoginAccount;
import com.evstudio.lottery.pojos.SvShopProrityHead;
import com.evstudio.lottery.pojos.SvShopProrityLine;
import com.evstudio.lottery.pojos.SvShopSalesMsg;
import com.jfinal.plugin.activerecord.Db;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ericren on 14-10-14.
 */
public class PriorityServices {
    public static PriorityServices services = new PriorityServices();

    public List<SvShopSalesMsg> getShopSalesBySvName(String svname,String shopcode,String month) {
        List<SvShopSalesMsg> list = null;

        String sql = "select * from sv_shop_sales_msg where sv = ?";
        
      //是否有店铺选项
        if (null != shopcode && !"".equals(shopcode)) {
            sql = sql + " and shop_code = '" + shopcode + "'";
        }

        //是否有会计月选项
        if (null != month && !"".equals(month)) {
            sql = sql + " and period = '" + month + "'";
        }
        
        sql = sql + " order by period desc";
        
        list = SvShopSalesMsg.dao.find(sql,
                svname);

        return list;
    }

    public List<SvShopSalesMsg> getShopSalesBySMName(String smname,String shopcode,String month) {
        List<SvShopSalesMsg> list = null;

		String sql = "select * from sv_shop_sales_msg where sm = ?";

		// 是否有店铺选项
		if (null != shopcode && !"".equals(shopcode)) {
			sql = sql + " and sv = '" + shopcode + "'";
		}

		// 是否有会计月选项
		if (null != month && !"".equals(month)) {
			sql = sql + " and period = '" + month + "'";
		}

		sql = sql + " order by period desc";

		list = SvShopSalesMsg.dao.find(sql, smname);

        return list;
    }

    public List<SvShopSalesMsg> getShopSalesByCodeNMonth(String shopCode, String month, String svLoginName) {
        List<SvShopSalesMsg> list = null;
        String sql = "select * from sv_shop_sales_msg where 1=1 ";

        //是否有SV选项
        if (null != svLoginName && !"".equals(svLoginName)) {
            sql = sql + "and sv = '" + svLoginName + "'";
        }

        //是否有店铺选项
        if (null != shopCode && !"".equals(shopCode)) {
            sql = sql + "and shop_code = '" + shopCode + "'";
        }

        //是否有会计月选项
        if (null != month && !"".equals(month)) {
            sql = sql + "and period = '" + month + "'";
        }
        
        sql = sql + " order by period desc";

        list = SvShopSalesMsg.dao.find(sql);

        return list;
    }

    public List<SvShopSalesMsg> getSmShopSalesByCodeNMonth(String smOrgCode, String shopCode, String month, String svLoginName) {
        List<SvShopSalesMsg> list = null;
        String sql = "select * from sv_shop_sales_msg where 1=1 ";

        //是否有SV选项
        if (null != svLoginName && !"".equals(svLoginName)) {
            sql = sql + "and sv = (select org_name from SCM_ORG_RELATION where org_id = '" + svLoginName + "')";
        } else {
            sql = sql + "and sv in (select org_name from SCM_ORG_RELATION where parent_id = '" +
                    smOrgCode + "') ";
        }

        //是否有店铺选项
        if (null != shopCode && !"".equals(shopCode)) {
            sql = sql + "and shop_code = '" + shopCode + "'";
        }

        //是否有会计月选项
        if (null != month && !"".equals(month)) {
            sql = sql + "and period = '" + month + "'";
        }
        
        sql = sql + " order by period desc";

        list = SvShopSalesMsg.dao.find(sql);

        return list;
    }

    /**
     * @param code
     * @param month
     * @return
     */
    public String getNewPriorityByCodeNMonth(String code, String month) {
        String strReturn = "0";
        String sql = "select t.prority_new from SV_SHOP_PRORITY_LINE t, SV_SHOP_PRORITY_HEAD t1 " +
                "where t1.apply_number = t.apply_number and " +
                " t.shop_code = ? and " +
                " t1.attribute3 = ? and t.attribute1='Y' ";

        List<String> listQuery = Db.query(sql, code, month);

        if (null != listQuery && listQuery.size() == 1) {
            strReturn = listQuery.get(0);
        }
        return strReturn;
    }

    /**
     * @param ids
     * @return
     */
    public List<SvShopSalesMsg> getSaleMsgsByIds(String[] ids) {
        List<SvShopSalesMsg> list = null;
        if (null != ids && ids.length > 0) {
            String strIds = Arrays.toString(ids)
                    .replace("[", "'")
                    .replace("]", "'")
                    .replace(",", "','");
            System.out.println(strIds);
            //To do
            list = SvShopSalesMsg.dao.find("select * from sv_shop_sales_msg where sales_msg_number in (" +
                    strIds + ")");
        }

        return list;
    }

    /**
     * @return
     */
    public int getSvShopProrityHeadNext() {
        return Db.queryBigDecimal("select sv_shop_prority_head_s.nextval from dual")
                .intValue();
    }

    /**
     * @return
     */
    public int getSvShopProrityLineNext() {
        return Db.queryBigDecimal("select sv_shop_prority_line_s.nextval from dual")
                .intValue();
    }

    public List<SvShopProrityHead> getApplyProrityListByUser(FndPrvLoginAccount account) {
        List<SvShopProrityHead> list = null;
        String sql = "";
        String userType = account.getStr("ORG_TYPE");
        if (null != userType)
            if ("200".equals(userType)) {
                sql = "select * from sv_shop_prority_head where apply_person_id = " +
                        "(select account_id from fnd_prv_login_account where org_id = ? ) " +
                        "order by apply_time desc";
            } else if ("100".equals(userType)) {
                sql = "select * from sv_shop_prority_head where apply_person_id in " +
                        "(select account_id from fnd_prv_login_account where org_id in " +
                        "( select org_id from scm_org_relation where parent_id = ?) ) " +
                        "order by apply_time desc";
            }

        list = SvShopProrityHead.dao.find(sql, account.getBigDecimal("ORG_ID"));

        return list;
    }

    public List<SvShopProrityHead> getSmApplyProrityListByUser(FndPrvLoginAccount account) {
        List<SvShopProrityHead> list = null;
        String sql = "";
        String userType = account.getStr("ORG_TYPE");
        if (null != userType)
            if ("200".equals(userType)) {
                sql = "select * from sv_shop_prority_head where apply_person_id = " +
                        "(select account_id from fnd_prv_login_account where org_id = ? ) " +
                        "order by attribute3 desc,apply_time desc";
            } else if ("100".equals(userType)) {
                sql = "select * from sv_shop_prority_head where apply_person_id in " +
                        "(select account_id from fnd_prv_login_account where org_id in " +
                        "( select org_id from scm_org_relation where parent_id = ?) ) or apply_person_id in" +
                        " (select account_id from fnd_prv_login_account where org_id=" + account.getBigDecimal("ORG_ID") + ")" +
                        "order by attribute3 desc,apply_time desc";
            }

        list = SvShopProrityHead.dao.find(sql, account.getBigDecimal("ORG_ID"));

        return list;
    }

    public int getApplyLineCountByHead(SvShopProrityHead head) {
        int iReturn = 0;
        iReturn = Db.queryBigDecimal("select count(line_id) from sv_shop_prority_line where apply_number = ?",
                head.getBigDecimal("APPLY_NUMBER")).intValue();
        return iReturn;
    }

    public List<SvShopProrityLine> getApplyLineByHeadId(String headId) {
        List<SvShopProrityLine> list = null;
        list = SvShopProrityLine.dao.find("select * from sv_shop_prority_line where apply_number = ?", headId);
        return list;
    }

    public SvShopProrityLine getLineByLineId(String lineId) {
        return SvShopProrityLine.dao.findById(Integer.parseInt(lineId));
    }

    public SvShopProrityHead getHeadByHeadId(String headid) {
        return SvShopProrityHead.dao.findById(Integer.parseInt(headid));
    }
}
