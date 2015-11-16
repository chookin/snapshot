package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 11/10/15.
 */
public class SMSControllerTest extends WebAppTest{

    @Test
    public void testSendAuthCode() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("sms/authCode/send")
                .setKey(SigInterceptor.defaultKey)
                .add("phoneNum", ConfigManager.getLong("test.phoneNum"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}