package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.dao.RedisHandler;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

/**
 * Created by zhuyin on 11/5/15.
 */
public class UserControllerTest extends WebAppTest{
    @Autowired
    private UserRepository userRepository;
    @Test
    public void testLoginByName() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("user/login")
                .add("username", "test")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
    @Test
    public void testLoginByPhone() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("user/login")
                .add("phoneNum", ConfigManager.get("test.phoneNum"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
    /**
     * Require, login redis-cli, and then set authCode for phone:
         $ redis-cli
         127.0.0.1:6379> keys *
         (empty list or set)
         127.0.0.1:6379> set sms_authCode_13911119999 9999
         OK
         127.0.0.1:6379> keys *
         1) "sms_authCode_13426198759"

     * @throws Exception
     */
    @Test
    public void testRegister() throws Exception {
        long mobile = 13911119999L;
        String code = "9999";
        userRepository.deleteByMobile(mobile);
        RedisHandler.instance().set(SMSController.getAuthCodeId(mobile), code, ConfigManager.getInt("sms.authCode.expireMinutes") * 60);
        ResponseMessage response = rest.reset()
                .setPath("user/register")
                .setKey(SigInterceptor.defaultKey)
                .add("username", "test" + System.currentTimeMillis())
                .add("phoneNum", mobile)
                .add("password", DigestUtils.md5Hex("test"))
                .add("authCode", code)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testModAvatar() throws Exception {
        String file = "/home/work/tmp/738b4710b912c8fcae1799cdfe039245d78821cd.jpg";
        ResponseMessage response = rest.reset()
                .setPath("user/avatar/mod")
                .add("phoneNum", ConfigManager.getLong("test.phoneNum"))
                .add("avatar", new FileSystemResource(new File(file)))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetInfo() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("user/info/get")
                .add("phoneNum", ConfigManager.getLong("test.phoneNum"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testModName() throws Exception {
        modName("test_mod");
        modName("test");
    }
    void modName(String name){
        ResponseMessage response = rest.reset()
                .setPath("user/name/mod")
                .add("phoneNum", ConfigManager.getLong("test.phoneNum"))
                .add("newName", name)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}