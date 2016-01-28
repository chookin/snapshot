package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.CommentHelper;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 1/28/16.
 */
@RestController
@RequestMapping("/specialShotComment")
public class SpecialShotCommentController {
    /**
     * 对活动进行评论
     *
     * @param uid 用户id
     * @param shotId 活动id
     * @param parent 父评论id
     * @param content 评论内容
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, Long shotId, Long parent, String content){
        Assert.notNull(shotId, "para 'shotId' is null");
        return CommentHelper.save(uid, shotId, ModelType.SpecialShot, parent, content);
    }

    /**
     * 获取对活动的评论
     * @param shotId 被评论的活动
     * @param page 分页请求的页码
     * @param step 分页请求的每页条数
     */
    @RequestMapping(value = "/getAboutShot", method = RequestMethod.GET)
    public ResponseMessage getAboutShot(long shotId, Integer page, Integer step){
        return CommentHelper.findComments(shotId, ModelType.SpecialShot, page, step);
    }
}
