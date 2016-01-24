# 概述
选择HTTP协议进行通信。为了具有安全性，客户端发出的请求需要携带签名信息。
# URL
```
http://{domain}:{port}/{path}?{query_string}
```

说明：

* {domain}为服务端的hostname或ip；
* {port}为服务端的端口；
* {path}为操作的资源；
* {query_string}由通用参数部分和具体API调用参数部分组成；
* {query_string}中的key/value对都必须是UTF-8编码。
* 对于GET请求，{query_string}必须放在QUERY参数中传递，即放在“?”后面；
* 对于POST请求，{query_string}放在POST参数中传递；

# 签名算法
所有api均需签名。签名算法如下：

* 获取请求的http method；
* 获取请求的url，包括host和sheme，但不包括query_string的部分；
* 将所有参数（包括GET或POST的参数，但不包含签名字段）格式化为“key=value”格式，如“k1=v1”、“k2=v2”、“k3=v3”；
* 将格式化好的参数键值对以字典序升序排列后，拼接在一起，如“k1：v1，k2：v2，k3：v3”，并将http method和url按顺序拼接在这个字符串前面；
* 在拼接好的字符串末尾追加上应用的secret_key，并进行urlencode，形成base_string；
* 上述字符串的MD5值即为签名的值：

```
sig=md5(urlencode($http_method$url$k1=$v1$k2=$v2$k3=$v3$secret_key))
```

注意：

* 计算签名时，
对于允许匿名访问的操作（即用户不可知或不存在的情况可以进行的操作，例如用户注册、发送手机验证码以及所有的Http.GET方法），基于服务端和客户端已知的默认key做签名。
```
secret_key=f4a8yoxG9F6b1gUB
```

对于非匿名访问，基于用户密码的两次md5值作为key做签名。
```
secret_key=md5(md5({password}))
```

举例：

```
url [POST]：
http://192.168.80.131:8080/user/register?username=test1447292143901&phoneNum=13426198759&password=098f6bcd4621d373cade4e832627b4f6&authCode=9999&time=1447292143902
secret_key：8c89b85dc3e8983c75744183c6d4451f
```

则参于签名的字符串：

```
POSThttp://192.168.80.131:8080/user/registerauthCode=9999password=098f6bcd4621d373cade4e832627b4f6phoneNum=13426198759time=1447292143902username=test14472921439018c89b85dc3e8983c75744183c6d4451f
```

PHP示例代码：

<pre>
/**
* $secret_key //应用的secret key
* $method //GET或POST
* $url url
* $arrContent //请求参数(包括GET和POST的所有参数，不含计算的sig)
* return $sig string
**/
function genSig($secret_key, $method, $url, $arrContent) {
    $gather = $method.$url;
    ksort($arrContent);
    foreach($arrContent as $key => $value) {
        $gather .= $key.'='.$value;
    }   
    $gather .= $secret_key;
    $sig = md5(urlencode($gather));
    return $sig;
}   
 
$secret_key = '8c89b85dc3e8983c75744183c6d4451f';
$method = 'POST';
$url = 'http://192.168.80.131:8080/user/login';
$arrContent = array(
    'phoneNum'=>'19911119999',
    'time'=>1447292143,
);  
$sig = genSig($secret_key, $method, $url, $arrContent);
</pre>

# 通用参数
以下参数是所有API都会用到的统一系统级参数。

参数名称 | 类型	  | 是否必需 | 描述
------ | -------- | -----  | ----
time   | long     | 是 | 用户发起请求时的unix时间戳, 精确到毫秒
sig    | string   | 是 | 签名值

# JSON响应输出格式
响应输出内容符合以下规范：

* id字段，long型，由web server生成，返回给用户方便问题追查与定位;
* succeed字段，bool型，标识是否HTTP API调用失败；
* message字段表示HTTP API返回的提示信息，如当调用失败时的错误消息；
* data属性是一个二级json，由n个包含key和value属性的对象组成；表示API返回的数据内容;
* time, long型，web server生成响应消息的unix时间戳, 精确到毫秒。

格式：

```
{
  "id": 1,
  "succeed" : true  | false,
  "message" : "message...",
  "data" : {para1:$para1,para2:$para2...},
  "time" : 1449647018735
}
```

# 约定

* 默认HTTP method为POST；
* 默认在调用成功时，响应的json为：
```
{id:$id, succeed:true, message='', data:{}, time:$time}
```
其中，

    * $id是web server实际返回的响应消息id;
    * $time是web server生成响应消息的unix时间戳, 精确到毫秒。

# 参考

* [百度rest api规范](http://developer.baidu.com/wiki/index.php?title=docs/cplat/push/api)
* [微信公众平台开发者文档](http://mp.weixin.qq.com/wiki/home/index.html)