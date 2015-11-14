package cmri.snapshot.api.helper;

import cmri.utils.configuration.ConfigManager;
import cmri.utils.io.FileHelper;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 生成缩略图
 * Created by zhuyin on 11/13/15.
 */
public class ThumbnailGen {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    private int width = ConfigManager.getInt("avatar.width");
    private int height = ConfigManager.getInt("avatar.height");
    private String dstPath;
    private String identity;
    private String outputFormat = "png";
    private float outputQuality = 0.8f;
    protected ThumbnailGen(){}
    public static ThumbnailGen getInstance(){
        return new ThumbnailGen();
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

    public String getDstPath() {
        return dstPath;
    }

    public ThumbnailGen setDstPath(String dstPath) {
        this.dstPath = dstPath;
        return this;
    }

    public String getIdentity() {
        return identity;
    }

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

    public String gen(String srcFile) throws IOException {
        FileHelper.mkdirs(dstPath);
        String fileName = FilenameUtils.concat(dstPath, identity)+"-"+width+"-"+height+ "." + outputFormat;
        //Thumbnail读取并压缩图片
        Thumbnails.of(srcFile)
                .size(width, height)
                .outputFormat(outputFormat)
                .outputQuality(outputQuality)
                .toFile(fileName)
        ;
        LOG.info("gen avatar "+fileName);
        return fileName;
    }
}
