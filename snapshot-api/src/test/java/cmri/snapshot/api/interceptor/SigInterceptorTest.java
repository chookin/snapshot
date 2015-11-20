package cmri.snapshot.api.interceptor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhuyin on 11/20/15.
 */
public class SigInterceptorTest{

    @Test
    public void testGenSig() throws Exception {
        String str = "POSThttp://111.13.47.167:8080/sms/authCode/sendphoneNum=13426198753time=1447917334486f4a8yoxG9F6b1gUB";
        String md5 = SigInterceptor.genSig(str);
        System.out.println(md5);
        Assert.assertEquals("72e42b10f73241140ad0cd05fcbc9b3c", md5);
    }
}