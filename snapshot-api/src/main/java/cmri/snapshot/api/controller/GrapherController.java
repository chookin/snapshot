package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.CameraRepository;
import cmri.snapshot.api.repository.GrapherPlanRepository;
import cmri.snapshot.api.repository.GrapherRepository;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.lang.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * Created by zhuyin on 12/1/15.
 */
@RestController
@RequestMapping("/grapher")
public class GrapherController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GrapherRepository grapherRepository;
    @Autowired
    private GrapherPlanRepository planRepository;
    @Autowired
    private CameraRepository cameraRepository;
    /**
     *
     * @param uid 用户ID
     * @param realName 真实姓名
     * @param idNum 身份证号
     * @param idImgPath 手持身份证正面照片的服务器路径
     * @param cameraId 器材编号
     * @param cameraModel 器材型号
     * @param lensModel 镜头型号
     * @param cameraImgPath 器材照片的服务器路径
     */
    @RequestMapping(value = "/toBecome", method = RequestMethod.POST)
    public ResponseMessage toBecome(Long uid, String realName, String idNum, String idImgPath, String cameraId, String cameraModel, String lensModel, String cameraImgPath) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = userRepository.findById(uid);
        user.setRealName(realName);
        user.setIdNum(idNum);
        user.setIdImage(idImgPath);
        user.setUpdateTime(now);
        userRepository.save(user);

        Camera camera = new Camera();
        camera.setUserId(uid);
        camera.setIdentity(cameraId);
        camera.setModel(cameraModel);
        camera.setLensModel(lensModel);
        camera.setImage(cameraImgPath);
        camera.setCreateTime(now);
        cameraRepository.save(camera);

        Grapher grapher = new Grapher();
        grapher.setUserId(uid);
        grapher.setCreateTime(now);
        grapherRepository.save(grapher);
        return new ResponseMessage();
    }

    /**
     * @param uid 用户ID
     * @param newName 昵称
     * @param region 服务城市
     * @param desire 擅长领域
     * @param shootNum 拍摄张数
     * @param shootHour 拍摄时长
     * @param truingNum 精修底片的张数
     * @param printNum 相片冲印的张数
     * @param clothing 服装
     * @param makeup 化妆
     */
    @RequestMapping(value = "/info/mod", method = RequestMethod.POST)
    public ResponseMessage modInfo(Long uid, String newName, String region, String desire, int shootNum, int shootHour, int truingNum, int printNum, String clothing, String makeup){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = userRepository.findById(uid);
        user.setName(newName);
        user.setUpdateTime(now);
        userRepository.save(user);

        Grapher grapher = grapherRepository.findByUserId(uid);
        grapher.setRegion(region);
        grapher.setDesire(desire);
        grapherRepository.save(grapher);

        GrapherPlan plan = new GrapherPlan();
        plan.setShootNum(shootNum);
        plan.setShootHour(shootHour);
        plan.setTruingNum(truingNum);
        plan.setPrintNum(printNum);
        plan.setClothing(clothing);
        plan.setMakeup(makeup);
        plan.setCreateTime(now);
        planRepository.save(plan);
        return new ResponseMessage();
    }

    @RequestMapping(value = "/plan/get", method = RequestMethod.POST)
    public ResponseMessage getPlan(Long uid){
        GrapherPlan plan = planRepository.findByUserId(uid);
        return new ResponseMessage()
                .set("plan", JsonHelper.toJson(plan))
                ;
    }

    @RequestMapping(value = "/camera/get", method = RequestMethod.POST)
    public ResponseMessage getCamera(Long uid){
        Camera camera = cameraRepository.findByUserId(uid);
        return new ResponseMessage()
                .set("camera", JsonHelper.toJson(camera))
                ;
    }
}
