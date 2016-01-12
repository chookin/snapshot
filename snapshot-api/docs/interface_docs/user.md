# 用户管理接口
## 用户注册
path: /user/register

paras:

* phoneNum, string, 手机号码
* username, string, 用户名
* password, string, 用户原始密码经md5加密后的字符串
* authCode, string, 手机验证码

<strong>注意：</strong>请先调用手机验证马接口发送手机验证码。

response:

    {
        id = 4,
        succeed = true,
        message = '',
        data = {},
        time = 1452583841855
    }
## 用户登录
path: /user/login
### 基于用户名的登录
paras:

* username, string, 用户名

<pre>POST {username:$username,date:$date,time:$time,sig:$signature}</pre>
说明：因为签名校验时已经使用了密码信息，因此不需要再传递密码的参数。

response:

    {
        id = 5,
        succeed = true,
        message = '',
        data = {
            uid = 1,
            phoneNum = 13426198753,
            username = test
        },
        time = 1452583842004
    }
### 基于手机号的登录
paras:

* phoneNum, string, 手机号码

<pre>POST {phoneNum:phoneNum,date:$date,time:$time,sig:$signature}</pre>

response:

    {
        id = 1,
        succeed = true,
        message = '',
        data = {
            uid = 3,
            phoneNum = 13811245934,
            username = jacob
        },
        time = 1452583840839
    }

## 获取用户基本信息
method: Http.GET  
path: /user/info/get

paras:

* uid, long, 用户id

response:

    {id=$id, succeed=true, message='',data={
            uid:$uid
            username:$name,
            phoneNum:$phoneNum,
            area:$area,
            avatar:$avatar,
            sex:$sex,
        },
    time:$time
    }

## 获取用户名
method: Http.GET  
path: /user/name/

paras:

* uid, long, 用户id

response:

    {
        id = 3,
        succeed = true,
        message = '',
        data = {
            username = test
        },
        time = 1452583841309
    }

## 修改用户名
path: /user/name/mod

paras:

* uid, long, 用户id
* newName, string, 新的用户名

response:

    {
        id = 7,
        succeed = true,
        message = '',
        data = {},
        time = 1452583842326
    }
    
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

response:

    {
        id = 2,
        succeed = true,
        message = '',
        data = {
            area = null,
            uid = 1,
            sex = null,
            phoneNum = 13426198753,
            avatar = upload/avatar/20160112/1-100-100.png,
            username = test
        },
        time = 1452583841138
    }
