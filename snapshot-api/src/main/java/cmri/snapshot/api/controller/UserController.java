package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.repository.UserRepository;
import cmri.snapshot.api.validator.SMSValidator;
import cmri.snapshot.api.validator.UserValidator;
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
    private SMSValidator smsValidator;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(String username, String password, Long phoneNum) throws AuthenticationException {
        User me = new User(username, password, phoneNum);
        User user = userValidator.validateLogin(me);
        user.setLoginTimes(me.getLoginTimes() + 1);
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        LOG.info(me.getName() + " login");
        return new ResponseMessage();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseMessage register(String username, String password, Long phoneNum, String verifyCode) {
        smsValidator.validateVerifyCode(phoneNum, verifyCode);
        User me = new User(username, password, phoneNum);
        userValidator.validateNotRegistered(me);
        LOG.info(me.getName() + " register");
        userRepository.save(me);
        return new ResponseMessage();
    }
}
