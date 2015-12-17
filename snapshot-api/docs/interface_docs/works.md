# 作品接口
## 添加作品
path: /works/add

paras:

* uid, long, 添加作品的用户的id
* name, string, 作品名称
* location, string, 拍摄地点
* imgs, MultipartFile[], 作品照片, 可不指定，即只是创建作品，暂不传照片

## 添加作品的照片
path: /works/photos/append

paras:

* uid, long, 添加作品照片的用户的id
* worksId, long, 作品id
* img, MultipartFile, 照片

## 删除作品的照片
path: /works/photos/delete

paras:

* uid, long, 用户id
* worksId, long, 作品id
* photoId, long, 照片id

## 根据作品id获取作品
path: /works/get

paras:

* worksId, long, 作品id

## 获取指定用户的作品集合
path: /works/getUserWorks

paras:

* userId, long, 获取哪个用户的作品
