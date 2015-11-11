
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
  `role` tinyint default NULL COMMENT '用户角色：0, test; 1, admin; 2, user; 3, photographer',
  `status` tinyint default 1 COMMENT '账户状态：0，禁用；1，正常启用',
  `create_time` timestamp default CURRENT_TIMESTAMP COMMENT '账户的创建时间',
  `update_time` DATETIME COMMENT '账户信息的修改时间',
  PRIMARY KEY  (`id`)
);

# test test
Insert into user(id, name, password, role) values(1,'test', '098f6bcd4621d373cade4e832627b4f6', 0);
# admin@init_sn
Insert into user(id, name, email, password, role) values(1,'admin','admin@chu.com', 'd35f1337e3f698228e641ea4b29b507d',1);