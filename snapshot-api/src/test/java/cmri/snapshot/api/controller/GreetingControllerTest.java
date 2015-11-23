package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhuyin on 10/28/15.
 */
public class GreetingControllerTest  extends WebAppTest {
    @Test
    public void testErrorPath() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("user/login1")
                .add("username", "test")
                .post();
        log(response);
        Assert.assertFalse(response.isSucceed());
    }
    @Ignore
    public void testEncoding(){
        ResponseMessage response = rest.reset()
                .setPath("user/login")
                .add("username", "test_测试")
                .post();
        log(response);
        Assert.assertFalse(response.isSucceed());
    }
}