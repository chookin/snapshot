package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.helper.LikeHelper;
import cmri.snapshot.api.repository.LikesRepository;
import cmri.snapshot.api.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * 摄影师作品点赞
 *
 * Created by zhuyin on 1/20/16.
 */
@RestController
@RequestMapping("/workLike")
public class WorkLikeController {
    /**
     * 用户对照片点赞
     *
     * @param uid 用户id
     * @param workId 对哪个照片点赞
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, long workId){
        LikeHelper.add(uid, workId, ModelType.Work);
        return new ResponseMessage();
    }
}
