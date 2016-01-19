package cmri.snapshot.api.controller;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.CommentsRepository;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuyin on 12/4/15.
 */
@RestController
@RequestMapping("/userComment")
public class UserCommentController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentsRepository commentsRepository;

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
        Comments comment = Comments.newOne();
        comment.setCommentatorId(uid);
        comment.setObjectId(userId);
        comment.setType(CommentObject.User.getVal());
        if(parent == null){
            comment.setParent(0);
        }else {
            comment.setParent(parent);
        }
        comment.setContent(content);
        comment.setTime(new Timestamp(System.currentTimeMillis()));
        commentsRepository.save(comment);
        return new ResponseMessage()
                .set("comment", JsonHelper.toJson(comment));
    }

    /**
     * 获取对用户的评论
     * @param userId 被评论的用户（或摄影师）
     * @param page 页码
     * @param step 每页条数
     */
    @RequestMapping(value = "/getAboutUser", method = RequestMethod.GET)
    public ResponseMessage getAboutUser(long userId, int page, int step){
        List<Comments> comments = commentsRepository.findByObjectId(userId, new PageRequest(page, step, new Sort(Sort.Direction.DESC, "time")));
        List<Map<String, String>> myComments = new ArrayList<>();
        for(Comments comment: comments){
            User commentator = userRepository.findById(comment.getCommentatorId());
            Map<String, String> myComment = new HashMap<>();
            myComments.add(myComment);
            myComment.put("commentatorId", String.valueOf(commentator.getId()));
            myComment.put("avatar", WebMvcConfig.getUrl(commentator.getAvatar()));
            myComment.put("nickname", commentator.getName());
            myComment.put("content", comment.getContent());
            myComment.put("time", String.valueOf(comment.getTime().getTime()));
        }
        return new ResponseMessage()
                .set("comments", JsonHelper.toJson(myComments))
                ;
    }
}
