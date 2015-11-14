package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.MultipartFileUploader;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.snapshot.api.helper.ThumbnailGen;
import cmri.utils.lang.JsonHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuyin on 11/12/15.
 */
@RestController
public class ImageUploadController {
    /**
     * 上传头像
     */
    @RequestMapping(value="/user/avatar", method = RequestMethod.POST)
    public ResponseMessage uploadAvatar(HttpServletRequest request, String phoneNum, @RequestParam(value="avatar")MultipartFile file) throws Exception{
        String filename = MultipartFileUploader.getInstance(request)
                .setRelPath("image")
                .setDefaultExtension("png")
                .upload(file)
                ;
        // 生成规定大小的头像
        String avatar = ThumbnailGen.getInstance()
                .setDstPath(ServerHelper.getUploadPath(request, "avatar"))
                .setIdentity(phoneNum)
                .gen(filename);
        return new ResponseMessage()
                .set("avatar", JsonHelper.toJson(avatar));
    }
}
