package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.service.UserRepository;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.Pair;
import cmri.utils.lang.ValidateKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by zhuyin on 10/28/15.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(HttpServletRequest request) {
        User user = new User(request.getParameter("userName"), request.getParameter("password"), request.getParameter("phoneNum"), request.getParameter("email"));
        String errorInfo = validateUser(user);
        if(StringUtils.isEmpty(errorInfo)){
            if(StringUtils.isBlank(user.getPassword())){
                errorInfo = "密码不能为空";
            }
            if(StringUtils.isEmpty(errorInfo)){
                errorInfo = validateLogin(user);
            }
        }
        return new ResponseMessage(true, errorInfo);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseMessage register(String userName, String password, String phoneNum, String email, String captcha, HttpSession session) {
        String errorInfo = validateCaptcha(session, captcha);
        if(StringUtils.isEmpty(errorInfo)) {
            User user = new User(userName, password, phoneNum, email);
            errorInfo = validateUser(user);
            if (StringUtils.isEmpty(errorInfo)) {
                errorInfo = validateNotRegistered(user);
                if (StringUtils.isEmpty(errorInfo)) {
                    userRepository.save(user);
                }
            }
        }
        return new ResponseMessage(StringUtils.isEmpty(errorInfo), errorInfo);
    }
    String validateUser(User user){
        if(StringUtils.isNoneEmpty(user.getMobile())){
            if(!ValidateKit.isMobile(user.getMobile())){
                return "手机号码格式错误";
            }
        }
        if(StringUtils.isNoneEmpty(user.getEmail())){
            if(!ValidateKit.isEmail(user.getEmail())){
                return "email格式错误";
            }
        }
        if(StringUtils.isNoneEmpty(user.getName())) {
            if (!ValidateKit.match("[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,8}", user.getName())) {
                return "用户名的长度介于2-8之间，只能包含中文，数字，字母，下划线";
            }
        }
        return "";
    }
    String validateCaptcha(HttpSession session, String captcha){
        Pair<String, Long> pair = (Pair<String, Long>) session.getAttribute(CaptchaController.CAPTCHA_CODE_KEY);
        if(pair != null){
            if(pair.getValue() < System.currentTimeMillis() - ConfigManager.getAsInteger("captcha.expires")*1000) {
                return "验证码过期";
            }
        }
        if (pair.getKey().equalsIgnoreCase(captcha)) {
            return "";
        }
        return "验证码错误";
    }
    String validateLogin(User user){
        User saved = null;
        if(StringUtils.isNoneEmpty(user.getEmail())){
            saved = userRepository.findByEmail(user.getEmail());
        }else if(StringUtils.isNoneEmpty(user.getMobile())){
            saved = userRepository.findByMobile(user.getMobile());
        }else if(StringUtils.isNoneEmpty(user.getName())) {
            saved = userRepository.findByName(user.getName());
        }
        if(saved == null){
            return "用户不存在";
        }
        if(!StringUtils.equals(saved.getPassword(), user.getPassword())){
            return "密码错误";
        }
        return "";
    }
    String validateNotRegistered(User user){
        if(StringUtils.isEmpty(user.getMobile()) && StringUtils.isEmpty(user.getEmail())){
            return  "注册时请指定email或手机号";
        }
        if(StringUtils.isNoneEmpty(user.getMobile())){
            if(userRepository.findByMobile(user.getMobile()) != null){
                return  "该手机号已被注册";
            }
        }else{
            if(userRepository.findByEmail(user.getEmail()) != null){
                return  "该email已经被注册过了";
            }
        }
        if(userRepository.findByName(user.getName()) != null){
            return  "该用户名已经被注册过了";
        }
        return "";
    }
}
