package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.repository.UserRepository;
import cmri.snapshot.api.validator.CaptchaValidator;
import cmri.snapshot.api.validator.UserValidator;
import cmri.utils.dao.RedisHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 10/28/15.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private CaptchaValidator captchaValidator;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(String username, String password, Long phoneNum, String email) throws AuthenticationException {
        User me = new User(username, password, phoneNum, email);
        User user = userValidator.validateLogin(me);
        user.setLoginTimes(me.getLoginTimes() + 1);
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        LOG.info(me.getName() + " login");
        return new ResponseMessage();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseMessage register(String username, String password, Long phoneNum, String email, String captchaId, String captcha) {
        captchaValidator.validateCaptcha(captchaId, captcha);
        RedisHandler.instance().del(captchaId);
        User me = new User(username, password, phoneNum, email);
        userValidator.validateNotRegistered(me);
        LOG.info(me.getName() + " register");
        userRepository.save(me);
        return new ResponseMessage();
    }
}
