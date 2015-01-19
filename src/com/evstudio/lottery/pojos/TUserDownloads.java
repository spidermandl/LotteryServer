package com.evstudio.lottery.pojos;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by ericren on 14-8-29.
 *
 * mysql> desc t_user_downloads;
 +--------------+--------------+------+-----+---------+----------------+
 | Field        | Type         | Null | Key | Default | Extra          |
 +--------------+--------------+------+-----+---------+----------------+
 | id           | int(11)      | NO   | PRI | NULL    | auto_increment |
 | downloadurl  | varchar(200) | YES  |     | NULL    |                |
 | downloadtime | datetime     | YES  |     | NULL    |                |
 | request      | text         | YES  |     | NULL    |                |
 | valid        | varchar(4)   | YES  |     | NULL    |                |
 +--------------+--------------+------+-----+---------+----------------+
 5 rows in set (0.02 sec)

 mysql>
 */
public class TUserDownloads extends Model<TUserDownloads>{
    public static TUserDownloads dao = new TUserDownloads();
}
