package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigFileManager;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhuyin on 15/12/6.
 */
public class WorkControllerTest extends WebAppTest{

    @Test
    public void testAddWork() throws Exception {
        FileSystemResource file1 = new FileSystemResource(new File(ConfigFileManager.dump("pic/sandy-2009.jpg")));
        FileSystemResource file2 = new FileSystemResource(new File(ConfigFileManager.dump("pic/yanzi.jpg")));
        ResponseMessage response = rest.reset()
                .setPath("work/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("name", "work_sandy")
                .add("location", "Beijing")
                .add("file1", file1)
                .add("file2", file2)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetWork() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("work/getWorks")
                .add("userId", ConfigManager.getLong("test.uid"))
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}