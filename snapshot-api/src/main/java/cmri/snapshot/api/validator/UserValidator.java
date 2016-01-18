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
        Validate.notEmpty(user.getPassword(), "Password can not be empty");
        validateUser(user);
        User saved = userRepository.find(user);
        if(saved == null){
            throw new IllegalArgumentException("User does not exist");
        }
        if(!StringUtils.equals(saved.getPassword(), user.getPassword())){
            throw new AuthenticationException("Password error");
        }
        return saved;
    }

    public void validateNotRegistered(User user){
        Validate.notEmpty(user.getName(), " username can not be empty");
        validateUser(user);
        if(user.getPhone() == null && StringUtils.isEmpty(user.getEmail())){
            throw new IllegalArgumentException("Please specify email or phone number when registering");
        }
        if(user.getPhone() != null){
            if(userRepository.findByPhone(user.getPhone()) != null){
                throw new IllegalArgumentException("The phone number has been registered");
            }
        }else{
            if(userRepository.findByEmail(user.getEmail()) != null){
                throw new IllegalArgumentException("The email has already been registered");
            }
        }
        if(userRepository.findByName(user.getName()) != null){
            throw new IllegalArgumentException("The username has already been registered");
        }
    }

    static void validateUser(User user){
        if(user.getPhone() != null){
            ValidateKit.isMobile(user.getPhone().toString(), "Phone number format error");
        }
        if(StringUtils.isNoneEmpty(user.getEmail())){
            ValidateKit.isEmail(user.getEmail(), "Email format error");
        }
        if(StringUtils.isNoneEmpty(user.getName())) {
            ValidateKit.match("[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,8}", user.getName(), "The length of the username should be between 2-8, and the username can only contain Chinese characters, numbers, letters, underscores");
        }
    }
}
