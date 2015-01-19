package com.evstudio.lottery.services;

import com.evstudio.lottery.pojos.TShopCheckRecord;
import com.jfinal.plugin.activerecord.Db;


public class CheckRecordServices {
    public static CheckRecordServices services = new CheckRecordServices();

    /**
     * @return
     */
    public int getTShopCheckRecordNext() {
        return Db.queryBigDecimal("select t_shop_check_record_s.nextval from dual")
                .intValue();
    }


    public TShopCheckRecord getTShopCheckRecordById(String id) {
        return TShopCheckRecord.dao.findById(Integer.parseInt(id));
    }

}
