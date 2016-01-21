package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.GroupShotGrapherRepository;
import cmri.snapshot.api.repository.GroupShotRepository;
import cmri.snapshot.api.repository.GroupShotStillRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhuyin on 15/12/7.
 */
@RestController
@RequestMapping("/groupShot")
public class GroupShotController {
    @Autowired
    private GroupShotRepository groupShotRepository;
    @Autowired
    private GroupShotStillRepository groupShotStillRepository;
    @Autowired
    private GroupShotGrapherRepository groupShotGrapherRepository;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseMessage create(HttpServletRequest request, long uid, String title, String intro, int price, String summary, String location, String service, String grapherIds ) throws IOException {
        GroupShot shot = GroupShot.newOne();
        shot.setTitle(title);
        shot.setIntro(intro);
        shot.setPrice(price);
        shot.setSummary(summary);
        shot.setLocation(location);
        shot.setService(service);
        shot.setCreator(uid);
        groupShotRepository.save(shot);

        if(request instanceof StandardMultipartHttpServletRequest){
            StandardMultipartHttpServletRequest mulRequest = (StandardMultipartHttpServletRequest) request;
            // 所有的 MultipartFile 文件都认为是剧照图片
            Map<String, MultipartFile> stills = mulRequest.getFileMap();
            for(Map.Entry<String, MultipartFile> entry: stills.entrySet()) {
                Pair<String, String> pair = ImageController.uploadImg(request, entry.getValue());
                GroupShotStill entity = new GroupShotStill();
                entity.setShotId(shot.getId());
                entity.setPic(pair.getValue());
                groupShotStillRepository.save(entity);
            }
        }
        if(grapherIds != null) {
            List<Long> myGrapherIds = new Gson().fromJson(grapherIds, new TypeToken<List<Long>>() {
            }.getType());
            for (long grapherId : myGrapherIds) {
                GroupShotGrapher grapher = new GroupShotGrapher();
                grapher.setShotId(shot.getId());
                grapher.setGrapherId(grapherId);
                groupShotGrapherRepository.save(grapher);
            }
        }
        return new ResponseMessage()
                .set("shotId", String.valueOf(shot.getId()))
                ;
    }

    /**
     * 用户报名参加团拍
     * @param uid 用户id
     * @param shotId 团拍活动id
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public ResponseMessage enroll(long uid, long shotId){
        // todo
        return new ResponseMessage();
    }

    /**
     * 获取团拍详情
     * @param shotId 团拍活动id
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseMessage getDetail(long shotId){
        return new ResponseMessage()
                ;
    }

    /**
     * 获取团拍列表
     *
     * @param uid 用户id，可选
     * @param longitude 经度，可选
     * @param latitude 纬度，可选
     * @param page 分页请求的页数，可选
     * @param step 分页请求的每页多少条，可选
     * @param sortType price，distance，date三类，用于按照价格、距离、时间排序，默认为价格
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseMessage get(Long uid, Double longitude, Double latitude, Integer page, Integer step, Integer sortType) {
        Pageable pageable = new PageRequest(page == null ? 0 : page,
                step == null ? 12 : step,
                new Sort(Sort.Direction.DESC, "createTime"));
        Page<GroupShot> shots = groupShotRepository.findAll(pageable);
        List<Map<String, String>> myShots = new ArrayList<>();
        for(GroupShot shot: shots){
            Map<String, String> map = new TreeMap<>();
            List<GroupShotStill> pics = groupShotStillRepository.findByShotId(shot.getId());
            String picUrl = "";
            if(!pics.isEmpty()){
                picUrl = pics.get(0).getPic();
                picUrl = WebMvcConfig.getUrl(picUrl);
            }
            map.put("shotId", String.valueOf(shot.getId()));
            map.put("title", shot.getTitle());
            map.put("intro", shot.getIntro());
            map.put("location", shot.getLocation());
            map.put("photographerCount", String.valueOf(3));
            map.put("picUrl", picUrl);
            map.put("price", String.valueOf(shot.getPrice()));
            map.put("startTime", String.valueOf(shot.getStartTime()==null?"":shot.getStartTime().getTime()));
            map.put("endTime", String.valueOf(shot.getEndTime()==null ? "":shot.getEndTime().getTime()));
            map.put("minNumber", String.valueOf(shot.getMinNumber()));
            map.put("maxNumber", String.valueOf(shot.getMaxNumber()));
            map.put("likeCount", String.valueOf(shot.getLikeCount()));
            map.put("commentCount", String.valueOf(shot.getCommentCount()));
            myShots.add(map);
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(myShots))
                ;
    }
}
