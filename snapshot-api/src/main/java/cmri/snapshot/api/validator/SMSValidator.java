package cmri.snapshot.api.validator;

import cmri.snapshot.api.controller.SMSController;
import cmri.utils.dao.RedisHandler;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

/**
 * Created by zhuyin on 11/5/15.
 */
@Service
public class SMSValidator {
    public void validateAuthCode(long phoneNum, String authCode){
        String id = SMSController.getAuthCodeId(phoneNum);
        String code = RedisHandler.instance().get(id);
        Validate.notEmpty(code, "Please resend auth code");
        if (!code.equalsIgnoreCase(authCode)) {
            throw new IllegalArgumentException("Auth code error");
        }
        RedisHandler.instance().del(id);
    }
}
