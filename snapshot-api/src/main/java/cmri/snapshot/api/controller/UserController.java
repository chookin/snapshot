package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.snapshot.api.helper.ThumbnailGen;
import cmri.snapshot.api.repository.AvatarDetailRepository;
import cmri.snapshot.api.repository.LoginRepository;
import cmri.snapshot.api.repository.UserCommentRepository;
import cmri.snapshot.api.repository.UserRepository;
import cmri.snapshot.api.validator.SMSValidator;
import cmri.snapshot.api.validator.UserValidator;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.JsonHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @Autowired
    private UserCommentRepository userCommentRepository;

    /**
     * 因为签名校验时已经使用了密码信息，因此不需要再传递密码的参数。
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseMessage login(Long phoneNum, String username) {
        User user;
        if(username != null){
            user = userRepository.findByName(username);
        }else{
            Assert.notNull(phoneNum, "please assign 'username' or 'phoneNum' to login");
            user = userRepository.findByPhone(phoneNum);
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
    public ResponseMessage getInfo(Long uid){
        User user = userRepository.findById(uid);
        return new ResponseMessage()
                .set("username", user.getName())
                .set("phoneNum", String.valueOf(user.getPhone()))
                .set("area", String.valueOf(user.getArea()))
                .set("avatar", user.getAvatar())
                .set("sex", user.getSex())
                .set("appointmentCount", String.valueOf(user.getAppointmentCount()))
                .set("collectedCount", String.valueOf(user.getCollectedCount()))
                ;
    }

    @RequestMapping(value = "/name/mod", method = RequestMethod.POST)
    public ResponseMessage modName(Long uid, String newName){
        User user = userRepository.findById(uid);
        user.setName(newName);
        userRepository.save(user);
        return new ResponseMessage();
    }

    @RequestMapping(value = "/password/mod", method = RequestMethod.POST)
    public ResponseMessage modPassword(Long uid, String password, String authCode){
        User user = userRepository.findById(uid);
        smsValidator.validateAuthCode(user.getPhone(), authCode);
        user.setPassword(password);
        userRepository.save(user);
        return new ResponseMessage();
    }
    /**
     * 提交头像修改
     */
    @RequestMapping(value="/avatar/mod", method = RequestMethod.POST)
    public ResponseMessage modAvatar(HttpServletRequest request, Long uid, @RequestParam(value = "img") MultipartFile file) throws Exception{
        // save the source avatar image file, 原始尺寸
        String filename = ImageController.uploadImg(request, file);
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

    /**
     * 对其他用户进行评论
     * @param uid 用户id
     * @param object 其他用户的id
     * @param parent 父评论id
     * @param content 评论内容
     */
    @RequestMapping(value = "/userComment/add", method = RequestMethod.POST)
    public ResponseMessage addUserComment(Long uid, Long object, Long parent, String content){
        Assert.notNull(object, "para 'object' is null");
        UserComment comment = new UserComment();
        comment.setUserId(uid);
        comment.setObject(object);
        if(parent == null){
            comment.setParent(0);
        }else {
            comment.setParent(parent);
        }
        comment.setContent(content);
        comment.setTime(new Timestamp(System.currentTimeMillis()));
        userCommentRepository.save(comment);
        return new ResponseMessage()
                .set("comment", JsonHelper.toJson(comment));
    }

    @RequestMapping(value = "/commentsAboutUser/get", method = RequestMethod.POST)
    public ResponseMessage getCommentsAboutUser(Long uid){
        List<UserComment> comments = userCommentRepository.findCommentsAboutUser(uid);
        return new ResponseMessage()
                .set("comments", JsonHelper.toJson(comments))
                ;
    }

    @RequestMapping(value = "/username/get", method = RequestMethod.POST)
    public ResponseMessage getUsername(Long uid){
        // TODO use redis to cache
        String username = userRepository.findById(uid).getName();
        return new ResponseMessage()
                .set("username", username);
    }
}
