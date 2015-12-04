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
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseMessage delete(long uid, long photoId){
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
}
