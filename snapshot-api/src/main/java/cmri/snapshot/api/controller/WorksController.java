package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Photo;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.Works;
import cmri.snapshot.api.repository.PhotoRepository;
import cmri.snapshot.api.repository.WorksRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuyin on 15/12/6.
 */
@RestController
@RequestMapping("/works")
public class WorksController {
    @Autowired
    private WorksRepository worksRepository;
    @Autowired
    private PhotoRepository photoRepository;

    /**
     * 添加作品
     *
     * @param request http请求
     * @param uid 用户id
     * @param name 作品名称
     * @param location 拍摄地点
     * @param imgs 作品照片, 可不指定，即只是创建作品，暂不传照片
     * @throws IOException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResponseMessage addWork(HttpServletRequest request, long uid, String name, String location,@RequestParam MultipartFile[] imgs) throws IOException {
        Works works = saveWork(uid, name, location);

        if(imgs == null) {
            List<Photo> photos = new ArrayList<>();
            for (MultipartFile img : imgs) {
                photos.add(createPhoto(request, uid, works.getId(), img));
            }
            photoRepository.save(photos);
        }
        return new ResponseMessage()
                .set("works", JsonHelper.toJson(works))
                ;
    }

    /**
     * 添加作品的照片
     *
     * @param request http请求
     * @param uid 用户id
     * @param worksId 作品id
     * @param img 照片
     */
    @RequestMapping(value = "/photos/append", method = RequestMethod.POST)
    public ResponseMessage appendPhoto(HttpServletRequest request, long uid, long worksId, @RequestParam MultipartFile img) throws IOException {
        photoRepository.save(createPhoto(request, uid, worksId, img));
        return new ResponseMessage();
    }

    /**
     * 删除作品的照片
     *
     * @param worksId 作品id
     * @param photoId 照片id
     */
    @RequestMapping(value = "/photos/delete", method = RequestMethod.POST)
    public ResponseMessage deletePhoto(long worksId, long photoId){
        photoRepository.deleteByIdAndWorksId(photoId, worksId);
        return new ResponseMessage();
    }

    Works saveWork(long uid, String name, String location){
        Works works = Works.newOne();
        works.setUserId(uid);
        works.setName(name);
        works.setLocation(location);
        worksRepository.save(works);
        return works;
    }

    Photo createPhoto(HttpServletRequest request, long uid, long worksId, MultipartFile img) throws IOException {
        String filename = ImageController.uploadImg(request, img);
        Photo photo = Photo.newOne();
        photo.setWorksId(worksId);
        photo.setUserId(uid);
        photo.setPhoto(filename);
        return photo;
    }

    /**
     * 根据作品id获取作品
     *
     * @param worksId 作品id
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseMessage get(long worksId){
        Works works = worksRepository.findOne(worksId);
        return new ResponseMessage()
                .set("works", JsonHelper.toJson(works))
                ;
    }

    /**
     * 获取指定用户的作品集合
     */
    @RequestMapping(value = "/getUserWorks", method = RequestMethod.GET)
    public ResponseMessage getUserWorks(long userId){
        List<Works> works = worksRepository.findByUserId(userId);
        return new ResponseMessage()
                .set("works", JsonHelper.toJson(works))
                ;
    }
}
