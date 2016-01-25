# 摄影师帐户管理接口
## 申请成为摄影师
method: POST

path: /photographer/toBecome

request paras:

* uid, long, 用户id
* realName, string, 真实姓名
* idNum, string, 身份证号
* idImgPath, string, 手持身份证正面照片的服务器路径
* cameraId, string, 器材编号
* cameraModel, string, 器材型号
* lensModel, string, 镜头型号
* cameraImgPath, string, 器材照片的服务器路径

<strong>注意：</strong>请使用[图片管理接口](./image.md#上传图片文件)上传身份证照片及器材照片。

## 获取摄影师的基本资料
method: GET

path: /photographer/info/get

request paras:

* userId, long, 摄影师id

response data:

* photographerId, 摄影师id
* priceTendency, 身价走势，json array形式，[{month:**,price:**},...]（返回最近12次的）
* serveCity, string, 服务城市
* skill, string, 擅长领域
* intro, string, 摄影师简介
* appointmentCount, int, 预约数量
* collectCount, int, 收藏数量
* likeCount, int, 点赞数量

## 修改摄影师的资料
method: POST

path: /photographer/info/mod

request paras:

* uid, long, 用户ID
* newName, string, 昵称
* region, string, 服务城市
* desire, string, 擅长领域
* shootNum, int, 拍摄张数
* shootHour, int, 拍摄时长
* truingNum, int, 精修底片的张数
* printNum, int, 相片冲印的张数
* clothing, string, 服装
* makeup, string, 化妆

## 获取指定摄影师的套餐详情
method: GET

path: /photographer/plan/get

request paras:

* userId, long, 摄影师id

## 获取指定摄影师的器材详情
method: GET

path: /photographer/cameras/get

request paras:

* userId, long, 摄影师id