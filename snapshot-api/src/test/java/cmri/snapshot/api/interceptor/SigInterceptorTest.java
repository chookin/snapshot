package cmri.snapshot.api.interceptor;

import cmri.snapshot.api.WebAppTest;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.RestHandler;
import cmri.utils.configuration.ConfigManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 11/20/15.
 */
public class SigInterceptorTest extends WebAppTest{

    @Test
    public void testGenSig() throws Exception {
        String str = "POSThttp://111.13.47.167:8080/sms/authCode/sendphoneNum=13426198753time=1447917334486f4a8yoxG9F6b1gUB";
        String md5 = SigInterceptor.genSig(str);
        System.out.println(md5);
        Assert.assertEquals("1d45cb307d052693975dedb67755d0c0", md5);
    }

    @Test
    public void testNoSig(){
        ResponseMessage response = rest.reset()
                .setPath("user/info/get")
                .add("phoneNum", ConfigManager.getLong("test.phoneNum"))
                .justPost();
        log(response);
        Assert.assertTrue(response.getMessage().equals("para 'sig' is empty"));
    }
}