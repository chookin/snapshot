# 摄影师帐户管理
## 申请成为摄影师
path: /photographer/toBecome

paras:

* uid, long, 用户id
* realName, string, 真实姓名
* idNum, string, 身份证号
* idImgPath, string, 手持身份证正面照片的服务器路径
* cameraId, string, 器材编号
* cameraModel, string, 器材型号
* lensModel, string, 镜头型号
* cameraImgPath, string, 器材照片的服务器路径

<strong>注意：</strong>请使用[图片管理接口](./image_manager.md#上传图片文件)上传身份证照片及器材照片。

## 修改摄影师的信息
path: /photographer/info/mod

paras:

* uid, 用户ID
* newName, 昵称
* region, 服务城市
* desire, 擅长领域
* shootNum, 拍摄张数
* shootHour, 拍摄时长
* truingNum, 精修底片的张数
* printNum, 相片冲印的张数
* clothing, 服装
* makeup, 化妆

## 获取指定摄影师的套餐详情
path: /photographer/plan/get

paras:

* uid, 用户ID
* gid, 摄影师id

## 获取指定摄影师的器材详情
path: /photographer/cameras/get

paras:

* uid, 用户ID
* gid, 摄影师id