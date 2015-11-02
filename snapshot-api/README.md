# introduction
snapshot-api

# configuration
Default configuration file is 'app.properties', which will be auto loaded, and reloaded every 10s.

http port is 8080.
# web
Use spring mvc framework.

# database

# test
curl -d "username=admin&password=abc" http://localhost:8080/user/login
curl -d "username=admin&password=e6d2cbe9f7f04256e3d6466d4a770990" http://localhost:8080/user/login
curl -d "username=admin&password=hi&phoneNum=13699996666&captcha=9yu8" http://localhost:8080/user/register
curl -d "username=test&password=hi&phoneNum=13699996666" http://localhost:8080/user/register
wget 'http://localhost:8080/captcha?width=145&height=36&fontSize=22'

# problems
## hibernate
table entity must have a default constructor, or else throw exception: "nested exception is org.hibernate.InstantiationException: No default constructor for entity".

Configuration problem: Unable to locate Spring NamespaceHandler for XML schema namespace [http://java.sun.com/xml/ns/persistence]
It seems to be you are trying to load persistence.xml as a Spring configuration file (probably, you added it to the file list when calling constructor of FileSystemXmlApplicationContext). You shouldn't do it, because persistence.xml is not a Spring configuration file.
It's a JPA configuration file, which should be placed into META-INF folder in your classpath.

