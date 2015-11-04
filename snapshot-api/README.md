# introduction
snapshot-api

# configuration
Default configuration file is 'app.properties', which will be auto loaded, and reloaded every 10s.

http port is 8080.
# web
Use spring mvc framework.

# database
## user

| column name  | type     |desc          |
| ------------ | -------- | ------------ |
| id           | bigint   | auto increment   |
| name         | char 50  | user name        |
| password     | char 32  | password         |
| mobile       | bigint 11| phone number     |
| role         | tinyint  | user role: 1, admin; 2, user; 3, photographer |
| create_time  | timestamp | user created time |
| update_time  | timestamp | user info updated time |
| last_login_ip | varchar(40) | last login ip |
| last_login_time | timestamp | last login time |
| login_times  | int      | login times      |

# security
基于用户uid和pwd做签名，实现安全认证。
<pre>
$key=md5Hex($date+md5Hex($password));
$signature = hmacSha1($key, $date + $time);
</pre>

## login
<pre>POST {username:$username,password:$password,date:$date,time:$time,sig:$sig}</pre>
or
<pre>POST {phoneNum:phoneNum,password:$password,date:$date,time:$time,sig:$sig}</pre>
## register
1, get captcha
Captcha number is 5 in default.
<pre>GET captcha?width=145&height=36&fontSize=22</pre>
response
<pre>{"succeed":true,"message":"","data":{"captchaId":"captcha-4b2ed8e7-36e3-437d-9672-2a116acab0bd","captcha":"/9j/4AAQSkZJRgABAgAA.."}}</pre>
captcha jpg is encoded to base64 string.

2, submit register
<pre>POST {username:$username,phoneNum:phoneNum,password:$password,$captchaId:$captchaId,$captcha:$captcha}</pre>

# test
<pre>
curl -d "username=admin&password=abc" http://localhost:8080/user/login
curl -d "username=admin&password=e6d2cbe9f7f04256e3d6466d4a770990" http://localhost:8080/user/login
curl -d "username=admin&password=hi&phoneNum=13699996666&captcha=9yu8" http://localhost:8080/user/register
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

