package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

/**
 * Created by zhuyin on 11/13/15.
 */
public class ImageUploadControllerTest extends WebAppTest{

    @Test
    public void testUploadAvatar() throws Exception {
        String file = "/home/work/tmp/738b4710b912c8fcae1799cdfe039245d78821cd.jpg";
        ResponseMessage response = rest.clear()
                .setPath("user/avatar")
                .add("phoneNum", "13911116666")
                .add("avatar", new FileSystemResource(new File(file)))
                .post();
        log(response);
        Assert.assertTrue(response.isSucceed());
    }
}