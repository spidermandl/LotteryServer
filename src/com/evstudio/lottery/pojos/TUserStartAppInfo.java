package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-9-16.
 * mysql> desc t_user_start_app_info;
 +----------------+--------------+------+-----+---------+----------------+
 | Field          | Type         | Null | Key | Default | Extra          |
 +----------------+--------------+------+-----+---------+----------------+
 | id             | int(11)      | NO   | PRI | NULL    | auto_increment |
 | reqtime        | datetime     | YES  |     | NULL    |                |
 | requrl         | varchar(200) | YES  |     | NULL    |                |
 | reqmethod      | varchar(32)  | YES  |     | NULL    |                |
 | reqcontent     | text         | YES  |     | NULL    |                |
 | appversion     | varchar(32)  | YES  |     | NULL    |                |
 | appversioncode | varchar(32)  | YES  | MUL | NULL    |                |
 | userid         | varchar(16)  | YES  | MUL | NULL    |                |
 | modifytime     | datetime     | YES  |     | NULL    |                |
 | valid          | varchar(4)   | YES  |     | NULL    |                |
 +----------------+--------------+------+-----+---------+----------------+
 10 rows in set (0.02 sec)

 mysql>
 */
public class TUserStartAppInfo extends Model<TUserStartAppInfo> {
    public static final TUserStartAppInfo dao = new TUserStartAppInfo();
}
