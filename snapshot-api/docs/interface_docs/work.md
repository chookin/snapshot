# 作品接口
## 添加作品
method: POST

path: /work/add

request paras:

* uid, long, 添加作品的用户的id
* name, string, 作品名称
* location, string, 拍摄地点
* img, MultipartFile[], 作品照片, 可不指定，即只是创建作品，暂不传照片

## 添加作品的照片
method: POST

path: /work/photos/append

request paras:

* uid, long, 添加作品照片的用户的id
* workId, long, 作品id
* img, MultipartFile, 照片

## 删除作品的照片
method: POST

path: /work/photos/delete

request paras:

* uid, long, 用户id
* workId, long, 作品id
* photoId, long, 照片id

## 根据作品id获取作品
method: GET

path: /work/get

request paras:

* workId, long, 作品id

## 获取指定摄影师的作品集合
method: GET

path: /work/getWorks

request paras:

* userId, long, 摄影师id
* page, int, 请求的页数
* step, int, 每页多少条

response data:

* works, json array, 作品集合，每条作品对象包括如下信息：
    * workId, long, 作品id
    * picUrl, string, 作品的url
    * likeCount, int, 点赞数量
    * commentCount, int, 评论数量


