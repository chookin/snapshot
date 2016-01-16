package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.SpecialShot;
import cmri.snapshot.api.repository.SpecialShotRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhuyin on 1/14/16.
 */
@RestController
@RequestMapping("/home")
public class HomePageController {
    @Autowired
    SpecialShotRepository specialShotRepository;
    /**
     * 推荐位广告，推荐特色服务
     * @param uid 用户id，可选
     * @param longitude 经度，double，可选
     * @param latitude 维度，double，可选
     */
    @RequestMapping(value = "/recommendedSpecialShot", method = RequestMethod.GET)
    public ResponseMessage getRecommendedSpecialShot(Long uid, Double longitude, Double latitude){
        Iterable<SpecialShot> shots = specialShotRepository.findAll();
        return new ResponseMessage()
                .set("shots", JsonHelper.toJson(shots))
                ;
    }

    /**
     * 推荐位广告，推荐特色服务
     * @param uid 用户id，可选
     * @param longitude 经度，double，可选
     * @param latitude 维度，double，可选
     */
    @RequestMapping(value = "/recommendedPhotographers", method = RequestMethod.GET)
    public ResponseMessage getRecommendedPhotographers(Long uid, Double longitude, Double latitude){
        return new ResponseMessage()
                ;
    }
}
