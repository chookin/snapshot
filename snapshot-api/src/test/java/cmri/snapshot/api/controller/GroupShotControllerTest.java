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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 1/21/16.
 */
public class GroupShotControllerTest extends WebAppTest{
    @Test
    public void testCreate() throws Exception {
        List<Long> grapherIds = new ArrayList<>();
        Collections.addAll(grapherIds, 1L, 2L);

        FileSystemResource pic1 = new FileSystemResource(new File(ConfigFileManager.dump("pic/sandy-2009.jpg")));
        FileSystemResource pic2 = new FileSystemResource(new File(ConfigFileManager.dump("pic/yanzi.jpg")));

        ResponseMessage response = rest.reset()
                .setPath("groupShot/create")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("title", "星球大战")
                .add("intro", "超低价位，为您提供星球大战戏服新体验")
                .add("summary", "为大家提供星球大战戏服，全新体验星球大战震撼场面，来一次与众不同的约会")
                .add("location", "北京")
                .add("price", 100)
                .add("grapherIds", grapherIds)
                .add("pic1", pic1)
                .add("pic2", pic2)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }


    @Test
    public void testEnroll() throws Exception {

    }

    @Test
    public void testGetDetail() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("groupShot/get")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("page", 0)
                .add("step", 5)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}