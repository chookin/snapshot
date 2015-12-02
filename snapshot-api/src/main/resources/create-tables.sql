
# DROP DATABASE snapshot;
# create database if not exists snapshot default character set utf8;
# grant all on snapshot.* to 'snap'@'localhost' identified by 'snap_cm';

use snapshot;

# 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id BIGINT NOT NULL auto_increment COMMENT '用户唯一标示',
  name varchar(50) NOT NULL COMMENT '昵称',
  password char(32) COMMENT '账户密码',
  mobile BIGINT(11) COMMENT '手机号',
  email varchar(128) COMMENT '邮箱',
  realName VARCHAR(128) COMMENT '真实姓名',
  idNum char(18) COMMENT '身份证号码',
  idImage VARCHAR(512) COMMENT '身份证照片的存储地址',
  role tinyint default 1 COMMENT '用户角色：-1, admin; 0, test; 1, user; 2, photographer',
  descr VARCHAR(1024) COMMENT '个人简介',
  status tinyint default 1 COMMENT '账户状态：0，禁用；1，正常启用',
  birthday DATE COMMENT '生日',
  sex enum('男','女'),
  area int COMMENT '所在地',
  avatar varchar(512) COMMENT '头像的存储地址',
  register_time DATETIME COMMENT '注册时间',
  update_time DATETIME COMMENT '账户信息的修改时间',
  order_count int DEFAULT 0 NOT NULL COMMENT '订单数',
  collected_count int DEFAULT 0 NOT NULL COMMENT '被收藏数',
  login_times int DEFAULT 0 NOT NULL COMMENT '登录次数',
  # Must define 'id' as primary key, or else throw exception: Incorrect table definition; there can be only one auto column and it must be defined as a key
  PRIMARY KEY  (id)
);

# 创建帐户test test
Insert into user(id, name, mobile, password, role) values(1,'test', 13426198753, '098f6bcd4621d373cade4e832627b4f6', 0);
# 创建帐户admin admin@init_sn
Insert into user(id, name, password, role) values(2,'admin','d35f1337e3f698228e641ea4b29b507d',-1);

# 用户登录历史详情表
DROP TABLE IF EXISTS login;
CREATE TABLE login(
  id BIGINT NOT NULL auto_increment,
  user_id BIGINT NOT NULL,
  login_ip char(15),
  time DATETIME,
  PRIMARY KEY  (id)
);

# 头像记录表
DROP TABLE if EXISTS avatar_detail;
CREATE TABLE avatar_detail(
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL COMMENT '用户id',
  photo varchar(512) comment '头像地址',
  time DATETIME COMMENT '头像的上传时间',
  PRIMARY KEY (id)
);

# 区域表
drop table if EXISTS area;
CREATE TABLE area(
  id BIGINT NOT NULL auto_increment,
  province char(6) NOT NULL COMMENT '省',
  city char(6) NOT NULL COMMENT '市',
  country char(6) NOT NULL COMMENT '县',
  region varchar(128) COMMENT '地域',
  PRIMARY KEY  (id)
) ;

# 器材信息表
DROP TABLE IF EXISTS camera;
CREATE TABLE camera(
  id BIGINT NOT NULL auto_increment,
  identity VARCHAR(512) NOT NULL COMMENT '器材标识',
  model varchar(128) NOT NULL COMMENT '器材型号',
  lensModel varchar(128) NOT NULL COMMENT '镜头型号',
  user_id BIGINT NOT NULL COMMENT '所属摄影师的id',
  create_time DATETIME COMMENT '添加时间',
  PRIMARY KEY  (id)
);

# 摄影师信息表
DROP TABLE if EXISTS grapher;
create TABLE grapher(
  id BIGINT NOT NULL auto_increment,
  user_id BIGINT NOT NULL COMMENT '摄影师id',
  price int DEFAULT 0 NOT NULL COMMENT '身价',
  rating int DEFAULT 0 NOT NULL COMMENT '评级',
  scope varchar(1024) COMMENT '约拍范围，服务城市，例如：北京、天津',
  desire VARCHAR(1024) COMMENT '约拍意愿,擅长领域，例如：情侣写真、个人写真',
  status TINYINT NOT NULL DEFAULT -1 COMMENT '-1: unauthorized, 1: authorized',
  PRIMARY KEY  (id)
);

