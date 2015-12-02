package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 11/21/15.
 */
public class GlobalExceptionHandlerTest extends WebAppTest{

    /**
     * Test 'Request method 'GET' not supported' of Exception HttpRequestMethodNotSupportedException.
     * @throws Exception
     */
    @Test
    public void testTinyError() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("user/info/get")
                .add("uid", ConfigManager.getLong("test.uid"))
                .get();
        log(response);
        Assert.assertTrue(!response.isSucceed());
    }
}