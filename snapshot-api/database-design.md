# database
## admin 管理员表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| name          | char(50)  | 管理员登录名       |
| password      | char(32)  | password         |
## user 用户表

| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| name          | char(50)  | user name        |
| password      | char(32)  | password         |
| mobile        | bigint(11) | phone number     |
| birthday      | date      | 生日             |
| sex           | char(2)   | 性别             |
| role          | tinyint    | user role: 1, consumer; 2, cameraman |
| resume        | varchar(1024) | 个人简介      |
| photoFav      | varchar(521) | 拍照偏好:宝宝外拍、全家福 |
| area          | int       | 所在地            |
| avatar        | varchar(512) | 头像          |
| sign_up_time  | timestamp    | 注册时间       |
| update_time   | timestamp | user info updated time |

## area 区域表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| province      | char(6)   | 省               |
| city          | char(6)   | 市               |
| country       | char(6)   | 县               |
| region        | varchar(32)| 地域             |

## login 用户登录历史详情表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user_id       | bigInt    | user id          |
| login_ip      | char(40)  | 登录IP            |
| login_time    | timestamp | 登录时间          |

## user_stat
| user_id       | bigInt    | user id          |
| orderNum      | int       | 订单量            |
| Favorites     |           | 收藏              |
| login_times   | int       | login times      |

## camera 相机信息表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user_id       | bigInt    | 摄影师id          |
| camera        | varchar(256) | 相机类型       |
| camera_identity | varchar(256) | 相机标识     |
| create_time   | timestamp | 添加时间          |

## cameraman 摄影师信息表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user_id       | bigInt    | user id          |
| value         | long      | 身价              |
| rating        | int       | 评级              |
| range         | varchar(256) | 约拍范围       |
| desire        | varchar(256) | 约拍意愿       |
| status        | tinyint   | -1: unauthorized, 1: authorized |


## photo
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user_id       | bigInt    | 摄影师id          |
| photo         | varchar(512) | 照片地址       |

## shot 约拍订单表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| location      | varchar(512) | 拍摄的地点      |
| desc          | varchar(2048) | 描述          |
| preview_image | | |
| consumer_max_number | int | 名额 |
| price         | double    | 价格              |
| status        | tinyint       | 订单状态: 进行中，取消，完成 |
| create_time   | timestamp | 订单创建时间       |
| registration_deadline | timestamp | 报名截止时间 |
| start_time    | timestamp | 拍摄的开始时间     |
| finish_time   | timestamp | 完成时间          |

## order_consumer 订单-消费者表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| shot_id       | bigint    | shot id          |
| consumer      | bigint    | 消费者            |

## order_cameraman 订单-摄影师表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| shot_id       | bigint    | shot id          |
| cameraman     | bigint    | 摄影师            |

## like 对用户点赞
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| object        | bigint    | 被点赞的人id       |
| subject       | bigint    | 哪个用户点的赞     |
| time          | timestamp | 时间              |

## like 对照片点赞
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| photo_id      | bigint    | 照片id         |
| subject       | bigint    | 哪个用户点的赞     |
| time          | timestamp | 时间              |

## remark 评论
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| object        | bigint    | 被评论的            |
| subject       | bigint    | 哪个用户做的评论    |
| time          | timestamp | 时间             |