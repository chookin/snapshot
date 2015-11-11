package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.captcha.AlphanumCatcha;
import cmri.utils.captcha.CaptchaGenerator;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.dao.RedisHandler;
import cmri.utils.web.NetworkHelper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 10/29/15.
 */
@Controller
public class CaptchaController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    private AtomicLong id = new AtomicLong();

    /**
     * 生成图片验证码
     */
    @ResponseBody
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public ResponseMessage captcha(Integer width, Integer height, Integer fontSize, HttpServletResponse response){
        Validate.notNull(width, "没有指定width");
        Validate.notNull(height, "没有指定height");
        Validate.notNull(fontSize, "没有指定fontSize");

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        CaptchaGenerator captcha = new AlphanumCatcha(ConfigManager.getInt("captcha.number"), width, height, fontSize);
        String captchaId = getCaptchaId();
        RedisHandler.instance().set(captchaId, captcha.getCode(), ConfigManager.getInt("captcha.expireSeconds"));
        LOG.trace("captcha: "+captchaId+", "+captcha);
        return new ResponseMessage().set("captcha", captcha.getImageBase64())
                .set("captchaId", captchaId);
    }

    private String getCaptchaId(){
        return "captcha-"+ NetworkHelper.getHostname()+"-" + id.incrementAndGet();
    }
}
