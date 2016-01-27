package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Comments;
import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.CommentHelper;
import cmri.utils.lang.JsonHelper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 1/20/16.
 */
@RestController
@RequestMapping("/workComment")
public class WorkCommentController {
    /**
     * 对其他用户进行评论
     *
     * @param uid 用户id
     * @param workId 摄影作品id
     * @param parent 父评论id
     * @param content 评论内容
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, Long workId, Long parent, String content){
        Assert.notNull(workId, "para 'workId' is null");
        Comments comment = CommentHelper.save(uid, workId, ModelType.Work, parent, content);

        return new ResponseMessage()
                .set("comment", JsonHelper.toJson(comment));
    }
}
