package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Photo;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.repository.PhotoRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhuyin on 15/12/6.
 */
@RestController
@RequestMapping("/photos")
public class PhotosController {
    @Autowired
    private PhotoRepository photoRepository;

    /**
     * 获取指定用户,指定作品的照片集合.如果不指定作品id,那么获取指定用户的所有照片
     *
     * @param uid 用户id
     * @param workId 作品id
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseMessage getPhotos(long uid, Long workId){
        List<Photo> photos = workId==null? photoRepository.findByUserId(uid): photoRepository.findByUserIdAndWorksId(uid, workId);
        return new ResponseMessage()
                .set("photos", JsonHelper.toJson(photos))
                ;
    }
}
