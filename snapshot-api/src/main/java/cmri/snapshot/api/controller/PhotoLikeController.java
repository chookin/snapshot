package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Photo;
import cmri.snapshot.api.domain.PhotoLike;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.repository.PhotoLikeRepository;
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
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PhotoLikeRepository photoLikeRepository;

    /**
     * 用户对照片点赞
     *
     * @param uid 用户id
     * @param photoId 对哪个照片点赞
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, long photoId){
        Photo photo = photoRepository.findOne(photoId);
        photo.setLikeCount(photo.getLikeCount() + 1);
        photoRepository.save(photo);

        PhotoLike like = new PhotoLike();
        like.setUserId(uid);
        like.setPhotoId(photoId);
        like.setTime(new Timestamp(System.currentTimeMillis()));
        photoLikeRepository.save(like);
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
        Photo photo = photoRepository.findOne(photoId);
        photo.setLikeCount(Math.max(photo.getLikeCount()-1, 0));
        photoRepository.save(photo);

        photoLikeRepository.delete(uid, photoId);
        return new ResponseMessage();
    }
}
