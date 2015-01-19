package com.evstudio.lottery.services;

import com.evstudio.lottery.common.Util;
import com.evstudio.lottery.pojos.CuxStoreCheckHead;
import com.evstudio.lottery.pojos.CuxXslHead;
import com.evstudio.lottery.pojos.CuxXslLine;
import com.evstudio.lottery.pojos.FndPrvLoginAccount;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ericren on 14-10-01.
 */
public class CuxServices {
    public static CuxServices services = new CuxServices();

    public List<CuxXslLine> getCuxLineByAccount(FndPrvLoginAccount account) {
        List<CuxXslLine> list = null;
        int org_id = account.getBigDecimal("ORG_ID").intValue();
        list = CuxXslLine.dao.find("select * from cux_xsl_line where create_by = ?", org_id);
        if (null != list)
            for (int i = 0; i < list.size(); i++) {
                CuxXslLine line = list.get(i);
                line.set("ATTRIBUTE_9", "");
                line.set("ATTRIBUTE_10", "");
                String workTime = line.getStr("STORE_SJ");
                if (null != workTime)
                    try {
                        String[] workTimes = workTime.split("-");
                        if (workTimes.length > 1) {
                            line.set("ATTRIBUTE_9", workTimes[0].replaceAll(" ", "").replaceAll("：", ":"));
                            line.set("ATTRIBUTE_10", workTimes[1].replaceAll(" ", "").replaceAll("：", ":"));
                        }
                    } catch (Exception e) {
                        System.out.println(workTime);
                        e.printStackTrace();
                    }
                list.set(i, line);
            }
        return list;
    }

    public List<CuxXslLine> getCuxLineByOrgId(String orgId) {
        List<CuxXslLine> list = null;
        list = CuxXslLine.dao.find("select * from cux_xsl_line where create_by=" + orgId);
        if (null != list)
            for (int i = 0; i < list.size(); i++) {
                CuxXslLine line = list.get(i);
                line.set("ATTRIBUTE_9", "");
                line.set("ATTRIBUTE_10", "");
                String workTime = line.getStr("STORE_SJ");
                if (null != workTime)
                    try {
                        String[] workTimes = workTime.split("-");
                        if (workTimes.length > 1) {
                            line.set("ATTRIBUTE_9", workTimes[0].replaceAll(" ", "").replaceAll("：", ":"));
                            line.set("ATTRIBUTE_10", workTimes[1].replaceAll(" ", "").replaceAll("：", ":"));
                        }
                    } catch (Exception e) {
                        System.out.println(workTime);
                        e.printStackTrace();
                    }
                list.set(i, line);
            }
        return list;
    }

    public List<CuxStoreCheckHead> getCuxHeadByAccount(FndPrvLoginAccount account) {
        List<CuxStoreCheckHead> list = null;
        String sql = "select * from cux_store_check_head where USER_ID = ? order by check_head_id desc";
        list = CuxStoreCheckHead.dao.find(sql, account.getStr("LOGIN_NAME"));

        return list;
    }

    public CuxXslHead getXslHeadByAccountNMonth(FndPrvLoginAccount account, String month) {
        CuxXslHead head = null;
        String sql = "select * from cux_xsl_head where xsl_y = ? and role_id = ?";

        head = CuxXslHead.dao.findFirst(sql, month, account.getBigDecimal("ORG_ID"));
        return head;
    }

    public CuxXslHead getThisMonth(FndPrvLoginAccount account) {
        CuxXslHead head = null;
        String thisMonth = "";//Util.getThisXslMonth();
        head = getXslHeadByAccountNMonth(account, thisMonth);
        if (null == head) {
            head = new CuxXslHead();
            head.set("ROLE_ID", account.getBigDecimal("ORG_ID"));
            head.set("ATTRIBUTE_2", account.getStr("LOGIN_NAME"));
            head.set("ATTRIBUTE_1", "N");
            head.set("CREATE_BY", account.getBigDecimal("ACCOUNT_ID"));
            head.set("UPDATE_BY", account.getBigDecimal("ACCOUNT_ID"));
            head.set("XSL_Y", thisMonth);
            head.set("XSL_ID", getCuxXslHeadNext());
            head.set("CREATE_DATE", new Timestamp(System.currentTimeMillis()));
            head.set("UPDATE_DATE", new Timestamp(System.currentTimeMillis()));

            head.save();
        }

        return head;
    }

    public int getCuxXslHeadNext() {
        return Db.queryBigDecimal("select CUX_XSL_HEAD_S.nextval from dual")
                .intValue();
    }

    public int getCuxXslLineNext() {
        return Db.queryBigDecimal("select CUX_XSL_LINE_S.nextval from dual")
                .intValue();
    }

    public int getCuxXslCountByPriority(FndPrvLoginAccount account, int headId, int priority ){
        int count = 0;
        String sql = "select count(*) from ";

        count = Db.queryInt(sql);

        return count;
    }
}
