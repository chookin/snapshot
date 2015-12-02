package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.MultipartFileUploader;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.TimeHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhuyin on 11/12/15.
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public ResponseMessage upload(HttpServletRequest request, @RequestParam(value = "img") MultipartFile file) throws IOException{
        String filename = uploadImg(request, file);
        return new ResponseMessage()
                .set("filename", filename)
                .set("url", WebMvcConfig.getUrl(filename))
                ;
    }
    public static String uploadImg(HttpServletRequest request, MultipartFile file) throws IOException {
        String uploadPath = FilenameUtils.concat(ConfigManager.get("upload.basePath"), "image");
        return MultipartFileUploader.getInstance(request)
                .setUploadPath(uploadPath)
                .setDefaultExtension("png")
                .upload(file)
                ;
    }
}
