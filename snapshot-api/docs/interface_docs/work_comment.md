# 作品评论接口
## 添加作品评论
method: POST

path: /workComment/add

request paras:

* uid, long, 发布评论的用户id
* workId, long, 作品id
* parent, long, 父评论id，可选
* content, string, 评论内容

response data:

* comment, json object, 评论信息对象

