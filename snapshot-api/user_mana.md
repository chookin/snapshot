# 基本帐户
## 注册
path: /user/register
paras:

- phoneNum, string, 手机号码
- username, string, 用户名
- password, string, 用户原始密码经md5加密后的字符串
- authCode, string, 手机验证码

## 登录
path: /user/login
### 基于用户名的登录
paras:

- username, string, 用户名

说明：因为签名校验时已经使用了密码信息，因此不需要再传递密码的参数。
### 基于手机号的登录
- phoneNum, string, 手机号码

## 获取账号信息
path: /user/info/get
paras:

- phoneNum, string, 手机号码

response:

```
{id=$id, succeed=true, message='', data={
username:$name，
phoneNum:$phoneNum,
sex:$sex,
}}
```
## 修改用户名
path: /user/name/mod
paras:

- phoneNum, string, 手机号码
- newName, string, 新的用户名

## 重置密码
path: /user/password/mod
paras:

- phoneNum, string, 手机号码
- password, string, 用户新密码经md5加密后的字符串
- authCode, string, 手机验证码

## 修改头像
path: /user/avatar/mod
paras: