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
public class WorksControllerTest extends WebAppTest{

    @Test
    public void testAddWork() throws Exception {
        FileSystemResource file1 = new FileSystemResource(new File(ConfigFileManager.dump("pic/sandy-2009.jpg")));
        FileSystemResource file2 = new FileSystemResource(new File(ConfigFileManager.dump("pic/yanzi.jpg")));
        // FileSystemResource[] imgs = {file1,file2};
        ArrayList<FileSystemResource> imgs = new ArrayList<>();
        imgs.add(file1);
        imgs.add(file2);
        ResponseMessage response = rest.reset()
                .setPath("works/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("name", "work_sandy")
                .add("location", "BeiJing")
                .add("imgs", file1)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetWorks() throws Exception {

    }
}