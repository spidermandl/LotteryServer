package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-6-25.
 *
 * mysql> desc syydj;
 +----------------+--------------+------+-----+---------+----------------+
 | Field          | Type         | Null | Key | Default | Extra          |
 +----------------+--------------+------+-----+---------+----------------+
 | id             | int(11)      | NO   | PRI | NULL    | auto_increment |
 | periods        | varchar(32)  | YES  |     | NULL    |                |
 | publishdate    | date         | YES  |     | NULL    |                |
 | dayperiods     | int(11)      | YES  |     | NULL    |                |
 | winningnumber1 | int(11)      | YES  |     | NULL    |                |
 | winningnumber2 | int(11)      | YES  |     | NULL    |                |
 | winningnumber3 | int(11)      | YES  |     | NULL    |                |
 | winningnumber4 | int(11)      | YES  |     | NULL    |                |
 | winningnumber5 | int(11)      | YES  |     | NULL    |                |
 | number1        | int(11)      | YES  |     | NULL    |                |
 | number2        | int(11)      | YES  |     | NULL    |                |
 | number3        | int(11)      | YES  |     | NULL    |                |
 | number4        | int(11)      | YES  |     | NULL    |                |
 | number5        | int(11)      | YES  |     | NULL    |                |
 | number6        | int(11)      | YES  |     | NULL    |                |
 | number7        | int(11)      | YES  |     | NULL    |                |
 | number8        | int(11)      | YES  |     | NULL    |                |
 | number9        | int(11)      | YES  |     | NULL    |                |
 | number10       | int(11)      | YES  |     | NULL    |                |
 | number11       | int(11)      | YES  |     | NULL    |                |
 | drawyear       | varchar(4)   | YES  |     | NULL    |                |
 | drawdate       | varchar(4)   | YES  |     | NULL    |                |
 | drawtime       | time         | YES  |     | NULL    |                |
 | importtime     | datetime     | YES  |     | NULL    |                |
 | comments       | varchar(200) | YES  |     | NULL    |                |
 | createid       | varchar(16)  | YES  |     | NULL    |                |
 | modifyid       | varchar(16)  | YES  |     | NULL    |                |
 | modifytime     | datetime     | YES  |     | NULL    |                |
 | valid          | varchar(4)   | YES  |     | NULL    |                |
 +----------------+--------------+------+-----+---------+----------------+
 29 rows in set (0.00 sec)

 */
public class Syydj extends Model<Syydj> {
    public static Syydj dao = new Syydj();
}
