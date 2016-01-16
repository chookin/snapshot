package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.*;
import cmri.utils.lang.JsonHelper;
import cmri.utils.lang.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    SpecialShotStillsRepository stillsRepository;
    @Autowired
    GrapherRepository grapherRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseMessage getDetail(long shotId){
        SpecialShot shot = specialShotRepository.findOne(shotId);
        if(shot == null){
            return new ResponseMessage(false, "no special_shot of "+shotId);
        }
        List<SpecialShotStills> stills = stillsRepository.findByShotId(shot.getId());
        List<String> pics = stills.stream().map(item -> WebMvcConfig.getUrl(item.getPic())).collect(Collectors.toList());

        List<SpecialShotGrapher> graphers = specialShotGrapherRepository.findByShotId(shotId);
        Map<Long, String> graphersMap = new HashMap<>();
        for(SpecialShotGrapher grapher: graphers){
            User user = userRepository.findById(grapher.getGrapherId());
            graphersMap.put(user.getId(), WebMvcConfig.getUrl(user.getAvatar()));
        }
        return new ResponseMessage()
                .set("shotId", String.valueOf(shot.getId()))
                .set("pics", JsonHelper.toJson(pics))
                .set("price", String.valueOf(shot.getPrice()))
                .set("title", shot.getTitle())
                .set("intro", shot.getIntro())
                .set("summary", shot.getSummary())
                .set("date", String.valueOf(shot.getTime()))
                .set("location", shot.getLocation())
                .set("service", shot.getService())
                .set("sculpt", shot.getSculpt())
                .set("likeCount", String.valueOf(shot.getLikeCount()))
                .set("commentsCount", String.valueOf(shot.getCommentCount()))
                .set("photographers", JsonHelper.toJson(graphersMap))
                ;
    }

    /**
     *
     * @param uid 用户id, long
     * @param title 标题, string
     * @param price 价格，单位元，int类型
     * @param summary 活动内容, string
     * @param location 位置, string
     * @param service 服务, string
     * @param sculpt 造型, string
     * @param grapherIds 摄影师id，格式[id1,id2]
     * @throws IOException
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseMessage create(long uid, HttpServletRequest request, String title, String intro, int price, String summary, String location, String service, String sculpt, String grapherIds ) throws IOException {
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
                SpecialShotStills entity = new SpecialShotStills();
                entity.setShotId(shot.getId());
                entity.setPic(pair.getValue());
                stillsRepository.save(entity);
            }
        }
        if(grapherIds != null) {
            List<Long> myGrapherIds = new Gson().fromJson(grapherIds, new TypeToken<List<Long>>() {
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
}
