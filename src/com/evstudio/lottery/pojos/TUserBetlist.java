package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-8-29.
 * mysql> desc t_user_betlist;
 +-------------+-------------+------+-----+---------+----------------+
 | Field       | Type        | Null | Key | Default | Extra          |
 +-------------+-------------+------+-----+---------+----------------+
 | id          | int(11)     | NO   | PRI | NULL    | auto_increment |
 | betid       | varchar(16) | YES  |     | NULL    |                |
 | userid      | varchar(16) | YES  |     | NULL    |                |
 | periods     | varchar(16) | YES  |     | NULL    |                |
 | dayperiods  | int(11)     | YES  |     | NULL    |                |
 | bettype     | varchar(8)  | YES  |     | NULL    |                |
 | betcontent  | varchar(64) | YES  |     | NULL    |                |
 | multiple    | int(11)     | YES  |     | NULL    |                |
 | issingle    | int(11)     | YES  |     | NULL    |                |
 | betmoney    | varchar(16) | YES  |     | NULL    |                |
 | bettime     | datetime    | YES  |     | NULL    |                |
 | betstatus   | varchar(2)  | YES  |     | NULL    |                |
 | iswin       | int(11)     | YES  |     | NULL    |                |
 | winmoney    | varchar(16) | YES  |     | NULL    |                |
 | moneystatus | int(11)     | YES  |     | NULL    |                |
 | createtime  | datetime    | YES  |     | NULL    |                |
 | modifytime  | datetime    | YES  |     | NULL    |                |
 | modifyid    | varchar(16) | YES  |     | NULL    |                |
 | valid       | varchar(4)  | YES  |     | NULL    |                |
 +-------------+-------------+------+-----+---------+----------------+
 19 rows in set (0.02 sec)

 mysql>
 */
public class TUserBetlist extends Model<TUserBetlist> {
    public static TUserBetlist dao = new TUserBetlist();
}
