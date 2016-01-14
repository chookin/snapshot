package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 1/14/16.
 */
@RestController
@RequestMapping("/home")
public class HomePageController {

    /**
     * 推荐位广告，推荐特色服务
     * @param uid 用户id，可选
     * @param longitude 经度，double，可选
     * @param latitude 维度，double，可选
     */
    @RequestMapping(value = "/recommendedShot", method = RequestMethod.GET)
    public ResponseMessage getRecommendedShot(Long uid, Double longitude, Double latitude){
        return new ResponseMessage()
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
