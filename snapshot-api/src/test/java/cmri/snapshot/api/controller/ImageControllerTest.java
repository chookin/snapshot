package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigFileManager;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

/**
 * Created by zhuyin on 11/13/15.
 */
public class ImageControllerTest extends WebAppTest{
    @Test
    public void testUpload() throws Exception {
        String file = ConfigFileManager.dump("pic/sandy-2009.jpg");
        ResponseMessage response = rest.reset()
                .setPath("image/upload")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("img", new FileSystemResource(new File(file)))
                        // .add("title", "test")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
    @Test
    public void testGet() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("image/get")
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
    @Test
    public void testGetSince() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("image/get")
                .add("since", "1452069197084")
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}