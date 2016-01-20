package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.CommentObject;
import cmri.snapshot.api.domain.Comments;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.domain.Work;
import cmri.snapshot.api.repository.CommentsRepository;
import cmri.snapshot.api.repository.WorkRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * Created by zhuyin on 1/20/16.
 */
@RestController
@RequestMapping("/workComment")
public class WorkCommentController {
    @Autowired
    WorkRepository workRepository;
    @Autowired
    CommentsRepository commentsRepository;

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
        Comments comment = Comments.newOne();
        comment.setCommentatorId(uid);
        comment.setObjectId(workId);
        comment.setType(CommentObject.Work.getVal());
        if(parent == null){
            comment.setParent(0);
        }else {
            comment.setParent(parent);
        }
        comment.setContent(content);
        comment.setTime(new Timestamp(System.currentTimeMillis()));
        commentsRepository.save(comment);

        Work work = workRepository.findOne(workId);
        work.setCommentCount(work.getCommentCount() + 1);
        workRepository.save(work);

        return new ResponseMessage()
                .set("comment", JsonHelper.toJson(comment));
    }
}
