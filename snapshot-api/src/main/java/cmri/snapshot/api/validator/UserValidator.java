package cmri.snapshot.api.validator;

import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.lang.ValidateKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * Created by zhuyin on 11/2/15.
 */
@Service
public class UserValidator {
    @Autowired
    private UserRepository userRepository;

    public User validateLogin(User user) throws AuthenticationException {
        Validate.notEmpty(user.getPassword(), "密码不能为空");
        validateUser(user);
        User saved = userRepository.find(user);
        if(saved == null){
            throw new IllegalArgumentException("用户不存在");
        }
        if(!StringUtils.equals(saved.getPassword(), user.getPassword())){
            throw new AuthenticationException("密码错误");
        }
        return saved;
    }

    public void validateNotRegistered(User user){
        Validate.notEmpty(user.getName(), "用户名不能为空");
        validateUser(user);
        if(user.getMobile() == null && StringUtils.isEmpty(user.getEmail())){
            throw new IllegalArgumentException("注册时请指定email或手机号");
        }
        if(user.getMobile() != null){
            if(userRepository.findByMobile(user.getMobile()) != null){
                throw new IllegalArgumentException("该手机号已被注册");
            }
        }else{
            if(userRepository.findByEmail(user.getEmail()) != null){
                throw new IllegalArgumentException("该email已经被注册过了");
            }
        }
        if(userRepository.findByName(user.getName()) != null){
            throw new IllegalArgumentException("该用户名已经被注册过了");
        }
    }

    static void validateUser(User user){
        if(user.getMobile() != null){
            ValidateKit.isMobile(user.getMobile().toString(), "手机号码格式错误");
        }
        if(StringUtils.isNoneEmpty(user.getEmail())){
            ValidateKit.isEmail(user.getEmail(), "email格式错误");
        }
        if(StringUtils.isNoneEmpty(user.getName())) {
            ValidateKit.match("[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,8}", user.getName(), "用户名的长度介于2-8之间，只能包含中文，数字，字母，下划线");
        }
    }
}
