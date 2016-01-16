package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.SpecialShot;
import cmri.snapshot.api.domain.SpecialShotStills;
import cmri.snapshot.api.repository.SpecialShotRepository;
import cmri.snapshot.api.repository.SpecialShotStillsRepository;
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
    @Autowired
    SpecialShotStillsRepository specialShotStillsRepository;
    /**
     * 推荐位广告，推荐特色服务
     * @param uid 用户id，可选
     * @param longitude 经度，double，可选
     * @param latitude 维度，double，可选
     */
    @RequestMapping(value = "/recommendedSpecialShot", method = RequestMethod.GET)
    public ResponseMessage getRecommendedSpecialShot(Long uid, Double longitude, Double latitude){
        Iterable<SpecialShot> shots = specialShotRepository.findAll();
        SpecialShot shot = shots.iterator().next();
        if(shot == null) {
            return new ResponseMessage(false, "no special shot");
        }
        List<SpecialShotStills> pics = specialShotStillsRepository.findByShotId(shot.getId());
        String picUrl = "";
        if(!pics.isEmpty()){
            picUrl = pics.get(0).getPic();
            picUrl = WebMvcConfig.getUrl(picUrl);
        }
        return new ResponseMessage()
                .set("shotId", String.valueOf(shot.getId()))
                .set("title", shot.getTitle())
                .set("intro", shot.getIntro())
                .set("picUrl", picUrl)
                .set("price", String.valueOf(shot.getPrice()))
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
