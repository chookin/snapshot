package cmri.snapshot.api.domain;

import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.JsonHelper;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * Created by zhuyin on 11/15/15.
 */
public class UserTest {
    @Test
    public void testToJson(){
        User user = new User("test", "098f6bcd4621d373cade4e832627b4f6", ConfigManager.getLong("test.phoneNum"))
                .setRegisterTime(new Timestamp(System.currentTimeMillis()))
                .setRole((byte) 0);
        String str = JsonHelper.toJson(user);
        System.out.println(str);
    }

}