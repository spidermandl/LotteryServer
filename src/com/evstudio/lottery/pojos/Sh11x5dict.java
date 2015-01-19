package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-8-11.
 * mysql> desc sh11x5dict;
 +------------+-------------+------+-----+---------+----------------+
 | Field      | Type        | Null | Key | Default | Extra          |
 +------------+-------------+------+-----+---------+----------------+
 | id         | int(11)     | NO   | PRI | NULL    | auto_increment |
 | periods    | varchar(32) | YES  |     | NULL    |                |
 | dicts      | varchar(16) | YES  |     | NULL    |                |
 | dictnumber | int(11)     | YES  |     | NULL    |                |
 | updatetime | datetime    | YES  |     | NULL    |                |
 | valid      | varchar(4)  | YES  |     | NULL    |                |
 +------------+-------------+------+-----+---------+----------------+
 6 rows in set (0.01 sec)

 mysql>
 */
public class Sh11x5dict extends Model<Sh11x5dict> {
    public static Sh11x5dict dao = new Sh11x5dict();
}
