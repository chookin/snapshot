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
| birthday      | date | 生日 |
| sex           | char(2) | 性别 |
| role          | tinyint    | user role: 1, consumer; 2, cameraman |
| resume        | varchar(1024) | 个人简介       |
| photoFav      | varchar(521) | 拍照偏好:宝宝外拍、全家福 |
| location      | int       | 所在地            |
| address       | varchar(128) | 详细地址 |
| orderNum      | int       | 订单量            |
| Favorites     |           | 收藏              |
| value         | long      | 身价              |
| rating        | int       | 评级              |
| head_portrait | | 头像 |
| registered_time | timestamp | registered time |
| update_time   | timestamp | user info updated time |
| login_times   | int       | login times      |

## login 用户登录历史详情表

| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user          | bigInt    | user id          |
| login_ip      | char(40)  | 登录IP            |
| login_time    | timestamp | 登录时间          |

# camera 相机信息表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user          | bigInt    | 摄影师id          |
| camera        | varchar(256) | 相机类型       |
| camera_identity | varchar(256) | 相机标识     |
| create_time   | timestamp | 添加时间          |

# cameraman 摄影师信息表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| user          | bigInt    | user id          |
| range         | varchar(256) | 约拍范围       |
| desire        | varchar(256) | 约拍意愿       |
| photos        |  | 个人作品 |

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
| shot          | bigint    | shot id          |
| consumer      | bigint    | 消费者            |

## order_cameraman 订单-摄影师表
| column name   | type      |desc              |
| ------------- | --------- | ---------------- |
| id            | bigint    | auto increment   |
| shot          | bigint    | shot id          |
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