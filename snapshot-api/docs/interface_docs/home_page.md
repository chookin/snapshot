# 首页接口
## 获取推荐位广告，推荐特色服务
method: GET

path: /home/recommendedSpecialShot

request paras:

* uid, long, 用户id
* longitude, double, 经度，可选
* latitude, double, 维度，可选

response data:

* shotId, long, 活动id
* title, string, 活动名称
* intro, string, 活动简介
* picUrl, string, 活动剧照的url
* price, int, 拍摄价格（单位，元）

响应示例：

    {
        id=2,
        succeed=true,
        message='',
        data={
            intro=超低价位，为您提供星球大战戏服新体验,
            picUrl=http://111.13.47.169:8080/upload/image/custom/star-wars-1.jpg,
            price=100,
            shotId=1,
            title=星球大战剧照
        },
        time=1453631403177
    }
## 获取推荐的摄影师活动
method: GET

path: /home/recommendedShots

request paras:

* uid, long, 用户id
* longitude, double, 经度，可选
* latitude, double, 维度，可选
* page, int, 请求的页数
* step, int, 每页多少条

response data:

* items, json array, 活动对象集合，每条活动对象包括如下信息:
    * shotId, long, 活动id
    * picUr, string, 活动剧照的url
    * publishDate, long, 活动发布时间,unix时间戳
    * location, string, 拍摄地点
    * price, int, 拍摄价格（单位，元）
    * photographerId, long, 摄影师id
    * avatarUrl, string, 摄影师头像的url
    * nickname, string, 摄影师昵称
    * appointmentCount, int, 预约数量
    * likeCount, int, 点赞数量
    * commentCount, int, 评论数量
