package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.LikeHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 1/28/16.
 */
@RestController
@RequestMapping("/specialShotLike")
public class SpecialShotLikeController {

    /**
     * 用户对活动点赞
     *
     * @param uid 用户id
     * @param shotId 对哪个活动点赞
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, long shotId){
        return LikeHelper.add(uid, shotId, ModelType.Shot);
    }

    /**
     * 用户取消对活动点赞
     *
     * @param uid 用户id
     * @param shotId 被取消点赞的活动id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessage delete(long uid, long shotId){
        return LikeHelper.delete(uid, shotId, ModelType.Shot);
    }
}
