# 摄影师帐户管理接口
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
path: /photographer/plan/get

paras:

* gid, long, 摄影师id

## 获取指定摄影师的器材详情
path: /photographer/cameras/get

paras:

* gid, long, 摄影师id