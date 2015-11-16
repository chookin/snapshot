
# DROP DATABASE chubot;
# create database if not exists `snapshot` default character set utf8;
# grant all on *.* to 'snap'@'localhost' identified by 'snap_cm';

use snapshot;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT NOT NULL auto_increment COMMENT '用户唯一标示',
  `name` varchar(50) NOT NULL COMMENT '昵称',
  `password` char(32) COMMENT '账户密码',
  `mobile` BIGINT(11) COMMENT '手机号',
  `email` varchar(128) COMMENT '邮箱',
  `role` tinyint default 1 COMMENT '用户角色：-1, admin; 0, test; 1, user; 2, photographer',
  `status` tinyint default 1 COMMENT '账户状态：0，禁用；1，正常启用',
  `birthday` DATE COMMENT '生日',
  `sex` enum('男','女'),
  `resume` VARCHAR(1024) COMMENT '个人简介',
  `area` int COMMENT '所在地',
  `avatar` varchar(512) COMMENT '头像的存储地址',
  `register_time` DATETIME COMMENT '注册时间',
  `update_time` DATETIME COMMENT '账户信息的修改时间',
  # Must define 'id' as primary key, or else throw exception: Incorrect table definition; there can be only one auto column and it must be defined as a key
  PRIMARY KEY  (`id`)
);

# test test
Insert into user(id, name, mobile, password, role) values(1,'test', 13426198753, '098f6bcd4621d373cade4e832627b4f6', 0);
# admin@init_sn
Insert into user(id, name, password, role) values(2,'admin','d35f1337e3f698228e641ea4b29b507d',-1);

DROP TABLE IF EXISTS login;
CREATE TABLE login(
  id BIGINT NOT NULL auto_increment,
  user_id BIGINT NOT NULL,
  login_ip char(15),
  time DATETIME,
  PRIMARY KEY  (`id`)
);

