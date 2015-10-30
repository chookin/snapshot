# introduction
snapshot-api

# configuration
Default configuration file is 'app.properties', which will be auto loaded, and reloaded every 10s.

http port is 8080.
# web
Use spring mvc framework.

# database

# test
curl -d "userName=admin&password=abc" http://localhost:8080/user/login
curl -d "userName=admin&password=hi&phoneNum=13699996666" http://localhost:8080/user/register
curl -d "userName=test&password=hi&phoneNum=13699996666" http://localhost:8080/user/register
wget 'http://localhost:8080/captcha?width=145&height=36&fontSize=22'

