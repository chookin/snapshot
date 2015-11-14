package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 11/10/15.
 */
public class SMSControllerTest extends WebAppTest{

    @Test
    public void testSendAuthCode() throws Exception {
        ResponseMessage response = rest.clear()
                .setPath("sms/authCode/send")
                .setKey(SigInterceptor.defaultKey)
                .add("phoneNum", 13426198753L)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}