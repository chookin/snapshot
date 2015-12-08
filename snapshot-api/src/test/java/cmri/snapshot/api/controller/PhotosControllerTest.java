package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 15/12/6.
 */
public class PhotosControllerTest extends WebAppTest {

    @Test
    public void testGetPhoto() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("photos/get")
                .add("uid", ConfigManager.getLong("test.uid"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}