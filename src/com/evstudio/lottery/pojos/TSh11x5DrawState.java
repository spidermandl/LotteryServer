package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-9-17.
 *
 * mysql> desc t_sh11x5_draw_state;
 +------------+-------------+------+-----+---------+----------------+
 | Field      | Type        | Null | Key | Default | Extra          |
 +------------+-------------+------+-----+---------+----------------+
 | id         | int(11)     | NO   | PRI | NULL    | auto_increment |
 | periods    | varchar(16) | YES  | MUL | NULL    |                |
 | dayperiods | int(11)     | YES  |     | NULL    |                |
 | begintime  | datetime    | YES  |     | NULL    |                |
 | endtime    | datetime    | YES  |     | NULL    |                |
 | number1    | int(11)     | YES  |     | NULL    |                |
 | number2    | int(11)     | YES  |     | NULL    |                |
 | number3    | int(11)     | YES  |     | NULL    |                |
 | number4    | int(11)     | YES  |     | NULL    |                |
 | number5    | int(11)     | YES  |     | NULL    |                |
 | number6    | int(11)     | YES  |     | NULL    |                |
 | number7    | int(11)     | YES  |     | NULL    |                |
 | number8    | int(11)     | YES  |     | NULL    |                |
 | number9    | int(11)     | YES  |     | NULL    |                |
 | number10   | int(11)     | YES  |     | NULL    |                |
 | number11   | int(11)     | YES  |     | NULL    |                |
 | createtime | datetime    | YES  |     | NULL    |                |
 | updatetime | datetime    | YES  |     | NULL    |                |
 | valid      | varchar(4)  | YES  | MUL | NULL    |                |
 +------------+-------------+------+-----+---------+----------------+
 19 rows in set (0.03 sec)

 mysql>
 */
public class TSh11x5DrawState extends Model<TSh11x5DrawState> {
    public static TSh11x5DrawState dao = new TSh11x5DrawState();
}
