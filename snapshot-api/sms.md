# 手机验证码
## 发送手机验证码
path：/sms/authCode/send

paras:

- phoneNum, string，手机号

请求示例：

1. prepare parameters:
    * phoneNum, 13426198753
    * time, 1447917334486
    * secret_key, f4a8yoxG9F6b1gUB
1. compute sig:

    <pre>
    str="POSThttp://111.13.47.167:8080/sms/authCode/sendphoneNum=13426198753time=1447917334486f4a8yoxG9F6b1gUB"
    sig=md5(URLEncoder.encode(str,"utf-8"))
    // => sig=72e42b10f73241140ad0cd05fcbc9b3c
    </pre> 
1. post data to server:

    <pre>
    method: post
    url: http://111.13.47.167:8080/sms/authCode/send
    data: phoneNum=13426198753&time=1447917334486&sig=72e42b10f73241140ad0cd05fcbc9b3c
    </pre>