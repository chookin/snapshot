package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Grapher;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.ShotRelease;
import cmri.snapshot.api.domain.ShotStill;
import cmri.snapshot.api.repository.GrapherRepository;
import cmri.snapshot.api.repository.ShotReleaseRepository;
import cmri.snapshot.api.repository.ShotStillRepository;
import cmri.utils.lang.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by zhuyin on 1/17/16.
 */
@RestController
@RequestMapping("/shot")
public class ShotController {
    @Autowired
    GrapherRepository grapherRepository;
    @Autowired
    ShotReleaseRepository shotReleaseRepository;
    @Autowired
    ShotStillRepository stillsRepository;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseMessage create(HttpServletRequest request, long uid, String location) throws IOException {
        Grapher grapher = grapherRepository.findByUserId(uid);
        if(grapher == null){
            return new ResponseMessage(false, "user " + uid + " is not a photographer");
        }
        ShotRelease shot = ShotRelease.newOne();
        shot.setGrapherId(uid);
        shot.setLocation(location);
        shotReleaseRepository.save(shot);

        if(request instanceof StandardMultipartHttpServletRequest){
            StandardMultipartHttpServletRequest mulRequest = (StandardMultipartHttpServletRequest) request;
            // 所有的 MultipartFile 文件都认为是剧照图片
            Map<String, MultipartFile> stills = mulRequest.getFileMap();
            for(Map.Entry<String, MultipartFile> entry: stills.entrySet()) {
                Pair<String, String> pair = ImageController.uploadImg(request, entry.getValue());
                ShotStill entity = new ShotStill();
                entity.setShotId(shot.getId());
                entity.setPic(pair.getValue());
                stillsRepository.save(entity);
            }
        }

        return new ResponseMessage()
                .set("shotId", String.valueOf(shot.getId()))
                ;
    }
}
