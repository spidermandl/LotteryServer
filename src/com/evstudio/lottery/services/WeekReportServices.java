package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.SvWorkReportHead;
import com.evstudio.lottery.pojos.SvWorkReportLine;
import com.jfinal.plugin.activerecord.Db;


/**
 * Created by ericren on 14-10-14.
 */
public class WeekReportServices {
    public static WeekReportServices services = new WeekReportServices();

    /**
     * @return
     */
    public int getSvWorkReportHeadNext() {
        return Db.queryBigDecimal("select sv_work_report_head_s.nextval from dual")
                .intValue();
    }
    
    /**
     * @return
     */
    public int getSvWorkReportLineNext() {
        return Db.queryBigDecimal("select sv_work_report_line_s.nextval from dual")
                .intValue();
    }

    public SvWorkReportLine getLineByLineId(String lineId) {
        return SvWorkReportLine.dao.findById(Integer.parseInt(lineId));
    }

    public SvWorkReportHead getHeadByHeadId(String headid) {
        return SvWorkReportHead.dao.findById(Integer.parseInt(headid));
    }
}
