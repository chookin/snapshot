# Configuration

* create configuration file.
<pre>cp support-files/my-large.cnf ~/local/mysql/etc/my.cnf</pre>
* edit 'my.cnf', check to use available port.

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

Notice, full text replacement command of vi:    `:g/work/s//yourname/g`
    
# init database
<pre>scripts/mysql_install_db --defaults-file=etc/my.cnf</pre> or <pre>mysqld --initialize</pre>

# start mysql
* start mysql, ensure the port is usable.<pre>bin/mysqld_safe  --defaults-file=etc/my.cnf &</pre>
* set the root password. <pre>bin/mysqladmin --defaults-file=etc/my.cnf -u root password</pre>
* view users, create database, and create user. 

# grant privileges
<pre>
bin/mysql --defaults-file=etc/my.cnf -u root -p
mysql> select user, host, password from mysql.user;
mysql> create database if not exists `snapshot` default character set utf8;
mysql> grant all on snapshot.* to 'snap'@'localhost' identified by 'snap_cm';
</pre>

# stop mysql
* stop mysql. <pre>bin/mysqladmin --defaults-file=etc/my.cnf -uroot -p shutdown</pre>