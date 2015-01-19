package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-6-26.
 *
 * mysql> desc dictSyxw1;
 +---------+---------+------+-----+---------+----------------+
 | Field   | Type    | Null | Key | Default | Extra          |
 +---------+---------+------+-----+---------+----------------+
 | id      | int(11) | NO   | PRI | NULL    | auto_increment |
 | number1 | int(11) | YES  |     | NULL    |                |
 | number2 | int(11) | YES  |     | NULL    |                |
 | number3 | int(11) | YES  |     | NULL    |                |
 | number4 | int(11) | YES  |     | NULL    |                |
 | number5 | int(11) | YES  |     | NULL    |                |
 +---------+---------+------+-----+---------+----------------+
 */
public class DictSyxw1 extends Model<DictSyxw1> {
    public static DictSyxw1 dao = new DictSyxw1();
}
