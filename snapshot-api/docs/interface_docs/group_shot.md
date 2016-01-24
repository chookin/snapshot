# 团拍接口
## 用户报名参加团拍
method: POST

path：/groupShot/enroll

request paras:

* uid, long, 用户ID
* shotId, long, 团拍活动id

## 获取团拍详情
method: GET

path：/groupShot/enroll

request paras:

* shotId, long, 团拍活动id

## 获取团拍列表
method: GET

path: /groupShot/get

request paras:

* uid, long, 用户id
* longitude, double, 经度，可选
* latitude, double, 维度，可选
* page, int, 分页请求的页数
* step, int, 分页请求的每页多少条
* sortType price，distance，date三类，用于按照价格、距离、时间排序，默认为价格


response data:

* items, json array, 团拍对象集合，每条团拍对象包括如下信息:
    * shotId, long, 活动id
    * picUrl, string, 活动剧照的url
    * price, int, 拍摄价格（单位，元）
    * title, string, 活动名称
    * intro, string, 活动简介
    * startTime, long, 活动开始时间, unix时间戳, 精确到毫秒
    * endTime, long, 活动结束时间, unix时间戳, 精确到毫秒
    * minNumber, int, 最少参团人数或家庭数
    * maxNumber, int, 最大参团人数或家庭数
    * location, string, 拍摄地点
    * photographerCount, int, 摄影师数量
    * likeCount, int, 点赞数量
    * commentCount, int, 评论数量

