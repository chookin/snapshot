package cmri.snapshot.api.helper;

import cmri.utils.io.FileHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by zhuyin on 11/13/15.
 */
public class MultipartFileUploader {
    static String getFilename(){
        return UUID.randomUUID().toString();
    }
    private final HttpServletRequest request;
    private String uploadPath;
    private String defaultExtension;
    protected MultipartFileUploader(HttpServletRequest request){
        this.request = request;
    }
    public static MultipartFileUploader getInstance(HttpServletRequest request){
        return new MultipartFileUploader(request);
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public MultipartFileUploader setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
        return this;
    }

    public String getDefaultExtension(){
        return this.defaultExtension;
    }
    public MultipartFileUploader setDefaultExtension(String extension){
        this.defaultExtension = extension;
        return this;
    }

    /**
     * Update MultipartFile to server，the stored path is：
        request.getSession().getServletContext().getRealPath("/") +　$uploadPath + TimeHelper.toString(new Date(), "yyyyMMdd")；
     And the stored file name is：
        uuid.rand + "." + $extension

     default $uploadPath is ConfigManager.get("upload.uploadPath");
     $extension is parsed from source multipartfile name, if fail to parse, then set to defaultExtension, which is set by call method setDefaultExtension().
     * @param file MultipartFile
     * @return file name
     * @throws IOException
     */
    public String upload(MultipartFile file) throws IOException {
        String imgPath = ServerHelper.getDateSubPath(uploadPath);
        String fullPath = ServerHelper.getUploadPath(request, imgPath);
        FileHelper.mkdirs(fullPath);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(extension.isEmpty()){
            extension = defaultExtension;
        }
        String myName = getFilename() + "." + extension;
        file.transferTo(new File(FilenameUtils.concat(fullPath, myName)));
        return FilenameUtils.concat(imgPath, myName);
    }
}
