# introduction
snapshot-api

# compile and package

1. mvn install cmri-utils.
<pre>mvn install -DskipTests</pre>
2. Install the sms sdk jar into local maven repository.
<pre>mvn install:install-file -Dfile=lib/CCP_REST_SDK_JAVA_v2.7r.jar -DgroupId=com-cloopen-rest -DartifactId=rest-sdk -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true </pre>
3. compile.
<pre>mvn clean compile -DskipTests</pre>

# deploy

1. Configure properties and make dirs:
    * Manual create the document root dir 'chu.server.documentRoot' configured by configuration file 'application.properties';
    * Modify 'log4j.appender.logfile.File' of configuration file 'log4j.properties';
    * Modify 'server.hostname' of configuration file 'app.properties' to the user ip.
1. Configure and start mysql.
    * create configuration file. <pre>cp support-files/my-large.cnf ~/local/mysql/etc/my.cnf</pre>
    * edit 'my.cnf'.   
    
    <pre>
    $ vi my.cnf         
    # The following options will be passed to all MySQL clients
    [client]
    #password       = your_password
    port            = 3306
    socket          = /home/work/local/mysql/var/mysqld.sock      
    # Here follows entries for some specific programs
    [mysqld_safe]
    socket          = /home/work/local/mysql/var/mysqld.sock
    nice            = 0       
    # The MySQL server
    [mysqld]
    port            = 3306
    socket          = /home/work/local/mysql/var/mysql.sock
    local-infile    = 0
    user            = work
    pid-file        = /home/work/local/mysql/var/mysqld.pid
    socket          = /home/work/local/mysql/var/mysqld.sock
    port            = 3306
    basedir         = /home/work/local/mysql
    datadir         =/home/work/data/mysql
    tmpdir          = /tmp
    skip-external-locking
    key_buffer              = 16M
    max_allowed_packet      = 16M
    thread_stack            = 192K
    thread_cache_size       = 8
    myisam-recover         = BACKUP
    query_cache_limit       = 1M
    query_cache_size        = 16M
    expire_logs_days        = 10
    max_binlog_size         = 100M
    # Replication Master Server (default)
    # binary logging is required for replication
    server-id       = 1
    log-bin         = mysql-bin
    [mysqldump]
    quick
    quote-names
    max_allowed_packet      = 16M
    [isamchk]
    key_buffer              = 16M
    </pre>
    * init database. <pre>scripts/mysql_install_db --defaults-file=etc/my.cnf</pre>
    * start mysql, ensure the port is usable.<pre>bin/mysqld_safe  --defaults-file=etc/my.cnf &</pre>
    * set the root password. <pre>bin/mysqladmin --defaults-file=etc/my.cnf -u root password</pre>
    * view users, create database, and create user. 
    
    <pre>
    bin/mysql --defaults-file=etc/my.cnf -u root -p
    mysql> select user, host, password from mysql.user\G;
    mysql> create database if not exists `snapshot` default character set utf8;
    mysql> grant all on snapshot.* to 'snap'@'localhost' identified by 'snap_cm';
    </pre>
1. Configure and start redis
<pre>
bin/redis-server redis.conf
</pre>

# configuration
Default configuration file is 'app.properties', which will be auto loaded, and reloaded every 10s.

* Default http port is 8080.

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
