package cmri.snapshot.api.controller;

import cmri.utils.captcha.AlphanumCatcha;
import cmri.utils.captcha.CaptchaGenerator;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.Pair;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zhuyin on 10/29/15.
 */
@Controller
public class CaptchaController {
    /**
     * 将验证码保存至session时使用,便于以后校验。
     */
    public static final String CAPTCHA_CODE_KEY = "_CAPTCHA_CODE_";

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void index(Integer width, Integer height, Integer fontSize, HttpServletResponse response, HttpSession session){
        Validate.notNull(width, "没有指定width");
        Validate.notNull(height, "没有指定height");
        Validate.notNull(fontSize, "没有指定fontSize");

        CaptchaGenerator captcha = new AlphanumCatcha(ConfigManager.getAsInteger("captcha.number"), width, height, fontSize);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(captcha.getImage(), "jpeg", sos);
            session.setMaxInactiveInterval(ConfigManager.getAsInteger("session.maxInactiveInterval"));
            session.setAttribute(CAPTCHA_CODE_KEY, new Pair<>(captcha.getCode(), System.currentTimeMillis()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
