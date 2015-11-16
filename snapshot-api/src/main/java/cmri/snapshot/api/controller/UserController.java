package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Login;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.helper.MultipartFileUploader;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.snapshot.api.helper.ThumbnailGen;
import cmri.snapshot.api.repository.LoginRepository;
import cmri.snapshot.api.repository.UserRepository;
import cmri.snapshot.api.validator.SMSValidator;
import cmri.snapshot.api.validator.UserValidator;
import cmri.utils.lang.JsonHelper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

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
    private LoginRepository loginRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private SMSValidator smsValidator;

    /**
     * 因为签名校验时已经使用了密码信息，因此不需要再传递密码的参数。
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(Long phoneNum, String username) {
        User user;
        if(username != null){
            user = userRepository.findByName(username);
        }else{
            Validate.notNull(phoneNum, "please assign 'username' or 'phoneNum' to login");
            user = userRepository.findByMobile(phoneNum);
        }
        LOG.info("user '" + user.getName() + "' login");

        // save login detail to 'login' table
        // TODO add login ip address
        Login login = new Login()
                .setUserId(user.getId())
                .setTime(new Timestamp(System.currentTimeMillis()))
                ;
        loginRepository.save(login);
        return new ResponseMessage();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseMessage register(Long phoneNum, String username, String password, String authCode) {
        smsValidator.validateAuthCode(phoneNum, authCode);
        User user = new User(username, password, phoneNum)
                .setRegisterTime(new Timestamp(System.currentTimeMillis()))
                ;
        userValidator.validateNotRegistered(user);
        LOG.info("user '" + user.getName() + "' register");
        userRepository.save(user);
        return new ResponseMessage();
    }

    @RequestMapping(value = "/info/get", method = RequestMethod.POST)
    public ResponseMessage getInfo(Long phoneNum){
        User user = userRepository.findByMobile(phoneNum);
        return new ResponseMessage()
                .set("username", user.getName())
                .set("phoneNum", String.valueOf(user.getMobile()))
                .set("area", String.valueOf(user.getArea()))
                .set("avatar", user.getAvatar())
                .set("sex", user.getSex())
                ;
    }

    @RequestMapping(value = "/name/mod", method = RequestMethod.POST)
    public ResponseMessage modName(Long phoneNum, String newName){
        User user = userRepository.findByMobile(phoneNum);
        user.setName(newName);
        userRepository.save(user);
        return new ResponseMessage();
    }

    @RequestMapping(value = "/password/mod", method = RequestMethod.POST)
    public ResponseMessage modPassword(Long phoneNum, String password, String authCode){
        smsValidator.validateAuthCode(phoneNum, authCode);
        User user = userRepository.findByMobile(phoneNum);
        user.setPassword(password);
        userRepository.save(user);
        return new ResponseMessage();
    }

    /**
     * 上传头像
     */
    @RequestMapping(value="/avatar/mod", method = RequestMethod.POST)
    public ResponseMessage modAvatar(HttpServletRequest request, Long phoneNum, @RequestParam(value = "avatar") MultipartFile file) throws Exception{
        // save the source avatar image file
        String filename = MultipartFileUploader.getInstance(request)
                .setRelPath("image")
                .setDefaultExtension("png")
                .upload(file)
                ;
        // generate image of the specified size and save
        String avatar = ThumbnailGen.getInstance()
                .setDstPath(ServerHelper.getUploadPath(request, "avatar"))
                .setIdentity(phoneNum.toString())
                .gen(filename);
        User user = userRepository.findByMobile(phoneNum);
        user.setAvatar(avatar);
        userRepository.save(user);
        return new ResponseMessage()
                .set("avatar", JsonHelper.toJson(avatar))
                ;
    }

}
