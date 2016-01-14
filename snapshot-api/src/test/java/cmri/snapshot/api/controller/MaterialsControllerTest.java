package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 12/15/15.
 */
public class MaterialsControllerTest extends WebAppTest {

    @Test
    public void testHomepages() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("materials/homepages")
                .add("time", 1450193196206L)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testPersonalHomepages() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("materials/personalHomepages")
                .add("time", 1450193196206L)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testCategories() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("materials/categories")
                .add("time", 1450193196206L)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGroupShotDetails() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("materials/groupShotDetails")
                .add("time", 1450193196206L)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetJpgUrl() throws Exception {
        String material = "c-travel";
        String url = MaterialsController.getJpgUrl(material);
        Assert.assertTrue(url.contains(material));
    }

    @Test
    public void testGetUrl() throws Exception {
        String material = "c-travel";
        String url = MaterialsController.getUrl(material);
        Assert.assertTrue(url.contains(material));
    }
}