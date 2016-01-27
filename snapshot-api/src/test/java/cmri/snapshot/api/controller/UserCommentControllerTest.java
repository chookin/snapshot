package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by zhuyin on 12/4/15.
 */
public class UserCommentControllerTest extends WebAppTest{
    @Test
    public void testAdd() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("userComment/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("userId", ConfigManager.getLong("test.uid") )
                .add("content", "hi~~")
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetAboutUser() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("userComment/getAboutUser")
                .add("userId", ConfigManager.getLong("test.uid"))
                .add("page", 0)
                .add("step", 12)
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}
