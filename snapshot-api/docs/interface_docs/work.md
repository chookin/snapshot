# 作品接口
## 添加作品
path: /work/add

paras:

* uid, long, 添加作品的用户的id
* name, string, 作品名称
* location, string, 拍摄地点
* imgs, MultipartFile[], 作品照片, 可不指定，即只是创建作品，暂不传照片

## 添加作品的照片
path: /work/photos/append

paras:

* uid, long, 添加作品照片的用户的id
* workId, long, 作品id
* img, MultipartFile, 照片

## 删除作品的照片
path: /work/photos/delete

paras:

* uid, long, 用户id
* workId, long, 作品id
* photoId, long, 照片id

## 根据作品id获取作品
method: Http.GET  
path: /work/get

paras:

* workId, long, 作品id

## 获取指定摄影师的作品集合
method: Http.GET  
path: /work/getWorks

paras:

* gid, long, 摄影师id
