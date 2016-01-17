package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigFileManager;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 1/17/16.
 */
public class ShotControllerTest extends WebAppTest{

    @Test
    public void testCreate() throws Exception {
        FileSystemResource pic1 = new FileSystemResource(new File(ConfigFileManager.dump("pic/sandy-2009.jpg")));
        FileSystemResource pic2 = new FileSystemResource(new File(ConfigFileManager.dump("pic/yanzi.jpg")));

        ResponseMessage response = rest.reset()
                .setPath("shot/create")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("price", 100)
                .add("pic1", pic1)
                .add("pic2", pic2)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}