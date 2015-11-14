package cmri.snapshot.api.helper;

import cmri.utils.configuration.ConfigManager;
import cmri.utils.io.FileHelper;
import cmri.utils.lang.TimeHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhuyin on 11/13/15.
 */
public class MultipartFileUploader {
    static int index = -1;
    static Lock lock = new ReentrantLock();

    /**
     * 产生作为文件名一部分的“计数”，避免文件并发上传时文件名重复的情况。
     */
    static int getIndex(){
        lock.lock();
        try {
            ++index;
            if(index == Integer.MAX_VALUE) index = 0;
            return index;
        }finally {
            lock.unlock();
        }
    }
    private final HttpServletRequest request;
    private String basePath = ConfigManager.get("upload.basePath");
    private String relPath;
    private String defaultExtension;
    protected MultipartFileUploader(HttpServletRequest request){
        this.request = request;
    }
    public static MultipartFileUploader getInstance(HttpServletRequest request){
        return new MultipartFileUploader(request);
    }

    public String getBasePath() {
        return basePath;
    }

    public MultipartFileUploader setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public String getRelPath(){
        return relPath;
    }

    public MultipartFileUploader setRelPath(String relPath){
        this.relPath = relPath;
        return this;
    }
    public String getDefaultExtension(){
        return this.defaultExtension;
    }
    public MultipartFileUploader setDefaultExtension(String extension){
        this.defaultExtension = extension;
        return this;
    }
    public String upload(MultipartFile file) throws IOException {
        String basePath= FilenameUtils.concat(request.getSession().getServletContext().getRealPath("/"), this.basePath);
        String uploadPath = ServerHelper.getUploadPath(basePath, relPath);
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if(extension.isEmpty()){
            extension = this.defaultExtension;
        }
        String myName = TimeHelper.toString(new Date(), "HHmmss") + "_" + getIndex() + "." + extension;
        String fullName = FilenameUtils.concat(uploadPath, myName);
        FileHelper.mkdirs(uploadPath);
        file.transferTo(new File(fullName));
        return fullName;
    }
}
