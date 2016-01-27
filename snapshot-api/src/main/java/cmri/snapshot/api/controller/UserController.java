package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.snapshot.api.helper.ThumbnailGen;
import cmri.snapshot.api.repository.*;
import cmri.snapshot.api.validator.SMSValidator;
import cmri.snapshot.api.validator.UserValidator;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.JsonHelper;
import cmri.utils.lang.Pair;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

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
    @Autowired
    private AvatarDetailRepository avatarDetailRepository;

    /**
     * 用户登录. 因为签名校验时已经使用了密码信息，因此不需要再传递密码的参数。
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(Long phoneNum, String username, String loginIP) {
        User user;
        if(username != null){
            user = userRepository.findByName(username);
        }else{
            Assert.notNull(phoneNum, "please assign 'username' or 'phoneNum' to login");
            user = userRepository.findByPhone(phoneNum);
        }
        LOG.info("user '" + user.getId() + "' login");

        // save login detail to 'login' table
        Login login = new Login()
                .setUserId(user.getId())
                .setLoginIp(loginIP)
                .setTime(new Timestamp(System.currentTimeMillis()))
                ;
        loginRepository.save(login);
        return new ResponseMessage()
                .set("uid", String.valueOf(user.getId()))
                .set("username", user.getName())
                .set("phoneNum", String.valueOf(user.getPhone()))
                ;
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseMessage register(Long phoneNum, String username, String password, String authCode) {
        smsValidator.validateAuthCode(phoneNum, authCode);
        User user = new User(username, password, phoneNum)
                .setRegisterTime(new Timestamp(System.currentTimeMillis()))
                ;
        userValidator.validateNotRegistered(user);
        LOG.info("user '" + user.getPhone() + "' register");
        userRepository.save(user);
        return new ResponseMessage();
    }

    /**
     * 获取用户基本信息
     */
    @RequestMapping(value = "/info/get", method = RequestMethod.GET)
    public ResponseMessage getInfo(long uid){
        User user = userRepository.findById(uid);
        return new ResponseMessage()
                .set("uid", String.valueOf(uid))
                .set("username", user.getName())
                .set("phoneNum", String.valueOf(user.getPhone()))
                .set("area", String.valueOf(user.getArea()))
                .set("avatar", user.getAvatar())
                .set("sex", user.getSex())
                ;
    }

    @RequestMapping(value = "/name/get", method = RequestMethod.GET)
    public ResponseMessage getName(long uid){
        // TODO use redis to cache
        String username = userRepository.findById(uid).getName();
        return new ResponseMessage()
                .set("username", username);
    }

    @RequestMapping(value = "/name/mod", method = RequestMethod.POST)
    public ResponseMessage modName(long uid, String newName){
        User user = userRepository.findById(uid);
        user.setName(newName);
        userRepository.save(user);
        return new ResponseMessage();
    }

    @RequestMapping(value = "/password/mod", method = RequestMethod.POST)
    public ResponseMessage modPassword(long uid, String password, String authCode){
        User user = userRepository.findById(uid);
        smsValidator.validateAuthCode(user.getPhone(), authCode);
        user.setPassword(password);
        userRepository.save(user);
        return new ResponseMessage();
    }

    /**
     * 修改头像
     */
    @RequestMapping(value="/avatar/mod", method = RequestMethod.POST)
    public ResponseMessage modAvatar(HttpServletRequest request, long uid, @RequestParam(value = "img") MultipartFile file) throws Exception{
        // save the source avatar image file, 原始尺寸
        String filename = ImageController.uploadImg(request, file).getValue();
        AvatarDetail origin = new AvatarDetail();
        origin.setPhoto(filename);
        origin.setUserId(uid);
        origin.setTime(new Timestamp(System.currentTimeMillis()));
        avatarDetailRepository.save(origin);

        // generate image of the specified size and save，指定尺寸
        String avatar = ThumbnailGen.getInstance(request)
                .setUploadPath(FilenameUtils.concat(ConfigManager.get("upload.basePath"), "avatar"))
                .setUid(String.valueOf(uid))
                .gen(FilenameUtils.concat(ServerHelper.getContextPath(request), filename));

        AvatarDetail thumbnail = new AvatarDetail();
        thumbnail.setPhoto(avatar);
        thumbnail.setUserId(uid);
        thumbnail.setTime(new Timestamp(System.currentTimeMillis()));
        avatarDetailRepository.save(thumbnail);

        User user = userRepository.findById(uid);
        user.setAvatar(avatar);
        userRepository.save(user);
        return new ResponseMessage()
                .set("filename", avatar)
                .set("url", WebMvcConfig.getUrl(avatar))
                ;
    }
}
