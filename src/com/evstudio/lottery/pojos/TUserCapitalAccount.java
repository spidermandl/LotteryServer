package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-8-29.
 *
 * mysql> desc t_user_capital_account;
 +-------------+-------------+------+-----+---------+----------------+
 | Field       | Type        | Null | Key | Default | Extra          |
 +-------------+-------------+------+-----+---------+----------------+
 | id          | int(11)     | NO   | PRI | NULL    | auto_increment |
 | userid      | varchar(16) | YES  | MUL | NULL    |                |
 | accounttype | varchar(16) | YES  | MUL | NULL    |                |
 | balance     | varchar(16) | YES  | MUL | NULL    |                |
 | expirydate  | datetime    | YES  |     | NULL    |                |
 | createtime  | datetime    | YES  |     | NULL    |                |
 | modifytime  | datetime    | YES  |     | NULL    |                |
 | modifyid    | varchar(16) | YES  |     | NULL    |                |
 | valid       | varchar(4)  | YES  |     | NULL    |                |
 +-------------+-------------+------+-----+---------+----------------+
 9 rows in set (0.01 sec)

 mysql>
 */
public class TUserCapitalAccount extends Model<TUserCapitalAccount> {
    public static TUserCapitalAccount dao = new TUserCapitalAccount();
}
