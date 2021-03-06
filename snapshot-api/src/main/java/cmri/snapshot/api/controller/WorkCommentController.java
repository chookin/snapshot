package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.CommentHelper;
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
     * 对作品进行评论
     *
     * @param uid 用户id
     * @param workId 摄影作品id
     * @param parent 父评论id
     * @param content 评论内容
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, Long workId, Long parent, String content){
        Assert.notNull(workId, "para 'workId' is null");
        return CommentHelper.save(uid, workId, ModelType.Work, parent, content);
    }

    /**
     * 获取对作品的评论
     * @param workId 被评论的作品
     * @param page 分页请求的页码
     * @param step 分页请求的每页条数
     */
    @RequestMapping(value = "/getAboutWork", method = RequestMethod.GET)
    public ResponseMessage getAboutUser(long workId, Integer page, Integer step){
        return CommentHelper.findComments(workId, ModelType.Work, page, step);
    }
}
