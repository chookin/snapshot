package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 15/12/5.
 */
public class ShotLikeControllerTest extends WebAppTest{

    @Test
    public void testAdd() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("shotLike/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("shotId", 1)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testDelete() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("shotLike/delete")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("shotId", 1)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}