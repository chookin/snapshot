package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by feifei on 15/12/5.
 */
public class PhotoLikeControllerTest extends WebAppTest{

    @Test
    public void testAdd() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("photoLike/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("photoId", 1)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testDelete() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("photoLike/delete")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("photoId", 1)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}