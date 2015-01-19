package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-9-16.
 *mysql> desc t_online_users;
 +------------+-------------+------+-----+---------+----------------+
 | Field      | Type        | Null | Key | Default | Extra          |
 +------------+-------------+------+-----+---------+----------------+
 | id         | int(11)     | NO   | PRI | NULL    | auto_increment |
 | userid     | varchar(16) | YES  | MUL | NULL    |                |
 | logintime  | datetime    | YES  |     | NULL    |                |
 | updatetime | datetime    | YES  |     | NULL    |                |
 | lefttype   | int(11)     | YES  |     | NULL    |                |
 | lefttime   | datetime    | YES  |     | NULL    |                |
 | state      | varchar(4)  | YES  |     | NULL    |                |
 +------------+-------------+------+-----+---------+----------------+
 7 rows in set (0.02 sec)

 mysql>
 */
public class TOlineUsers extends Model<TOlineUsers> {
    public static TOlineUsers dao = new TOlineUsers();
}
