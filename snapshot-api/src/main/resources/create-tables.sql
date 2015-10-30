
# DROP DATABASE chubot;
create database if not exists `snapshot` default character set utf8;
grant all on *.* to 'snap'@'localhost' identified by 'snap_cm';

use snapshot;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL auto_increment COMMENT '用户唯一标示',
  `name` varchar(50) NOT NULL COMMENT '昵称',
  `password` char(32) default NULL COMMENT '账户密码',
  `email` char(128) default NULL COMMENT '邮箱',
  # int range:[-2^31,2^31-1] [-2147483648,2147483647]
  `mobile` BIGINT(11) default NULL COMMENT '手机号',
  `role` tinyint(1) default NULL COMMENT '用户角色：1，管理员',
  `ustatus` tinyint(1) default 1 COMMENT '账户状态：0，禁用；1，正常启用',
  `createTime` timestamp default CURRENT_TIMESTAMP COMMENT '账户的创建时间',
  `updateTime` DATETIME COMMENT '账户信息的修改时间',
  `lastLoginIP` varchar(40) default NULL COMMENT '用户最后一次登录IP',
  `lastLoginTime` DATETIME COMMENT '用户最后一次登录时间',
  `loginTimes` int DEFAULT 0 COMMENT '登录次数',
  PRIMARY KEY  (`id`)
);

# admin@init_sn
Insert into user(id, name, email, password, role) values(1,'admin','admin@chu.com', 'e6d2cbe9f7f04256e3d6466d4a770990',1);