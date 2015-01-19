package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by ericren on 14-8-6.
 * mysql> desc t_sys_users;
 +---------------+--------------+------+-----+---------+----------------+
 | Field         | Type         | Null | Key | Default | Extra          |
 +---------------+--------------+------+-----+---------+----------------+
 | id            | int(11)      | NO   | PRI | NULL    | auto_increment |
 | userid        | varchar(16)  | NO   | MUL | NULL    |                |
 | barcode       | varchar(128) | YES  |     | NULL    |                |
 | loginname     | varchar(255) | NO   | MUL | NULL    |                |
 | passwd        | varchar(255) | NO   |     | NULL    |                |
 | nickname      | varchar(255) | YES  |     | NULL    |                |
 | chsname       | varchar(255) | NO   | MUL | NULL    |                |
 | engname       | varchar(255) | YES  |     | NULL    |                |
 | sex           | varchar(16)  | YES  |     | NULL    |                |
 | birth         | varchar(16)  | YES  |     | NULL    |                |
 | natives       | varchar(16)  | YES  |     | NULL    |                |
 | cardtype      | varchar(16)  | YES  |     | NULL    |                |
 | idcard        | varchar(18)  | YES  |     | NULL    |                |
 | educationid   | varchar(16)  | YES  |     | NULL    |                |
 | major         | varchar(32)  | YES  |     | NULL    |                |
 | phone         | varchar(80)  | YES  |     | NULL    |                |
 | qq            | varchar(32)  | YES  |     | NULL    |                |
 | email         | varchar(255) | YES  |     | NULL    |                |
 | homepage      | varchar(200) | YES  |     | NULL    |                |
 | permanentaddr | varchar(255) | YES  |     | NULL    |                |
 | permanentzip  | varchar(8)   | YES  |     | NULL    |                |
 | address       | varchar(255) | YES  |     | NULL    |                |
 | postcode      | varchar(8)   | YES  |     | NULL    |                |
 | homephone     | varchar(80)  | YES  |     | NULL    |                |
 | comments      | text         | YES  |     | NULL    |                |
 | levelid       | varchar(16)  | YES  |     | NULL    |                |
 | logintimes    | int(11)      | YES  |     | NULL    |                |
 | lastlogin     | datetime     | YES  |     | NULL    |                |
 | updateid      | varchar(16)  | YES  |     | NULL    |                |
 | createtime    | datetime     | YES  |     | NULL    |                |
 | activetime    | datetime     | YES  |     | NULL    |                |
 | updatetime    | datetime     | YES  |     | NULL    |                |
 | valid         | varchar(4)   | NO   |     | 1       |                |
 +---------------+--------------+------+-----+---------+----------------+
 */
public class TSysUsers extends Model<TSysUsers> {
    public static TSysUsers dao = new TSysUsers();
}
