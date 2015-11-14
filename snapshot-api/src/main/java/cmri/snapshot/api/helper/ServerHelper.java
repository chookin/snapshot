package cmri.snapshot.api.helper;

import cmri.utils.configuration.ConfigManager;
import cmri.utils.lang.TimeHelper;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhuyin on 11/14/15.
 */
public class ServerHelper {

    /**
     * Get file full name of uploaded resource.
     * filename = request.getSession().getServletContext().getRealPath("/") + ConfigManager.get("upload.basePath") + relPath + time('yyyyMMdd')
     * @param relPath the relative path, please not start with "/", or else the base path is no use.
     */
    public static String getUploadPath(HttpServletRequest request, String relPath){
        String basePath = ConfigManager.get("upload.basePath");
        basePath= FilenameUtils.concat(request.getSession().getServletContext().getRealPath("/"), basePath);
        return getUploadPath(basePath, relPath);
    }

    public static String getUploadPath(String basePath, String relPath){
        String uploadPath = FilenameUtils.concat(basePath, relPath);
        uploadPath = FilenameUtils.concat(uploadPath, TimeHelper.toString(new Date(), "yyyyMMdd"));
        return uploadPath;
    }
}
