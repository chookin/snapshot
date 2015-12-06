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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feifei on 15/12/6.
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
     * @param imgs 作品照片
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResponseMessage addWork(HttpServletRequest request, long uid, String name, String location, MultipartFile[] imgs) throws IOException {
        Works works = Works.newOne();
        works.setUserId(uid);
        works.setName(name);
        works.setLocation(location);
        worksRepository.save(works);

        List<Photo> photos = new ArrayList<>();
        for(MultipartFile img: imgs){
            String filename = ImageController.uploadImg(request, img);
            Photo photo = Photo.newOne();
            photo.setWorksId(works.getId());
            photo.setUserId(uid);
            photo.setPhoto(filename);
            photos.add(photo);
        }
        photoRepository.save(photos);
        return new ResponseMessage()
                ;

    }

    /**
     * 根据作品id获取作品
     *
     * @param worksId 作品id
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseMessage getWorks(long worksId){
        Works works = worksRepository.findOne(worksId);
        return new ResponseMessage()
                .set("works", JsonHelper.toJson(works))
                ;
    }

    /**
     * 获取指定用户的作品集合
     */
    @RequestMapping(value = "/getUserWorks", method = RequestMethod.POST)
    public ResponseMessage getUserWorks(long uid){
        List<Works> works = worksRepository.findByUserId(uid);
        return new ResponseMessage()
                .set("works", JsonHelper.toJson(works))
                ;
    }
}
