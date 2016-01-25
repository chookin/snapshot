# 特色服务接口
## 创建特色服务
method: POST

path: /specialShot/create

request paras:

* uid, long, 用户id
* title, string, 活动名称
* intro, string, 活动简介
* summary, string, 活动内容
* price, int, 拍摄价格（单位，元）
* location, string, 拍摄的地点
* service, string, 服务内容，如：>60张拍摄，10张精修
* sculpt, string, 造型，如：1组
* photographers, string, 摄影师id集合，格式：[id1,id2,...]
* multipart image, 参数名称随意，一个文件对应一个'multipart image' para，可以由多个'multipart image' para

response data:

* shotId, long, 生成的活动的id

## 获取特色服务的列表
method: GET

path: /specialShot/get

request paras:

* uid, long, 用户id
* longitude, double, 经度，可选
* latitude, double, 维度，可选
* page, int, 请求的页数
* step, int, 每页多少条

response data:

* items, json array, 活动对象集合，每条活动对象包括如下信息:
    * shotId, long, 活动id
    * title, string, 活动名称
    * intro, string, 活动简介
    * picUr, string, 活动剧照的url
    * price, long, 拍摄价格（单位，元）

## 获取特色服务的详情
method: GET

path: /specialShot/detail

request paras:

* shotId, long, 活动id

response data:

* shotId, long, 活动id
* picUrls,json数组, 剧照图片url的集合, 格式[url1,url2,...]
* price, int, 拍摄价格（单位，元）
* title, string, 活动名称
* intro, string, 活动简介
* summary, string, 活动内容
* date, long, 活动时间, unix时间戳, 精确到毫秒
* photographers, string, 摄影师id集合，格式：[id1,id2,...]
* location, string, 拍摄的地点
* service, string, 服务内容
* sculpt, string, 造型，如：1组
* likeCount, int, 点赞数量
* commentCount, int, 评论数量
