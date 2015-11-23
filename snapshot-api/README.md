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
    * create configuration file.
    <pre>cp support-files/my-large.cnf ~/local/mysql/etc/my.cnf</pre>
    * edit 'my.cnf', check to use available port. Notice, full text replacement command of vi:<pre>:g/work/s//yourname/g</pre>
        
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
    mysql> select user, host, password from mysql.user;
    mysql> create database if not exists `snapshot` default character set utf8;
    mysql> grant all on snapshot.* to 'snap'@'localhost' identified by 'snap_cm';
    </pre>
1. Configure and start redis
    * key paras
    
    <pre>
    # By default Redis does not run as a daemon. Use 'yes' if you need it.
    # Note that Redis will write a pid file in /var/run/redis.pid when daemonized.
    daemonize yes
    # Accept connections on the specified port, default is 6379.
    # If port 0 is specified Redis will not listen on a TCP socket.
    port 6379
    # The working directory.
    #
    # The DB will be written inside this directory, with the filename specified
    # above using the 'dbfilename' configuration directive.
    #
    # The Append Only File will also be created inside this directory.
    #
    # Note that you must specify a directory here, not a file name.
    dir /home/zhuyin/data/redis/
    </pre>
    
    * start 
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
</pre>

# web
Use spring mvc framework.

# 接口规范
[接口规范](./interface_specification.md)

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
Notice:

* Tinyint,占用1字节的存储空间,取值范围是：带符号的范围是-128到127.
* Int range:[-2^31,2^31-1] [-2147483648,2147483647], so using bigint for mobile.
* The length of a string's md5 output is 32.
* TIMESTAMP values are converted from the current time zone to UTC for storage, and converted back from UTC to the current time zone for retrieval. (This occurs only for the TIMESTAMP data type, not for other types such as DATETIME.) More notably:If you store a TIMESTAMP value, and then change the time zone and retrieve the value, the retrieved value is different from the value you stored.
* Timestamps in MySQL generally used to track changes to records, and are often updated every time the record is changed. If you want to store a specific value you should use a datetime field.
* BIGINT[(M)] [UNSIGNED] [ZEROFILL] 大整数。带符号的范围是-9223372036854775808到9223372036854775807。无符号的范围是0到18446744073709551615。M指示最大显示宽度。最大有效显示宽度是255。显示宽度与存储大小或类型包含的值的范围无关

## spring
document root default to  AbstractEmbeddedServletContainerFactory.COMMON_DOC_ROOTS = { "src/main/webapp", "public","static"}

* [Spring Framework Reference Documentation](http://docs.spring.io/spring/docs/4.2.3.RELEASE/spring-framework-reference/htmlsingle)
* [Spring Boot Reference Guide](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
