# 图片管理接口
## 上传图片文件
method: POST

path: /image/upload

request paras:

* uid, long, 用户id
* img, multipartFile, 图片文件

response data:

* filename, string, 上传到服务器后，图片文件的服务器路径
* url, string, 图片文件的url