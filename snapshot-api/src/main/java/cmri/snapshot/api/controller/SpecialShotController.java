package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.*;
import cmri.utils.lang.JsonHelper;
import cmri.utils.lang.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhuyin on 1/14/16.
 */
@RestController
@RequestMapping("/specialShot")
public class SpecialShotController {
    @Autowired
    SpecialShotRepository specialShotRepository;
    @Autowired
    SpecialShotGrapherRepository specialShotGrapherRepository;
    @Autowired
    SpecialShotStillRepository specialShotStillRepository;
    @Autowired
    GrapherRepository grapherRepository;
    @Autowired
    UserRepository userRepository;


    /**
     *
     * @param uid 用户id, long
     * @param title 标题, string
     * @param price 价格，单位元，int类型
     * @param summary 活动内容, string
     * @param location 位置, string
     * @param service 服务, string
     * @param sculpt 造型, string
     * @param photographers 摄影师id，格式[id1,id2]
     * @throws IOException
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseMessage create(HttpServletRequest request, long uid, String title, String intro, int price, String summary, String location, String service, String sculpt, String photographers ) throws IOException {
        SpecialShot shot = SpecialShot.newOne();
        shot.setTitle(title);
        shot.setIntro(intro);
        shot.setPrice(price);
        shot.setSummary(summary);
        shot.setLocation(location);
        shot.setService(service);
        shot.setSculpt(sculpt);
        shot.setCreator(uid);
        specialShotRepository.save(shot);

        if(request instanceof StandardMultipartHttpServletRequest){
            StandardMultipartHttpServletRequest mulRequest = (StandardMultipartHttpServletRequest) request;
            // 所有的 MultipartFile 文件都认为是剧照图片
            Map<String, MultipartFile> stills = mulRequest.getFileMap();
            for(Map.Entry<String, MultipartFile> entry: stills.entrySet()) {
                Pair<String, String> pair = ImageController.uploadImg(request, entry.getValue());
                SpecialShotStill entity = new SpecialShotStill();
                entity.setShotId(shot.getId());
                entity.setPic(pair.getValue());
                specialShotStillRepository.save(entity);
            }
        }
        if(photographers != null) {
            List<Long> myGrapherIds = new Gson().fromJson(photographers, new TypeToken<List<Long>>() {
            }.getType());
            for (long grapherId : myGrapherIds) {
                SpecialShotGrapher grapher = new SpecialShotGrapher();
                grapher.setShotId(shot.getId());
                grapher.setGrapherId(grapherId);
                specialShotGrapherRepository.save(grapher);
            }
        }
        return new ResponseMessage()
                .set("shotId", String.valueOf(shot.getId()))
                ;
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseMessage get(Long uid, Double longitude, Double latitude, Integer page, Integer step){
        Pageable pageable = new PageRequest(page == null?0:page,
                step==null?12:step,
                new Sort(Sort.Direction.DESC, "createTime"));
        Page<SpecialShot> shots = specialShotRepository.findAll(pageable);
        List<Map<String, String>> myShots = new ArrayList<>();
        for(SpecialShot shot: shots){
            Map<String, String> map = new TreeMap<>();
            List<SpecialShotStill> pics = specialShotStillRepository.findByShotId(shot.getId());
            String picUrl = "";
            if(!pics.isEmpty()){
                picUrl = pics.get(0).getPic();
                picUrl = WebMvcConfig.getUrl(picUrl);
            }
            map.put("shotId", String.valueOf(shot.getId()));
            map.put("title", shot.getTitle());
            map.put("intro", shot.getIntro());
            map.put("picUrl", picUrl);
            map.put("price", String.valueOf(shot.getPrice()));
            myShots.add(map);
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(myShots))
                ;
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseMessage getDetail(long shotId){
        SpecialShot shot = specialShotRepository.findOne(shotId);
        if(shot == null){
            return new ResponseMessage(false, "no special_shot of "+shotId);
        }
        List<SpecialShotStill> stills = specialShotStillRepository.findByShotId(shot.getId());
        List<String> picUrls = stills.stream().map(item -> WebMvcConfig.getUrl(item.getPic())).collect(Collectors.toList());

        List<SpecialShotGrapher> graphers = specialShotGrapherRepository.findByShotId(shotId);
        Map<String, String> graphersMap = new TreeMap<>();
        for(SpecialShotGrapher grapher: graphers){
            User user = userRepository.findById(grapher.getGrapherId());
            graphersMap.put("uid", String.valueOf(user.getId()));
            graphersMap.put("avatar", WebMvcConfig.getUrl(user.getAvatar()));
        }
        return new ResponseMessage()
                .set("shotId", String.valueOf(shot.getId()))
                .set("picUrls", JsonHelper.toJson(picUrls))
                .set("price", String.valueOf(shot.getPrice()))
                .set("title", shot.getTitle())
                .set("intro", shot.getIntro())
                .set("summary", shot.getSummary())
                .set("date", String.valueOf(shot.getTime().getTime()))
                .set("location", shot.getLocation())
                .set("service", shot.getService())
                .set("sculpt", shot.getSculpt())
                .set("likeCount", String.valueOf(shot.getLikeCount()))
                .set("commentCount", String.valueOf(shot.getCommentCount()))
                .set("photographers", JsonHelper.toJson(graphersMap))
                ;
    }
}
