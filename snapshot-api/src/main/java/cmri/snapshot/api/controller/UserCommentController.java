package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.CommentHelper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 12/4/15.
 */
@RestController
@RequestMapping("/userComment")
public class UserCommentController {
    /**
     * 对其他用户进行评论
     *
     * @param uid 用户id
     * @param userId 被评论用户的id
     * @param parent 父评论id
     * @param content 评论内容
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, Long userId, Long parent, String content){
        Assert.notNull(userId, "para 'userId' is null");
        return CommentHelper.save(uid, userId, ModelType.User, parent, content);
    }

    /**
     * 获取对用户的评论
     * @param userId 被评论的用户（或摄影师）
     * @param page 分页请求的页码
     * @param step 分页请求的每页条数
     */
    @RequestMapping(value = "/getAboutUser", method = RequestMethod.GET)
    public ResponseMessage getAboutUser(long userId, Integer page, Integer step){
        return CommentHelper.findComments(userId, ModelType.User, page, step);
    }
}
