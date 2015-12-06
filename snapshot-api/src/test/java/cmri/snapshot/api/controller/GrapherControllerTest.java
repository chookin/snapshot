package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 12/4/15.
 */
public class GrapherControllerTest extends WebAppTest {

    @Test
    public void testToBecome() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("grapher/toBecome")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("realName", "test")
                .add("idNum", "412921198701013399")
                .add("idImgPath", "imgPath")
                .add("cameraId", "191218r1121")
                .add("cameraModel", "5d3")
                .add("lensModel", "70-200")
                .add("cameraImgPath", "test")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testModInfo() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("grapher/info/mod")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("newName", "abc")
                .add("region", "北京")
                .add("desire", "情侣写真、个人写真")
                .add("shootNum", 30)
                .add("shootHour", 2)
                .add("truingNum", 10)
                .add("printNum", 5)
                .add("clothing", "自备")
                .add("makeup", "无")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetPlan() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("grapher/plan/get")
                .add("uid", ConfigManager.getLong("test.uid"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetCamera() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("grapher/cameras/get")
                .add("uid", ConfigManager.getLong("test.uid"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}