# 摄影师身价表，记录历史身价
DROP TABLE IF EXISTS grapher_price;
CREATE TABLE grapher_price(
  user_id BIGINT NOT NULL COMMENT '摄影师id',
  price int DEFAULT 0 NOT NULL COMMENT '身价',
  time DATETIME COMMENT '时间'
);


# 对用户的点赞
drop table if exists user_like;
create table user_like(
  object bigint not null comment '被点赞的人id',
  subject bigint not null comment '哪个用户点的赞',
  time datetime comment '点赞时间'
);
# 照片评论
drop table if exists user_comment;
create table user_comment(
  id bigint not null auto_increment,
  object bigint not null comment '被评论的人id',
  subject bigint not null comment '评论的人',
  parent bigint default 0 not null comment '父评论id',
  centent text not null comment '评论正文',
  time datetime comment '评论时间',
  PRIMARY KEY  (id)
);
# 相片
drop table if exists photo;
create table photo(
  id bigint not null auto_increment,
  user_id bigint not null comment '上传照片的用户的id',
  photo varchar(512) comment '照片地址',
  like_count int default 0 not null comment '点赞次数',
  comment_count int default 0 not null comment '评论次数',
  TIME DATETIME COMMENT '照片上传的时间',
  PRIMARY KEY  (id)
);

# 对照片的点赞
drop table if exists photo_like;
create table photo_like(
  photo_id bigint not null comment '被点赞的照片id',
  user_id bigint not null comment '哪个用户点的赞',
  time datetime comment '点赞时间'
);
# 照片评论
drop table if exists photo_comment;
create table photo_comment(
  id bigint not null auto_increment,
  shot_id bigint not null comment '照片id',
  user_id bigint not null comment '评论的人',
  parent bigint default 0 not null comment '父评论id',
  centent text comment '评论正文',
  time datetime comment '评论时间',
  PRIMARY KEY  (id)
);
# 约拍活动
drop table if exists shot;
create table shot(
  id bigint not null auto_increment,
  price int not null comment '价格',
  start_time datetime comment '活动开始时间',
  end_time datetime comment '活动结束时间',
  location varchar(512) default '' not null comment '拍摄的地点',
  service varchar(512) default '' not null comment '服务，如：>60张拍摄，30张精修',
  min_number int default 0 not null comment '最少参团人数或家庭数, inclusive',
  max_number int default 0 not null comment '最大参团人数或家庭数, inclusive',
  enrolled_number int default 0 not null comment '已报名的人数或家庭数',
  status TINYINT DEFAULT 0 NOT NULL COMMENT '订单状态: 0: 未开始, 1: 进行中; 2, 取消, 3:完成',
  descr varchar(2048) default '' not null comment '活动简介',
  create_time datetime comment '活动创建时间',
  registration_deadline datetime comment '报名截止时间',
  PRIMARY KEY  (id)
);
# 约拍活动的摄影师
drop table if exists shot_grapher;
create table shot_grapher(
  shot_id bigint not null comment '约拍活动id',
  grapher_id bigint not null comment '摄影师id'
);
# 约拍活动的参加人员
drop table if exists shot_enroll;
create table shot_enroll(
  shot_id bigint not null comment '约拍活动id',
  user_id bigint not null comment '参团人id',
  time datetime not null comment '报名的时间'
);
# 约拍活动的样板图片
drop table if exists shot_poster;
create table shot_poster(
  shot_id bigint not null comment '约拍活动id',
  photo varchar(512) comment '约拍海报图片'
);
# 对约拍的点赞
drop table if exists shot_like;
create table shot_like(
  shot_id bigint not null comment '约拍活动id',
  user_id bigint not null comment '点赞的人',
  time datetime comment '点赞时间'
);
# 约拍评论
drop table if exists shot_comment;
create table shot_comment(
  id bigint not null auto_increment,
  shot_id bigint not null comment '约拍活动id',
  user_id bigint not null comment '评论的人',
  parent bigint default 0 not null comment '父评论id',
  centent text comment '评论正文',
  time datetime comment '评论时间',
  PRIMARY KEY  (id)
);
