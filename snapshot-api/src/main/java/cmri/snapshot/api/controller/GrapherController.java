package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.*;
import cmri.utils.lang.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

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
     * 申请成为摄影师
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
    public ResponseMessage toBecome(long uid, String realName, String idNum, String idImgPath, String cameraId, String cameraModel, String lensModel, String cameraImgPath) {
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
     * 修改摄影师的信息
     *
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
    public ResponseMessage modInfo(long uid, String newName, String region, String desire, int shootNum, int shootHour, int truingNum, int printNum, String clothing, String makeup){
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
        plan.setUserId(uid);
        plan.setShotNum(shootNum);
        plan.setShotHour(shootHour);
        plan.setTruingNum(truingNum);
        plan.setPrintNum(printNum);
        plan.setClothing(clothing);
        plan.setMakeup(makeup);
        plan.setCreateTime(now);
        planRepository.save(plan);
        return new ResponseMessage();
    }

    /**
     * 获取指定摄影师的套餐详情
     */
    @RequestMapping(value = "/plan/get", method = RequestMethod.POST)
    public ResponseMessage getPlan(long gid){
        GrapherPlan plan = planRepository.findByUserId(gid);
        return new ResponseMessage()
                .set("plan", JsonHelper.toJson(plan))
                ;
    }

    /**
     * 获取指定摄影师的器材详情
     */
    @RequestMapping(value = "/cameras/get", method = RequestMethod.POST)
    public ResponseMessage getCamera(long gid){
        List<Camera> cameras = cameraRepository.findByUserId(gid);
        return new ResponseMessage()
                .set("cameras", JsonHelper.toJson(cameras))
                ;
    }

    /**
     * 获取摄影师的可预约日期
     *
     * @param gid 摄影师id
     */
    @RequestMapping(value = "/availableDate/get", method = RequestMethod.POST)
    public ResponseMessage getAvailableDate(long gid){
        // TODO: 15/12/7
        return new ResponseMessage()
                ;
    }
}
