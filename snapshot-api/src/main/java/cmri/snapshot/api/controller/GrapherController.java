package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 12/1/15.
 */
@RestController
@RequestMapping("/grapher")
public class GrapherController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;
    /**
     *
     * @param userId 用户ID
     * @param realName 真实姓名
     * @param idNum 身份证号
     * @param idImgPath 手持身份证正面照片的服务器路径
     * @param cameraId 器材编号
     * @param cameraModel 器材型号
     * @param lensModel 镜头型号
     * @param cameraImgPath 器材照片的服务器路径
     */
    @RequestMapping(value = "/toBecome", method = RequestMethod.POST)
    public ResponseMessage toBecome(Long userId, String realName, String idNum, String idImgPath, String cameraId, String cameraModel, String lensModel, String cameraImgPath) {
        User user = userRepository.findById(userId);

        return new ResponseMessage();
    }

    @RequestMapping(value = "/info/mod", method = RequestMethod.POST)
    public ResponseMessage modInfo(Long userId, String newName, String avatar, String scope, String desire){
        User user = userRepository.findById(userId);
        user.setName(newName);
        userRepository.save(user);
        return new ResponseMessage();
    }
}
