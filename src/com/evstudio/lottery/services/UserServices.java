package com.evstudio.lottery.services;

import com.evstudio.lottery.common.MD5Util;
import com.evstudio.lottery.pojos.CuxSvOrgMapping;
import com.evstudio.lottery.pojos.FndPrvLoginAccount;
import com.evstudio.lottery.pojos.ScmOrgRelation;

import java.util.List;

/**
 * Created by ericren on 14-10-01.
 */
public class UserServices {
    public static UserServices services = new UserServices();

    /**
     *
     * @param username
     * @param passwd
     * @return
     */
    public FndPrvLoginAccount login(String username, String passwd) {
        FndPrvLoginAccount dto = null;
        List<FndPrvLoginAccount> list = null;

        list = FndPrvLoginAccount.dao.find("select * from fnd_prv_login_account " +
                        "where login_name = ? and login_pwd = ?",
                username, MD5Util.MD5(passwd));
        if (null != list && list.size() == 1) {
            dto = list.get(0);
        }

        return dto;
    }

    /**
     * 获取用户组织
     *
     * @param user
     * @return
     */
    public ScmOrgRelation getOrgByUser(FndPrvLoginAccount user) {
        ScmOrgRelation dto = null;
        List<ScmOrgRelation> list = null;
        list = ScmOrgRelation.dao.find("select * from scm_org_relation where org_id = ?",
                user.getBigDecimal("ORG_ID"));
        if (null != list && list.size() == 1) {
            dto = list.get(0);
        }

        return dto;
    }

    /**
     * 得到父级对象组织
     *
     * @param user
     * @return
     */
    public FndPrvLoginAccount getParentByUser(FndPrvLoginAccount user) {
        FndPrvLoginAccount dto = null;
        List<FndPrvLoginAccount> list = null;
        list = FndPrvLoginAccount.dao.find("select * from fnd_prv_login_account where org_id = " +
                        "(select parent_id from scm_org_relation where org_id = ?)",
                user.getBigDecimal("ORG_ID"));
        if (null != list && list.size() == 1) {
            dto = list.get(0);
        }

        return dto;
    }

    public CuxSvOrgMapping getOrgMappingByUser(FndPrvLoginAccount user) {
        CuxSvOrgMapping dto = null;
        List<CuxSvOrgMapping> list = null;
        String sql = "";
        String userType = user.getStr("ORG_TYPE");
        if (null != userType)
            if ("200".equals(userType)) {
                sql = "select * from cux_sv_org_mapping where org_id = " +
                        "(select parent_id from scm_org_relation where org_id = " +
                        "(select parent_id from scm_org_relation where org_id = ?))";
            } else if ("100".equals(userType)) {
                sql = "select * from cux_sv_org_mapping where org_id = " +
                        "(select parent_id from scm_org_relation where org_id = ?)";
            }
        list = CuxSvOrgMapping.dao.find(sql,
                user.getBigDecimal("ORG_ID"));
        if (null != list && list.size() == 1) {
            dto = list.get(0);
        }

        return dto;
    }
}
