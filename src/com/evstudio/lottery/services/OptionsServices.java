package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.ScmOrgRelation;
import com.evstudio.lottery.pojos.SvShopProrityHead;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericren on 14-10-17.
 */
public class OptionsServices {
    public static OptionsServices services = new OptionsServices();

    /**
     * 通过SV获取店铺ID的Options
     * 也可以SM通过ID获取下属SV的Options
     *
     * @param orgId
     * @param selected
     * @return
     */
    public ArrayList<String[]> getCodeByOrgId(String orgId, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select * from SCM_ORG_RELATION where parent_id = ?";
        List<ScmOrgRelation> listQuery = ScmOrgRelation.dao.find(sql, orgId);

        if (null != listQuery && listQuery.size() > 0) {
            for (int i = 0; i < listQuery.size(); i++) {
                String[] strs = new String[3];
                ScmOrgRelation dto = listQuery.get(i);
                if (dto.getBigDecimal("TYPE_LEVEL").intValue() == 200) {
                    strs[0] = String.valueOf(dto.getBigDecimal("ORG_ID"));
                } else {
                    strs[0] = dto.getStr("ORG_CODE");
                }
                strs[1] = dto.getStr("ORG_NAME");
                if (!"".equals(selected) && strs[0].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                list.add(strs);
            }
        }

        return list;
    }

    public ArrayList<String[]> getSmCodeByOrgId(String orgId, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select * from SCM_ORG_RELATION where parent_id = ?";
        List<ScmOrgRelation> listQuery = ScmOrgRelation.dao.find(sql, orgId);

        if (null != listQuery && listQuery.size() > 0) {
            for (int i = 0; i < listQuery.size(); i++) {
                String[] strs = new String[3];
                ScmOrgRelation dto = listQuery.get(i);
                if (dto.getBigDecimal("TYPE_LEVEL").intValue() == 200) {
                    strs[0] = String.valueOf(dto.getBigDecimal("ORG_ID"));
                } else {
                    strs[0] = dto.getStr("ORG_CODE");
                }
                strs[1] = dto.getStr("ORG_NAME");
                if (!"".equals(selected) && strs[1].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                list.add(strs);
            }
        }

        return list;
    }

    /**
     * 通过SV获取店铺ID的Options
     * 也可以SM通过ID获取下属SV的Options
     *
     * @param orgId
     * @param selected
     * @return
     */
    public ArrayList<String[]> getCodeByOrgIdAndSelectedByName(String orgId, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select * from SCM_ORG_RELATION where parent_id = ?";
        List<ScmOrgRelation> listQuery = ScmOrgRelation.dao.find(sql, orgId);

        if (null != listQuery && listQuery.size() > 0) {
            for (int i = 0; i < listQuery.size(); i++) {
                String[] strs = new String[3];
                ScmOrgRelation dto = listQuery.get(i);
                if (dto.getBigDecimal("TYPE_LEVEL").intValue() == 200) {
                    strs[0] = String.valueOf(dto.getBigDecimal("ORG_ID"));
                } else {
                    strs[0] = dto.getStr("ORG_CODE");
                }
                strs[1] = dto.getStr("ORG_NAME");
                if (!"".equals(selected) && strs[1].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                list.add(strs);
            }
        }

        return list;
    }

    /**
     * 获取会计月Options
     *
     * @param svName
     * @param selected
     * @return
     */
    public ArrayList<String[]> getPriorityMonthBySvName(String svName, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select distinct attribute3 from sv_shop_prority_head where attribute3 is not null order by attribute3 desc";
//        List<String> listQuery = Db.query(sql, svName);
        List<SvShopProrityHead> headlist = SvShopProrityHead.dao.find(sql);
        if (null != headlist && headlist.size() > 0)
            for (int i = 0; i < headlist.size(); i++) {
                String[] strs = new String[3];
                strs[0] = headlist.get(i).getStr("ATTRIBUTE3");
                strs[1] = headlist.get(i).getStr("ATTRIBUTE3");
                if (!"".equals(selected) && strs[0].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                list.add(strs);
            }

        return list;
    }

    /**
     * 获取会计月Options
     *
     * @param smCode
     * @param selected
     * @return
     */
    public ArrayList<String[]> getPriorityMonthBySMName(String smCode, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select distinct period from sv_shop_sales_msg where sv in " +
                "(select org_name from SCM_ORG_RELATION where parent_id = ?)" +
                " order by period desc";
        List<String> listQuery = Db.query(sql, smCode);
        if (null != listQuery && listQuery.size() > 0)
            for (int i = 0; i < listQuery.size(); i++) {
                String[] strs = new String[3];
                strs[0] = listQuery.get(i);
                strs[1] = listQuery.get(i);
                if (!"".equals(selected) && strs[0].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                list.add(strs);
            }

        return list;
    }

    public ArrayList<String[]> getPriorityMonthByShopCode(String shopCode, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select distinct period from sv_shop_sales_msg where shop_code = ? order by period desc";
        List<String> listQuery = Db.query(sql, shopCode);
        if (null != listQuery && listQuery.size() > 0)
            for (int i = 0; i < listQuery.size(); i++) {
                String[] strs = new String[3];
                strs[0] = listQuery.get(i);
                strs[1] = listQuery.get(i);
                if (!"".equals(selected) && strs[0].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                list.add(strs);
            }

        return list;
    }

    public ArrayList<String[]> getCodeByOrgIdWithPriority(String orgId, String selected) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        String sql = "select * from SCM_ORG_RELATION where parent_id = ?";
        List<ScmOrgRelation> listQuery = ScmOrgRelation.dao.find(sql, orgId);

        if (null != listQuery && listQuery.size() > 0) {
            for (int i = 0; i < listQuery.size(); i++) {
                String[] strs = new String[4];
                ScmOrgRelation dto = listQuery.get(i);
                if (dto.getBigDecimal("TYPE_LEVEL").intValue() == 200) {
                    strs[0] = String.valueOf(dto.getBigDecimal("ORG_ID"));
                } else {
                    strs[0] = dto.getStr("ORG_CODE");
                }
                strs[1] = dto.getStr("ORG_NAME");
                if (!"".equals(selected) && strs[0].equals(selected))
                    strs[2] = "selected";
                else
                    strs[2] = "";

                strs[3] = ShopServices.services.getPriorityByShopCode(strs[0]);

                list.add(strs);
            }
        }

        return list;
    }
}
