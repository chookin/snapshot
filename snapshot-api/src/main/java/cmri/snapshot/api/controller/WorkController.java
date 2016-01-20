package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.Photo;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.SpecialShotStill;
import cmri.snapshot.api.domain.Work;
import cmri.snapshot.api.repository.PhotoRepository;
import cmri.snapshot.api.repository.WorkRepository;
import cmri.utils.lang.JsonHelper;
import cmri.utils.lang.Pair;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhuyin on 15/12/6.
 */
@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private PhotoRepository photoRepository;

    /**
     * 添加作品
     *
     * @param request http请求
     * @param uid 用户id
     * @param name 作品名称
     * @param location 拍摄地点
     * @param img 作品照片, 可不指定，即只是创建作品，暂不传照片
     * @throws IOException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional
    public ResponseMessage addWork(HttpServletRequest request, long uid, String name, String location, @RequestParam MultipartFile[] img) throws IOException {
        Work work = saveWork(uid, name, location);

        List<Photo> photos = new ArrayList<>();
        if(img != null && img.length >0) {
            for (MultipartFile file : img) {
                photos.add(createPhoto(request, uid, work.getId(), file));
            }
        }else{
            if(request instanceof StandardMultipartHttpServletRequest){
                StandardMultipartHttpServletRequest mulRequest = (StandardMultipartHttpServletRequest) request;
                // 所有的 MultipartFile 文件都认为是图片
                Map<String, MultipartFile> stills = mulRequest.getFileMap();
                for(Map.Entry<String, MultipartFile> entry: stills.entrySet()) {
                    photos.add(createPhoto(request, uid, work.getId(), entry.getValue()));
                }
            }
        }
        if(photos.size() >0){
            photoRepository.save(photos);
        }
        return new ResponseMessage()
                .set("work", JsonHelper.toJson(work))
                ;
    }

    /**
     * 添加作品的照片
     *
     * @param request http请求
     * @param uid 用户id
     * @param workId 作品id
     * @param img 照片
     */
    @RequestMapping(value = "/photos/append", method = RequestMethod.POST)
    public ResponseMessage appendPhoto(HttpServletRequest request, long uid, long workId, @RequestParam MultipartFile img) throws IOException {
        photoRepository.save(createPhoto(request, uid, workId, img));
        return new ResponseMessage();
    }

    /**
     * 删除作品的照片
     *
     * @param workId 作品id
     * @param photoId 照片id
     */
    @RequestMapping(value = "/photos/delete", method = RequestMethod.POST)
    public ResponseMessage deletePhoto(long workId, long photoId){
        photoRepository.deleteByIdAndWorkId(photoId, workId);
        return new ResponseMessage();
    }

    Work saveWork(long uid, String name, String location){
        Work work = Work.newOne();
        work.setUserId(uid);
        work.setName(name);
        work.setLocation(location);
        workRepository.save(work);
        return work;
    }

    /**
     * 创建照片实例
     */
    Photo createPhoto(HttpServletRequest request, long uid, long workId, MultipartFile img) throws IOException {
        String filename = ImageController.uploadImg(request, img).getValue();
        Photo photo = Photo.newOne();
        photo.setWorkId(workId);
        photo.setUserId(uid);
        photo.setPhoto(filename);
        return photo;
    }

    /**
     * 根据作品id获取作品
     *
     * @param workId 作品id
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseMessage get(long workId){
        Work work = workRepository.findOne(workId);
        return new ResponseMessage()
                .set("work", JsonHelper.toJson(work))
                ;
    }

    /**
     * 获取指定作品的照片集合.如果不指定作品id
     *
     * @param workId 作品id
     */
    @RequestMapping(value = "/getPhotos", method = RequestMethod.GET)
    public ResponseMessage getPhotos(long workId){
        List<Photo> photos = photoRepository.findByWorkId(workId);
        return new ResponseMessage()
                .set("photos", JsonHelper.toJson(photos))
                ;
    }

    /**
     * 获取指定摄影师的作品集合
     */
    @RequestMapping(value = "/getWorks", method = RequestMethod.GET)
    public ResponseMessage getWorks(Long gid, Integer page, Integer step){
        Validate.notNull(gid, "para gid is null");
        Pageable pageable = new PageRequest(page == null?0:page,
                step==null?12:step,
                new Sort(Sort.Direction.DESC, "createTime"));
        List<Work> works = workRepository.findByUserId(gid, pageable);
        List<Map<String,String>> myWorks = new ArrayList<>();
        for(Work work: works){
            Map<String,String> myWork = new TreeMap<>();
            myWork.put("workId", String.valueOf(work.getId()));
            List<Photo> photos = photoRepository.findByWorkId(work.getId());
            List<String> photoUrls = photos.stream().map(p -> WebMvcConfig.getUrl(p.getPhoto())).collect(Collectors.toList());
            myWork.put("picUrls",JsonHelper.toJson(photoUrls));
            myWork.put("commentCount", String.valueOf(work.getCommentCount()));
            myWork.put("likeCount", String.valueOf(work.getLikeCount()));
            myWorks.add(myWork);
        }
        return new ResponseMessage()
                .set("works", JsonHelper.toJson(myWorks))
                ;
    }
}
