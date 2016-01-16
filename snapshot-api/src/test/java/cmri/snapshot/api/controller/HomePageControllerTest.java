package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhuyin on 1/16/16.
 */
public class HomePageControllerTest extends WebAppTest{

    @Test
    public void testGetRecommendedSpecialShot() throws Exception {
        ResponseMessage response = rest.reset()
                .setPath("home/recommendedSpecialShot")
                .add("uid", ConfigManager.getLong("test.uid"))
                .get();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }

    @Test
    public void testGetRecommendedPhotographers() throws Exception {

    }
}