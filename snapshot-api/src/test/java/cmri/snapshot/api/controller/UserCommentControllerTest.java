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
public class UserCommentControllerTest extends WebAppTest{
    @Test
    public void testAdd() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("userComment/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("object", ConfigManager.getLong("test.uid"))
                .add("content", "hi~~")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetAboutUser() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("userComment/getAboutUser")
                .add("uid", ConfigManager.getLong("test.uid"))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}