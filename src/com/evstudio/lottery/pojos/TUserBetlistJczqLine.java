package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by eric on 14/12/15.
 *
 * mysql> desc t_user_betlist_jczq_line;
 +-------------+--------------+------+-----+---------+----------------+
 | Field       | Type         | Null | Key | Default | Extra          |
 +-------------+--------------+------+-----+---------+----------------+
 | id          | int(11)      | NO   | PRI | NULL    | auto_increment |
 | head_id     | varchar(16)  | YES  |     | NULL    |                |
 | line_id     | varchar(16)  | YES  |     | NULL    |                |
 | gameinfo    | text         | YES  |     | NULL    |                |
 | gameday     | int(11)      | YES  |     | NULL    |                |
 | bettype     | int(11)      | YES  |     | NULL    |                |
 | betcontent  | varchar(200) | YES  |     | NULL    |                |
 | multiple    | int(11)      | YES  |     | NULL    |                |
 | betmoney    | varchar(16)  | YES  |     | NULL    |                |
 | betstatus   | varchar(2)   | YES  |     | NULL    |                |
 | iswin       | int(11)      | YES  |     | NULL    |                |
 | winmoney    | varchar(16)  | YES  |     | NULL    |                |
 | moneystatus | int(11)      | YES  |     | NULL    |                |
 | createtime  | datetime     | YES  |     | NULL    |                |
 | modifytime  | datetime     | YES  |     | NULL    |                |
 | modiyid     | varchar(16)  | YES  |     | NULL    |                |
 | valid       | varchar(4)   | YES  |     | NULL    |                |
 +-------------+--------------+------+-----+---------+----------------+
 17 rows in set (0.15 sec)

 mysql>

 */
public class TUserBetlistJczqLine extends Model<TUserBetlistJczqLine> {
    public static TUserBetlistJczqLine dao = new TUserBetlistJczqLine();
}
