package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by eric on 14/12/15.
 *
 mysql> desc t_user_betlist_jczq_head;
 +-------------+-------------+------+-----+---------+----------------+
 | Field       | Type        | Null | Key | Default | Extra          |
 +-------------+-------------+------+-----+---------+----------------+
 | id          | int(11)     | NO   | PRI | NULL    | auto_increment |
 | head_id     | varchar(32) | YES  |     | NULL    |                |
 | userid      | varchar(16) | YES  |     | NULL    |                |
 | bettime     | datetime    | YES  |     | NULL    |                |
 | bettype     | varchar(16) | YES  |     | NULL    |                |
 | ticketnumer | int(11)     | YES  |     | NULL    |                |
 | multiple    | int(11)     | YES  |     | NULL    |                |
 | issingle    | int(11)     | YES  |     | NULL    |                |
 | betcontent  | text        | YES  |     | NULL    |                |
 | betmoney    | varchar(16) | YES  |     | NULL    |                |
 | betstatus   | varchar(2)  | YES  |     | NULL    |                |
 | iswin       | int(11)     | YES  |     | NULL    |                |
 | winmoney    | varchar(16) | YES  |     | NULL    |                |
 | moneystatus | int(11)     | YES  |     | NULL    |                |
 | createtime  | datetime    | YES  |     | NULL    |                |
 | modifytime  | datetime    | YES  |     | NULL    |                |
 | modifyid    | varchar(16) | YES  |     | NULL    |                |
 | valid       | varchar(4)  | YES  |     | NULL    |                |
 +-------------+-------------+------+-----+---------+----------------+
 18 rows in set (0.14 sec)

 mysql>
 */
public class TUserBetlistJczqHead extends Model<TUserBetlistJczqHead>{
    public static TUserBetlistJczqHead dao = new TUserBetlistJczqHead();
}
