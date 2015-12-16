# problems
## hibernate
Table entity must have a default constructor, or else throw exception:
<pre>nested exception is org.hibernate.InstantiationException: No default constructor for entity</pre>

Configuration problem:
<pre>Unable to locate Spring NamespaceHandler for XML schema namespace [http://java.sun.com/xml/ns/persistence]</pre>
It seems to be you are trying to load persistence.xml as a Spring configuration file (probably, you added it to the file list when calling constructor of FileSystemXmlApplicationContext). You shouldn't do it, because persistence.xml is not a Spring configuration file.
It's a JPA configuration file, which should be placed into META-INF folder in your classpath.

Update exceptions:
<pre>org.springframework.dao.InvalidDataAccessApiUsageException: Executing an update/delete query; nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query</pre>
Need use @Transactional.

# appendix
## mysql
如果是mac,

* 采用brew安装
<pre>brew install mysql</pre>
* 添加修改mysql配置
<pre>mysqld --help --verbose | more (查看帮助, 按空格下翻)</pre>
你会看到开始的这一行(表示配置文件默认读取顺序)
<pre>
Default options are read from the following files in the given order:
/etc/my.cnf /etc/mysql/my.cnf /usr/local/etc/my.cnf ~/.my.cnf
</pre>
通常这些位置是没有配置文件的, 所以要自己建一个

<pre>
# 用这个可以找到样例.cnf
ls $(brew --prefix mysql)/support-files/my-*
# 拷贝到第一个默认读取目录
cp /usr/local/opt/mysql/support-files/my-default.cnf /etc/my.cnf
# 此后按需修改my.cnf
</pre>

* 操作
可用使用mysql的脚本启停,也可借助brew
<pre>
brew services start mysql
brew services stop mysql
</pre>

Notice:

* Tinyint,占用1字节的存储空间,取值范围是：带符号的范围是-128到127.
* Int range:[-2^31,2^31-1] [-2147483648,2147483647], so using bigint for phone number.
* The length of a string's md5 output is 32.
* TIMESTAMP values are converted from the current time zone to UTC for storage, and converted back from UTC to the current time zone for retrieval. (This occurs only for the TIMESTAMP data type, not for other types such as DATETIME.) More notably:If you store a TIMESTAMP value, and then change the time zone and retrieve the value, the retrieved value is different from the value you stored.
* Timestamps in MySQL generally used to track changes to records, and are often updated every time the record is changed. If you want to store a specific value you should use a datetime field.
* BIGINT[(M)] [UNSIGNED] [ZEROFILL] 大整数。带符号的范围是-9223372036854775808到9223372036854775807。无符号的范围是0到18446744073709551615。M指示最大显示宽度。最大有效显示宽度是255。显示宽度与存储大小或类型包含的值的范围无关

## spring
document root default to  AbstractEmbeddedServletContainerFactory.COMMON_DOC_ROOTS = { "src/main/webapp", "public","static"}

* [Spring Framework Reference Documentation](http://docs.spring.io/spring/docs/4.2.3.RELEASE/spring-framework-reference/htmlsingle)
* [Spring Boot Reference Guide](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)