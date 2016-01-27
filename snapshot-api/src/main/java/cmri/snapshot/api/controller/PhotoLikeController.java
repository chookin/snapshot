package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.helper.LikeHelper;
import cmri.snapshot.api.repository.LikesRepository;
import cmri.snapshot.api.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * Created by zhuyin on 12/4/15.
 */
@RestController
@RequestMapping("/photoLike")
public class PhotoLikeController {

    /**
     * 用户对照片点赞
     *
     * @param uid 用户id
     * @param photoId 对哪个照片点赞
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, long photoId){
        LikeHelper.add(uid, photoId, ModelType.Photo);
        return new ResponseMessage();
    }

    /**
     * 用户取消对照片点赞
     *
     * @param uid 用户id
     * @param photoId 被取消点赞的照片id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessage delete(long uid, long photoId){
        LikeHelper.delete(uid, photoId, ModelType.Photo);
        return new ResponseMessage();
    }
}
