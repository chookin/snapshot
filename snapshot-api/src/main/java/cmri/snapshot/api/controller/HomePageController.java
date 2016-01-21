package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.*;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by zhuyin on 1/14/16.
 */
@RestController
@RequestMapping("/home")
public class HomePageController {
    @Autowired
    SpecialShotRepository specialShotRepository;
    @Autowired
    SpecialShotStillRepository specialShotStillRepository;
    @Autowired
    ShotReleaseRepository shotReleaseRepository;
    @Autowired
    ShotStillRepository shotStillRepository;
    @Autowired
    GrapherRepository grapherRepository;
    @Autowired
    UserRepository userRepository;
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
        List<SpecialShotStill> pics = specialShotStillRepository.findByShotId(shot.getId());
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
     * @param page 请求的页数
     * @param step 每页多少条
     */
    @RequestMapping(value = "/recommendedShots", method = RequestMethod.GET)
    public ResponseMessage getRecommendedShots(Long uid, Double longitude, Double latitude, Integer page, Integer step){
        Pageable pageable = new PageRequest(page == null?0:page,
                step==null?12:step,
                new Sort(Sort.Direction.DESC, "createTime"));
        Iterable<ShotRelease> shotReleases = shotReleaseRepository.findAll(pageable);
        List<Map<String,String>> items = new ArrayList<>();
        for(ShotRelease shot: shotReleases){
            Map<String, String> map = new TreeMap<>();
            User user = userRepository.findById(shot.getGrapherId());
            List<ShotStill> stills = shotStillRepository.findByShotId(shot.getId());
            if(stills.size() > 0){
                map.put("picUrl", WebMvcConfig.getUrl(stills.get(0).getPic()));
            }
            map.put("avatarUrl", WebMvcConfig.getUrl(user.getAvatar()));
            map.put("nickname", user.getName());
            map.put("publishDate", String.valueOf(shot.getCreateTime()));
            map.put("appointmentCount", String.valueOf(shot.getAppointmentCount()));
            map.put("likeCount", String.valueOf(shot.getLikeCount()));
            map.put("commentsCount", String.valueOf(shot.getCommentCount()));
            map.put("location", shot.getLocation());
            map.put("photographerId", String.valueOf(user.getId()));

            items.add(map);
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(items))
                ;
    }
}
