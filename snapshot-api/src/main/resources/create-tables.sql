
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
  phone BIGINT(11) COMMENT '手机号',
  email varchar(128) COMMENT '邮箱',
  real_name VARCHAR(128) COMMENT '真实姓名',
  id_num char(18) COMMENT '身份证号码',
  id_image VARCHAR(512) COMMENT '身份证照片的存储地址',
  role tinyint default 1 COMMENT '用户角色：-1, admin; 0, test; 1, user; 2, photographer',
  profile VARCHAR(1024) COMMENT '个人简介',
  status tinyint default 1 COMMENT '账户状态：0，禁用；1，正常启用',
  birthday DATE COMMENT '生日',
  sex enum('男','女'),
  area int COMMENT '所在地',
  avatar varchar(512) COMMENT '头像的存储地址',
  register_time DATETIME COMMENT '注册时间',
  update_time DATETIME COMMENT '账户信息的修改时间',
#   order_count int DEFAULT 0 NOT NULL COMMENT '订单数',
#   appointment_count int DEFAULT 0 COMMENT '预约数',
#   collected_count int DEFAULT 0 NOT NULL COMMENT '被收藏数',
  # Must define 'id' as primary key, or else throw exception: Incorrect table definition; there can be only one auto column and it must be defined as a key
  PRIMARY KEY  (id)
);

# 创建帐户test test
Insert into user(id, name, phone, password, role) values(1,'test', 13426198753, '098f6bcd4621d373cade4e832627b4f6', 0);
# 创建帐户admin admin@init_sn
Insert into user(id, name, password, role) values(2,'admin','d35f1337e3f698228e641ea4b29b507d',-1);

INSERT INTO user(id, name, phone, password) VALUE(3, 'jacob', '13811245934', '730ea9a92c1f03e5b1934129e98528c7');

# 用户登录历史详情表
DROP TABLE IF EXISTS login;
CREATE TABLE login(
  id BIGINT NOT NULL auto_increment,
  user_id BIGINT NOT NULL,
  login_ip char(15),
  time DATETIME,
  PRIMARY KEY  (id),
  FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE on DELETE CASCADE
);

# 头像记录表
DROP TABLE if EXISTS avatar_detail;
CREATE TABLE avatar_detail(
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL COMMENT '用户id',
  photo varchar(512) comment '头像地址',
  time DATETIME COMMENT '头像的上传时间',
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id) ON UPDATE CASCADE on DELETE CASCADE
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
  lens_model varchar(128) NOT NULL COMMENT '镜头型号',
  user_id BIGINT NOT NULL COMMENT '所属摄影师的id',
  image VARCHAR(512) COMMENT '相机照片地址',
  create_time DATETIME COMMENT '添加时间',
  PRIMARY KEY  (id)
);

# 摄影师信息表
DROP TABLE if EXISTS grapher;
create TABLE grapher(
  user_id BIGINT NOT NULL COMMENT '摄影师id',
  price int DEFAULT 0 NOT NULL COMMENT '身价',
  rating int DEFAULT 0 NOT NULL COMMENT '评级',
  region varchar(1024) COMMENT '约拍范围，服务城市，例如：北京、天津',
  desire VARCHAR(1024) COMMENT '约拍意愿,擅长领域，例如：情侣写真、个人写真',
  status TINYINT NOT NULL DEFAULT -1 COMMENT '-1: unauthorized, 1: authorized',
  create_time DATETIME COMMENT '添加时间',
  PRIMARY KEY  (user_id),
  FOREIGN KEY (user_id) REFERENCES user(id) on UPDATE CASCADE on DELETE RESTRICT
);
# 摄影师套餐
DROP TABLE IF EXISTS grapher_plan;
CREATE TABLE grapher_plan(
  id BIGINT NOT NULL auto_increment,
  user_id BIGINT NOT NULL COMMENT '摄影师id',
  shot_num int DEFAULT 0 COMMENT '拍摄张数',
  shot_hour int DEFAULT 0 COMMENT '拍摄时长，小时数',
  truing_num int DEFAULT 0 COMMENT '精修底片的张数',
  print_num INT DEFAULT 0 COMMENT '相片冲印的张数',
  clothing VARCHAR(256) COMMENT '服装',
  makeup VARCHAR(256) COMMENT '化妆',
  create_time DATETIME COMMENT '添加时间',
  PRIMARY KEY  (id),
  FOREIGN KEY (user_id) REFERENCES user(id) on UPDATE CASCADE on DELETE CASCADE
);

