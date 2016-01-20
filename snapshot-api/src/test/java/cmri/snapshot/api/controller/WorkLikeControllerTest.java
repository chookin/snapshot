package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 1/20/16.
 */
public class WorkLikeControllerTest extends WebAppTest{

    @Test
    public void testAdd() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("workLike/add")
                .add("uid", ConfigManager.getLong("test.uid"))
                .add("workId", 1)
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}