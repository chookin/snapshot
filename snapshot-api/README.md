# introduction
snapshot-api is developed on jdk1.8, using spring mvc framework.

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
    * Modify 'log4j.appender.logfile.File' of log4j.properties;
    * Modify 'server.hostname' of app.properties to the user ip.
    * Modify 'spring.datasource.url' of application.properties.
    * Modify "hibernate.connection.url" of persistence.xml.
1. Configure and start mysql.
    * create configuration file.
    <pre>cp support-files/my-large.cnf ~/local/mysql/etc/my.cnf</pre>
    * edit 'my.cnf', check to use available port. Notice, full text replacement command of vi:<pre>:g/work/s//yourname/g</pre>
        
    <pre>
    $ vi my.cnf         
    # The following options will be passed to all MySQL clients
    [client]
    #password       = your_password
    port            = 23306
    socket          = /home/work/local/mysql/var/mysqld.sock   
    default-character-set=utf8
    # Here follows entries for some specific programs
    [mysqld_safe]
    socket          = /home/work/local/mysql/var/mysqld.sock
    nice            = 0       
    # The MySQL server
    [mysqld]
    port            = 23306
    socket          = /home/work/local/mysql/var/mysql.sock
    local-infile    = 0
    user            = work
    pid-file        = /home/work/local/mysql/var/mysqld.pid
    socket          = /home/work/local/mysql/var/mysqld.sock
    port            = 23306
    basedir         = /home/work/local/mysql
    datadir         = /home/work/data/mysql
    tmpdir          = /tmp
    skip-external-locking
    max_allowed_packet      = 16M
    thread_stack            = 192K
    thread_cache_size       = 8
    ###key_buffer              = 16M
    ###myisam-recover          = BACKUP
    query_cache_limit       = 1M
    query_cache_size        = 16M
    expire_logs_days        = 10
    max_binlog_size         = 100M
    collation-server        = utf8_unicode_ci
    init-connect            ='SET NAMES utf8'
    character-set-server    = utf8
    # 设置连接的等待时间为21天
    wait_timeout            = 1814400
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
    * init database. <pre>scripts/mysql_install_db --defaults-file=etc/my.cnf</pre> or <pre>mysqld --initialize</pre>
    * start mysql, ensure the port is usable.<pre>bin/mysqld_safe  --defaults-file=etc/my.cnf &</pre>
    * set the root password. <pre>bin/mysqladmin --defaults-file=etc/my.cnf -u root password</pre>
    * view users, create database, and create user. 
    
    <pre>
    bin/mysql --defaults-file=etc/my.cnf -u root -p
    mysql> select user, host, password from mysql.user;
    mysql> create database if not exists `snapshot` default character set utf8;
    mysql> grant all on snapshot.* to 'snap'@'localhost' identified by 'snap_cm';
    </pre>
    * stop mysql. <pre>bin/mysqladmin --defaults-file=etc/my.cnf -uroot -p shutdown</pre>
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
Default configuration files are 'app.properties' and 'application.properties', which will be auto loaded, and reloaded every 10s.

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

# 接口规范
[接口规范](./docs/interface_specification.md)

# test
<pre>
curl http://111.13.47.169:8080/materials/homepages?sig=e7481c3a91ac55e6b2e7c3b111a3aadb\&time=1450193196206\&username=test
curl http://111.13.47.169:8080/materials/categories?sig=87127b777f0ba7aabcc746d95e07e5ea\&time=1450193196206\&username=test

curl -d "username=test&time=1450231255519&sig=733690b34e95714079bf2874cf5fbd07" http://111.13.47.169:8080/user/login
#curl -d "username=test&password=hi&phoneNum=13699996666" http://localhost:8080/user/register
#wget 'http://localhost:8080/captcha?width=145&height=36&fontSize=22'
</pre>