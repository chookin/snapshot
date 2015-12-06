package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.UserComment;
import cmri.snapshot.api.repository.UserCommentRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhuyin on 12/4/15.
 */
@RestController
@RequestMapping("/userComment")
public class UserCommentController {
    @Autowired
    private UserCommentRepository userCommentRepository;

    /**
     * 对其他用户进行评论
     *
     * @param uid 用户id
     * @param object 其他用户的id
     * @param parent 父评论id
     * @param content 评论内容
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, Long object, Long parent, String content){
        Assert.notNull(object, "para 'object' is null");
        UserComment comment = UserComment.newOne();
        comment.setUserId(uid);
        comment.setObject(object);
        if(parent == null){
            comment.setParent(0);
        }else {
            comment.setParent(parent);
        }
        comment.setContent(content);
        comment.setTime(new Timestamp(System.currentTimeMillis()));
        userCommentRepository.save(comment);
        return new ResponseMessage()
                .set("comment", JsonHelper.toJson(comment));
    }

    /**
     * 获取对指定用户的评论
     */
    @RequestMapping(value = "/getAboutUser", method = RequestMethod.POST)
    public ResponseMessage getAboutUser(long uid){
        List<UserComment> comments = userCommentRepository.findCommentsAboutUser(uid);
        return new ResponseMessage()
                .set("comments", JsonHelper.toJson(comments))
                ;
    }
}
