# introduction
snapshot-api

# compile
Install the sms sdk jar into local maven repository:

```
mvn install:install-file -Dfile=lib/CCP_REST_SDK_JAVA_v2.7r.jar -DgroupId=com-cloopen-rest -DartifactId=rest-sdk -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
```

```
cd ~/project/ub-lab/tagbase/src/utils && mvn install -DskipTests \
&& cd ~/project/snapshot/snapshot-api && mvn clean compile -DskipTests
```
# configuration
Default configuration file is 'app.properties', which will be auto loaded, and reloaded every 10s.

http port is 8080.
# protocol
Response of server:
<pre>
{
	"succeed" : true  | false,
	"message" : "message...",
	"data" : {para1:$para1,para2:$para2...}
}
</pre>
such as:
<pre>
{succeed=true, message='', data={}}
{succeed=false, message='签名校验失败', data={}}
# web
Use spring mvc framework.

# database
[database](./database-design.md)
# security
基于用户password做签名，实现安全认证。
<pre>
$key=md5Hex(md5Hex($password));
$signature = hmacSha1($key, $date + $time);
</pre>
其中，

* $date为'yyyyMMdd'的日期字符串，日期为计算密钥时的日期
* $password为用户密码
* md5Hex()是用于将字符串编码为md5字符串的方法
* $key为用于hmacSha1加密消息的密钥
* $date和$key可以缓存到客户端，减少密钥的计算频次
* $time为计算签名时的时间，long型，采用utc时间
* $signature为消息签名
服务方根据请求中的username找到对应的password，同样计算密钥、做签名，如果signature相等则认证通过。
## login
<pre>POST {username:$username,date:$date,time:$time,sig:$signature}</pre>
or
<pre>POST {phoneNum:phoneNum,date:$date,time:$time,sig:$signature}</pre>
## register
1, send sms auth code
auth code的id是 sms_authCode_{phoneNum}

2. submit register
<pre>POST {username:$username,phoneNum:phoneNum,password:$password,$captchaId:$captchaId,$captcha:$captcha}</pre>

# test
<pre>
curl -d "username=test&password=098f6bcd4621d373cade4e832627b4f6" http://localhost:8080/user/login
curl -d "username=test&password=hi&phoneNum=13699996666&captcha=9yu8" http://localhost:8080/user/register
curl -d "username=test&password=hi&phoneNum=13699996666" http://localhost:8080/user/register
wget 'http://localhost:8080/captcha?width=145&height=36&fontSize=22'
</pre>

# problems
## hibernate
Table entity must have a default constructor, or else throw exception:
<pre>nested exception is org.hibernate.InstantiationException: No default constructor for entity</pre>

Configuration problem:
<pre>Unable to locate Spring NamespaceHandler for XML schema namespace [http://java.sun.com/xml/ns/persistence]</pre>
It seems to be you are trying to load persistence.xml as a Spring configuration file (probably, you added it to the file list when calling constructor of FileSystemXmlApplicationContext). You shouldn't do it, because persistence.xml is not a Spring configuration file.
It's a JPA configuration file, which should be placed into META-INF folder in your classpath.

# appendix
## mysql
Tinyint,占用1字节的存储空间,取值范围是：带符号的范围是-128到127.

## spring
document root default to  AbstractEmbeddedServletContainerFactory.COMMON_DOC_ROOTS = { "src/main/webapp", "public","static"}
