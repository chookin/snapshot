# 用户评论
## 对其他用户进行评论
path：/userComment/add

paras:

* uid, long, 用户id
* userId, long, 被评论用户的id
* parent, long, 父评论id
* content, string, 评论内容

## 获取对用户的评论
path：/userComment/getAboutUser

paras:

* userId, long, 用户id