package cmri.snapshot.api.helper;

import cmri.snapshot.api.controller.ImageController;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.io.FileHelper;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 生成缩略图
 * Created by zhuyin on 11/13/15.
 */
public class ThumbnailGen {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    private final HttpServletRequest request;
    private int width = ConfigManager.getInt("avatar.width");
    private int height = ConfigManager.getInt("avatar.height");
    private String uploadPath;
    private String identity;
    private String outputFormat = "png";
    private float outputQuality = 0.8f;
    protected ThumbnailGen(HttpServletRequest request){
        this.request = request;
    }
    public static ThumbnailGen getInstance(HttpServletRequest request){
        return new ThumbnailGen(request);
    }

    public int getWidth() {
        return width;
    }

    public ThumbnailGen setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public ThumbnailGen setHeight(int height) {
        this.height = height;
        return this;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public ThumbnailGen setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
        return this;
    }

    public String getIdentity() {
        return identity;
    }

    /**
     * 设置文件名的标识
     */
    public ThumbnailGen setIdentity(String identity) {
        this.identity = identity;
        return this;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * destination file path: FilenameUtils.concat(uploadPath, identity)+"-"+width+"-"+height+ "." + outputFormat
     * @return destination file path
     * @throws IOException
     */
    public String gen(String srcFile) throws IOException {
        String imgPath = ServerHelper.getDateSubPath(uploadPath);
        String fullPath = ServerHelper.getUploadPath(request, imgPath);
        FileHelper.mkdirs(fullPath);
        String myName = identity+"-"+width+"-"+height+ "." + outputFormat;
        String fullName = FilenameUtils.concat(fullPath, myName);
        //Thumbnail读取并压缩图片
        Thumbnails.of(srcFile)
                .size(width, height)
                .outputFormat(outputFormat)
                .outputQuality(outputQuality)
                .toFile(fullName)
        ;
        LOG.info("gen avatar "+fullName);
        return FilenameUtils.concat(imgPath, myName);
    }
}
