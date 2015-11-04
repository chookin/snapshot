package cmri.snapshot.api.validator;

import cmri.utils.dao.RedisHandler;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

/**
 * Created by zhuyin on 11/2/15.
 */
@Service
public class CaptchaValidator {
    public void validateCaptcha(String captchaId, String captcha){
        String code = RedisHandler.instance().get(captchaId);
        Validate.notEmpty(code, "验证码错误");
        if (!code.equalsIgnoreCase(captcha)) {
            throw new IllegalArgumentException("验证码错误");
        }
    }
}
