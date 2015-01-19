package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.CuxStoreCheckHead;
import com.evstudio.lottery.pojos.CuxStoreCheckLine;
import com.jfinal.plugin.activerecord.Db;


/**
 * Created by ericren on 14-10-14.
 */
public class InspectionServices {
    public static InspectionServices services = new InspectionServices();

    /**
     * @return
     */
    public int getCuxStoreCheckHeadNext() {
        return Db.queryBigDecimal("select cux_store_check_head_s.nextval from dual")
                .intValue();
    }
    
    /**
     * @return
     */
    public int getCuxStoreCheckLineNext() {
        return Db.queryBigDecimal("select cux_store_check_line_s.nextval from dual")
                .intValue();
    }

    public CuxStoreCheckLine getLineByLineId(String lineId) {
        return CuxStoreCheckLine.dao.findById(Integer.parseInt(lineId));
    }

    public CuxStoreCheckHead getHeadByHeadId(String headid) {
        return CuxStoreCheckHead.dao.findById(Integer.parseInt(headid));
    }
}
