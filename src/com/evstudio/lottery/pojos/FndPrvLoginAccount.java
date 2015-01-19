package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-10-01.
 *
 * 用户账号表
 *
 *
 * Snapshot on 2014-10-14 08:19:07

 Schema: M6MIS


 FND_PRV_LOGIN_ACCOUNT

 Field	Type	Allow Null	Default Value
 ACCOUNT_ID	NUMBER(15,0)	No
 LOGIN_NAME	VARCHAR2(255BYTE)	No
 LOGIN_PWD	VARCHAR2(255BYTE)	No
 STATUS	NUMBER(5,0)	No	0
 DESCRIPTION	VARCHAR2(200BYTE)	Yes
 REGION_ID	NUMBER	Yes
 ORG_ID	NUMBER	Yes
 PWD_UPDATE_DATE	DATE	Yes
 AGENT_ID	NUMBER	Yes
 EBS_USER_ID	NUMBER	Yes
 TYPE	NUMBER	Yes
 VENDOR_ID	NUMBER	Yes
 MOBIL_NO	VARCHAR2(2000BYTE)	Yes
 INITIAL_FLAG	VARCHAR2(1BYTE)	Yes	'Y'
 MUST_CHANGE_PWD_FLAG	VARCHAR2(1BYTE)	Yes	'Y'
 MANDT	VARCHAR2(9BYTE)	Yes
 LIFNR	VARCHAR2(30BYTE)	Yes
 BWKEY	VARCHAR2(100BYTE)	Yes
 EMPLOYEE_ID	NUMBER	Yes
 ORG_TYPE	VARCHAR2(30BYTE)	Yes
 ERP_USER	VARCHAR2(300BYTE)	Yes
 CC_USER	VARCHAR2(20BYTE)	Yes
 CREATION_DATE	DATE	Yes	sysdate
 */
public class FndPrvLoginAccount extends Model<FndPrvLoginAccount> {
    public static FndPrvLoginAccount dao = new FndPrvLoginAccount();
}
