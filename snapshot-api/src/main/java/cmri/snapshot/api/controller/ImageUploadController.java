package cmri.snapshot.api.controller;

import cmri.snapshot.api.helper.ServerHelper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhuyin on 11/12/15.
 */
@RestController
public class ImageUploadController {
    @RequestMapping(value = "/images/{imagePath}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    public FileSystemResource getFile(@PathVariable("imagePath") String imagePath, HttpServletRequest request) {
        return new FileSystemResource(ServerHelper.getUploadPath(request, "")+imagePath);
    }
}
