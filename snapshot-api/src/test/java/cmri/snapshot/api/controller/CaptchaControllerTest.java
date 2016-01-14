package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 11/5/15.
 */

public class CaptchaControllerTest extends WebAppTest {
    @Test
    public void testCaptcha() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("captcha")
                .add("width", 145)
                .add("height", 36)
                .add("fontSize", 22)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}