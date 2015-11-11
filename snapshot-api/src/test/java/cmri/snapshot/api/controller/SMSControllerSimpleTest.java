package cmri.snapshot.api.controller;

import cmri.utils.configuration.ConfigManager;
import org.junit.Test;

/**
 * Created by zhuyin on 11/11/15.
 */
public class SMSControllerSimpleTest {

    @Test
    public void testSendAuthCode() throws Exception {
        new SMSController().sendAuthCode(13426198753L, "1124", ConfigManager.getInt("sms.authCode.expireMinutes"));
    }
}