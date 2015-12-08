# 用户管理
## 用户注册
path: /user/register
paras:

* phoneNum, string, 手机号码
* username, string, 用户名
* password, string, 用户原始密码经md5加密后的字符串
* authCode, string, 手机验证码

<strong>注意：</strong>请先调用手机验证马接口发送手机验证码。
## 用户登录
path: /user/login
### 基于用户名的登录
paras:

* username, string, 用户名

<pre>POST {username:$username,date:$date,time:$time,sig:$signature}</pre>
说明：因为签名校验时已经使用了密码信息，因此不需要再传递密码的参数。
### 基于手机号的登录
paras:

* phoneNum, string, 手机号码

<pre>POST {phoneNum:phoneNum,date:$date,time:$time,sig:$signature}</pre>
## 获取用户基本信息
path: /user/info/get
paras:

* uid, long, 用户id

response:

```
{id=$id, succeed=true, message='', data={
uid:$uid
username:$name,
phoneNum:$phoneNum,
area:$area,
avatar:$avatar,
sex:$sex,
}}
```

## 获取用户名
path: /user/name/mod
paras:

* uid, long, 用户id

## 修改用户名
path: /user/name/mod
paras:

* uid, long, 用户id
* newName, string, 新的用户名

## 修改密码
path: /user/password/mod
paras:

* phoneNum, string, 手机号码
* password, string, 用户新密码经md5加密后的字符串
* authCode, string, 手机验证码

## 修改头像
path: /user/avatar/mod
paras:

* phoneNum, string, 手机号码
* avatar, multipartFile, 头像文件
