# introduction
snapshot-api is developed on jdk1.8, using spring mvc framework.

# compile and package
1. Install the sms sdk jar into local maven repository.
<pre>mvn install:install-file -Dfile=lib/CCP_REST_SDK_JAVA_v2.7r.jar -DgroupId=com-cloopen-rest -DartifactId=rest-sdk -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true </pre>
1. compile.
<pre>mvn clean compile -DskipTests</pre>
1. package
<pre>mvn package -DskipTests</pre>

# deployment base services

1. Configure properties and make dirs:
    * Manual create the document root dir 'chu.server.documentRoot' configured by configuration file 'application.properties';
    * Modify 'log4j.appender.logfile.File' of log4j.properties;
    * Modify 'server.hostname' of app.properties to the server ip.
    * Modify 'spring.datasource.url' of application.properties.
    * Modify "hibernate.connection.url" of persistence.xml.
1. Configure and start mysql.
1. Configure and start redis.

# configuration
Default configuration files are 'app.properties' and 'application.properties', which will be auto loaded, and reloaded every 10s.

* Default http port is 8080.

# 接口规范
[接口规范](./docs/interface_specification.md)
