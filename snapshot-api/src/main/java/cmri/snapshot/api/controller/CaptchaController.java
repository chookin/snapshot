package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.captcha.AlphanumCatcha;
import cmri.utils.captcha.CaptchaGenerator;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.dao.RedisHandler;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by zhuyin on 10/29/15.
 */
@Controller
public class CaptchaController {
    @ResponseBody
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public ResponseMessage index(Integer width, Integer height, Integer fontSize, HttpServletResponse response){
        Validate.notNull(width, "没有指定width");
        Validate.notNull(height, "没有指定height");
        Validate.notNull(fontSize, "没有指定fontSize");

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        CaptchaGenerator captcha = new AlphanumCatcha(ConfigManager.getInteger("captcha.number"), width, height, fontSize);
        String captchaKey = "captcha-"+UUID.randomUUID().toString();
        RedisHandler.instance().set(captchaKey, captcha.getCode(), ConfigManager.getInteger("captcha.expireSeconds"));
        return new ResponseMessage().set("captcha", captcha.getImageBase64())
                .set("captchaKey", captchaKey);
    }
}
