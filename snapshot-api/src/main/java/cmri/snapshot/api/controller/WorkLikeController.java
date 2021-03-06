package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.LikeHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 摄影师作品点赞
 *
 * Created by zhuyin on 1/20/16.
 */
@RestController
@RequestMapping("/workLike")
public class WorkLikeController {
    /**
     * 用户对作品点赞
     *
     * @param uid 用户id
     * @param workId 对哪个作品点赞
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, long workId){
        LikeHelper.add(uid, workId, ModelType.Work);
        return new ResponseMessage();
    }

    /**
     * 用户取消对作品点赞
     *
     * @param uid 用户id
     * @param workId 被取消点赞的作品id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessage delete(long uid, long workId){
        return LikeHelper.delete(uid, workId, ModelType.Work);
    }
}
