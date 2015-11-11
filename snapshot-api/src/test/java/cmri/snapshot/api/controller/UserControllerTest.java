package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.configuration.ConfigManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 11/5/15.
 */
public class UserControllerTest extends WebAppTest{

    @Test
    public void testLogin() throws Exception {
        ResponseMessage response = rest.clear()
                .setPath("user/login")
                .add("username", "test")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    /**
     * Require, login redis-cli, and then set authCode for phone:
         $ redis-cli
         127.0.0.1:6379> keys *
         (empty list or set)
         127.0.0.1:6379> set sms_authCode_13426198759 9999
         OK
         127.0.0.1:6379> keys *
         1) "sms_authCode_13426198759"

     * @throws Exception
     */
    @Test
    public void testRegister() throws Exception {
        ResponseMessage response = rest.clear()
                .setPath("user/register")
                .setKey(SigInterceptor.defaultKey)
                .add("username", "test" + System.currentTimeMillis())
                .add("phoneNum", "13426198759")
                .add("password", DigestUtils.md5Hex(ConfigManager.get("test.password")))
                .add("authCode", "9999")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}