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

/**
 * Created by zhuyin on 11/2/15.
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
    public ResponseMessage login(Long phoneNum, String username) {
        User user;
        if(username != null){
            user = userRepository.findByName(username);
        }else{
            user = userRepository.findByMobile(phoneNum);
        }
        LOG.info("user '" + user.getName() + "' login");
        return new ResponseMessage();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseMessage register(Long phoneNum, String username, String password, String authCode) {
        smsValidator.validateAuthCode(phoneNum, authCode);
        User user = new User(username, password, phoneNum);
        userValidator.validateNotRegistered(user);
        LOG.info("user '" + user.getName() + "' register");
        userRepository.save(user);
        return new ResponseMessage();
    }
}