# 摄影师身价表 记录历史身价
DROP TABLE IF EXISTS grapher_price_history;
CREATE TABLE grapher_price_history(
  id BIGINT NOT NULL auto_increment,
  grapher_id BIGINT NOT NULL COMMENT '摄影师id',
  price int DEFAULT 0 NOT NULL COMMENT '身价，单位元',
  month int COMMENT '月份,由‘该身价值的计算时间’计算得到',
  time DATETIME COMMENT '该身价值的计算时间',
  PRIMARY KEY (id),
  UNIQUE (grapher_id, month),
  FOREIGN KEY (grapher_id) REFERENCES grapher(user_id) on UPDATE CASCADE on DELETE CASCADE
);

# 摄影活动发布
DROP TABLE if EXISTS shot_release;
CREATE TABLE shot_release(
  id bigint not null,
  grapher_id BIGINT NOT NULL COMMENT '摄影师id',
  location varchar(512) comment '拍摄的地点，可以与摄影师的常驻地点不同',
  like_count int default 0 not null comment '点赞次数',
  comment_count int default 0 not null comment '评论次数',
  appointment_count int DEFAULT 0 NOT NULL COMMENT '预约数量',
  create_time DATETIME COMMENT '活动的创建时间',
  PRIMARY KEY (id),
  FOREIGN KEY (grapher_id) REFERENCES grapher(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS shot_still;
CREATE TABLE shot_still(
  id bigint not null auto_increment,
  shot_id bigint not null comment '活动id',
  pic varchar(512) comment '剧照照片地址',
  PRIMARY KEY  (id),
  FOREIGN KEY (shot_id) REFERENCES shot_release(id) on UPDATE CASCADE on DELETE CASCADE
);


# 作品
DROP TABLE if EXISTS work;
CREATE TABLE work(
  id BIGINT NOT NULL,
  user_id bigint not null comment '上传照片的用户的id',
  name VARCHAR(64) NOT NULL COMMENT '作品名称',
  location VARCHAR(128) COMMENT '拍摄地点',
  like_count int default 0 not null comment '点赞次数',
  comment_count int default 0 not null comment '评论次数',
  create_time DATETIME COMMENT '作品创建时间',
  PRIMARY KEY (id)
);
INSERT into work(id, user_id, name) VALUES(1, 1, 'zhu');

# 作品照片
DROP TABLE if EXISTS photo;
CREATE TABLE photo(
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT COMMENT '照片所属用户',
  work_id BIGINT COMMENT '该照片所属作品的id',
  photo VARCHAR(512) NOT NULL COMMENT '照片地址',
  like_count int default 0 not null comment '点赞次数',
  comment_count int default 0 not null comment '评论次数',
  time DATETIME COMMENT '上传时间',
  PRIMARY KEY (id)
);
INSERT into photo(id, user_id, photo) VALUES(1, 1, 'path/p1');

# 订单
drop table if EXISTS shot_order;
CREATE TABLE shot_order(
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL COMMENT '用户id',
  grapher_id BIGINT NOT NULL COMMENT '摄影师id',
  price int DEFAULT 0 NOT NULL COMMENT '订单价格',
  start_time DATETIME COMMENT '时间',
  create_time DATETIME COMMENT '订单创建时间',
  PRIMARY KEY (id)
);

# 特色服务
drop TABLE if EXISTS special_shot;
CREATE TABLE special_shot(
  id bigint not null,
  price int DEFAULT 0 not null comment '价格',
  time datetime comment '时间',
  location varchar(512) comment '拍摄的地点',
  title varchar(128) DEFAULT '' COMMENT '活动名称',
  intro varchar(254) DEFAULT '' COMMENT '活动简介',
  summary VARCHAR(1024) DEFAULT '' COMMENT '活动内容',
  service varchar(128) DEFAULT '' COMMENT '服务，如：>60张拍摄，10张精修',
  sculpt varchar(32) DEFAULT '' COMMENT '造型，如：1组',
  creator BIGINT NOT NULL COMMENT '创建者',
  create_time DATETIME COMMENT '创建时间',
  like_count int default 0 not null comment '点赞次数',
  comment_count int default 0 not null comment '评论次数',
  PRIMARY KEY (id)
);
DROP TABLE IF EXISTS special_shot_still;
CREATE TABLE special_shot_still(
  id bigint not null auto_increment,
  shot_id bigint not null comment '特色服务活动id',
  pic varchar(512) comment '照片地址',
  PRIMARY KEY  (id),
  FOREIGN KEY (shot_id) REFERENCES special_shot(id) on UPDATE CASCADE on DELETE CASCADE
);

DROP TABLE IF EXISTS special_shot_grapher;
CREATE TABLE special_shot_grapher(
  id bigint not null auto_increment,
  shot_id bigint not null comment '特色服务活动id',
  grapher_id bigint not null comment '摄影师id',
  PRIMARY KEY  (id),
  FOREIGN KEY (shot_id) REFERENCES special_shot(id) on UPDATE CASCADE on DELETE CASCADE,
  FOREIGN KEY (grapher_id) REFERENCES grapher(user_id) on UPDATE CASCADE on DELETE CASCADE
);

# 团拍活动
drop table if exists group_shot;
create table group_shot(
  id bigint not null auto_increment,
  price int DEFAULT 0 not null comment '价格',
  start_time datetime comment '活动开始时间',
  end_time datetime comment '活动结束时间',
  registration_deadline datetime comment '报名截止时间',
  title varchar(128) DEFAULT '' COMMENT '活动名称',
  intro varchar(2048) default '' not null comment '活动简介',
  summary VARCHAR(1024) DEFAULT '' COMMENT '活动内容',
  location varchar(512) comment '拍摄的地点',
  service varchar(512) comment '服务，如：>60张拍摄，30张精修',
  min_number int default 0 not null comment '最少参团人数或家庭数, inclusive',
  max_number int default 0 not null comment '最大参团人数或家庭数, inclusive',
  enrolled_number int default 0 not null comment '已报名的人数或家庭数',
  status TINYINT DEFAULT 0 NOT NULL COMMENT '订单状态: 0: 未开始, 1: 进行中; 2, 取消, 3:完成',
  like_count int default 0 not null comment '点赞次数',
  comment_count int default 0 not null comment '评论次数',
  creator BIGINT NOT NULL COMMENT '创建者',
  create_time datetime comment '活动创建时间',
  PRIMARY KEY (id)
);
# 团拍活动的摄影师
drop table if exists group_shot_grapher;
create table group_shot_grapher(
  id bigint not null auto_increment,
  shot_id bigint not null comment '团拍活动id',
  grapher_id bigint not null comment '摄影师id',
  PRIMARY KEY (id),
  FOREIGN KEY (shot_id) REFERENCES group_shot(id) on DELETE CASCADE,
  FOREIGN KEY (grapher_id) REFERENCES grapher(user_id) on UPDATE CASCADE on DELETE CASCADE
);
# 团拍活动的参加人员
drop table if exists group_shot_enroll;
create table group_shot_enroll(
  id bigint not null auto_increment,
  shot_id bigint not null comment '团拍活动id',
  user_id bigint not null comment '参团人id',
  time datetime not null comment '报名的时间',
  PRIMARY KEY (id),
  FOREIGN KEY (shot_id) REFERENCES group_shot(id) on DELETE CASCADE
);
# 团拍活动的样板图片
drop table if exists group_shot_still;
create table group_shot_still(
  id bigint not null auto_increment,
  shot_id bigint not null comment '团拍活动id',
  pic varchar(512) comment '团拍海报图片',
  PRIMARY KEY  (id),
  FOREIGN KEY (shot_id) REFERENCES group_shot(id) on UPDATE CASCADE on DELETE CASCADE
);

# 图片
DROP TABLE IF EXISTS pic;
CREATE TABLE pic (
  id BIGINT NOT NULL auto_increment,
  title varchar(64) COMMENT '标题',
  path varchar(512) NOT NULL COMMENT '图片的服务端存储地址',
  upload_time DATETIME COMMENT '上传时间',
  PRIMARY KEY  (id)
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments(
  id bigint not null,
  object_id bigint not null comment '被评论对象的id',
  commentator_id bigint not null comment '评论的人',
  parent bigint default 0 not null comment '父评论id',
  content text comment '评论正文',
  type TINYINT not NULL COMMENT '评论对象的类型',
  time datetime comment '评论时间',
  PRIMARY KEY  (id)
);

DROP TABLE IF EXISTS likes;
CREATE TABLE likes(
  id bigint not null auto_increment,
  object_id bigint not null comment '被点赞对象的id',
  commentator_id bigint not null comment '点赞的人',
  type TINYINT not NULL COMMENT '点赞对象的类型',
  time datetime comment '点赞时间',
  PRIMARY KEY  (id)
);
