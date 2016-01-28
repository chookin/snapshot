# 用户评论
## 对其他用户进行评论
method: POST

path：/userComment/add

request paras:

* uid, long, 用户id
* userId, long, 被评论用户的id
* parent, long, 父评论id，可选
* content, string, 评论内容

## 获取对用户的评论
method: GET  

path：/userComment/getAboutUser

request paras:

* userId, long, 被评论用户的id
* page, int, 分页请求的页数
* step, int, 分页请求的每页多少条

response data:

* comments, json array, 评论集合，每条评论包括如下信息：
    * commentatorId, long, 发布评论的用户id
    * avatar, string, 发布评论的用户头像url
    * nickname, string, 发布评论的用户的昵称
    * time, long, 评论时间，unix时间戳
    * content, string, 评论内容


