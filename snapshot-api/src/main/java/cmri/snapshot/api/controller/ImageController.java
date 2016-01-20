package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.Pic;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.MultipartFileUploader;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.snapshot.api.repository.PicRepository;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.JsonHelper;
import cmri.utils.lang.Pair;
import cmri.utils.lang.TimeHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by zhuyin on 11/12/15.
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private PicRepository picRepository;

    /**
     * 上传图片文件
     *
     * @param request http 请求
     * @param file 照片文件
     * @throws IOException
     */
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public ResponseMessage upload(HttpServletRequest request, @RequestParam(value = "img") MultipartFile file, String title) throws IOException{
        Pair<String, String> pair = uploadImg(request, file);
        picRepository.save(Pic.newOne().setPath(pair.getValue()).setTitle(getTitle(pair.getKey(), title)));
        return new ResponseMessage()
                .set("filename", pair.getValue())
                .set("url", WebMvcConfig.getUrl(pair.getValue()))
                ;
    }

    /**
     * 上传图片文件，一次可以上传多张
     * @param request 请求
     * @param files 照片文件
     * @throws IOException
     */
    @RequestMapping(value="/uploadMultiple", method = RequestMethod.POST)
    public ResponseMessage upload(HttpServletRequest request, @RequestParam(value = "img") MultipartFile[] files) throws IOException{
        List<String> filenames = new ArrayList<>();
        List<Pic> pics = new ArrayList<>();
        for(MultipartFile file: files){
            Pair<String, String> pair = uploadImg(request, file);
            Pic pic = Pic.newOne().setTitle(getTitle(pair.getKey(), "")).setPath(pair.getValue());
            pics.add(pic);
            filenames.add(pair.getValue());
        }
        picRepository.save(pics);
        return new ResponseMessage()
                .set("count", String.valueOf(filenames.size()))
                .set("filenames", JsonHelper.toJson(filenames))
                ;
    }

    /**
     * 获取图片列表
     * @param since 指定起始时间，获取自该时刻至今所上传的图片
     */
    @ResponseBody
    @RequestMapping(value="/get", method = RequestMethod.GET)
    public ResponseMessage get(Long since){
        long myTime = since == null ? 0: since;
        List<Pic> pics = picRepository.findSince(new Timestamp(myTime));
        List<Map<String, String>> items = new ArrayList<>();
        for(Pic pic : pics){
            Map<String, String> item = new TreeMap<>();
            item.put("title", pic.getTitle()==null?"":pic.getTitle());
            item.put("url", WebMvcConfig.getUrl(pic.getPath()));
            items.add(item);
        }
        return new ResponseMessage()
                .set("items", JsonHelper.toJson(items));
    }

    /**
     * 获取文件名
     */
    public static String getTitle(String filename, String title){
        return StringUtils.isEmpty(title)?FilenameUtils.getBaseName(filename):title;
    }

    /**
     * 上传图片文件
     *
     * @param request http 请求
     * @param file 照片文件
     * @return Pair of file original name and file saved path. Saved path is relative to server context path.
     * @throws IOException
     */
    public static Pair<String, String> uploadImg(HttpServletRequest request, MultipartFile file) throws IOException {
        String uploadPath = FilenameUtils.concat(ConfigManager.get("upload.basePath"), "image");
        return MultipartFileUploader.getInstance(request)
                .setUploadPath(uploadPath)
                .setDefaultExtension("png")
                .upload(file)
                ;
    }
}
