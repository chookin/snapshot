package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;

/**
 * Created by zhuyin on 11/5/15.
 */

public class CaptchaControllerTest extends WebAppTest {
    @Test
    public void testCaptcha() throws Exception {
        ResponseMessage r = new TestRestTemplate().getForObject(base + port+"/captcha", ResponseMessage.class);
        System.out.println(r);
    }
}