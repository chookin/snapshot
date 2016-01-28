package cmri.snapshot.api.helper;

import cmri.snapshot.api.WebMvcConfig;
import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.CommentStatRepository;
import cmri.snapshot.api.repository.CommentsRepository;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhuyin on 1/27/16.
 */
@Component
public class CommentHelper {
    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    private CommentStatRepository commentStatRepository;

    @Autowired
    private UserRepository userRepository;

    private static CommentsRepository commentsRepository_;
    private static CommentStatRepository commentStatRepository_;
    private static UserRepository userRepository_;

    @PostConstruct
    public void init(){
        commentsRepository_= commentsRepository;
        commentStatRepository_ = commentStatRepository;
        userRepository_ = userRepository;
    }
    public static List<Comments> justFindComments(long objectId, ModelType type, Integer page, Integer step){
        Pageable pageable = new PageRequest(page == null?0:page,
                step==null?12:step,
                new Sort(Sort.Direction.DESC, "time"));
        return commentsRepository_.findByObjectIdAndType(objectId, type.getVal(), pageable);
    }

    public static ResponseMessage findComments(long objectId, ModelType type, Integer page, Integer step){
        List<Comments> comments = CommentHelper.justFindComments(objectId, type, page, step);
        List<Map<String, String>> myComments = new ArrayList<>();
        for(Comments comment: comments){
            User commentator = userRepository_.findById(comment.getCommentatorId());
            Map<String, String> myComment = new TreeMap<>();
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
    public static CommentStat findStat(long objectId, ModelType type){
        return commentStatRepository_.findByObjectIdAndType(objectId, type.getVal());
    }

    public static long findCount(long objectId, ModelType modelType){
        CommentStat stat = findStat(objectId, modelType);
        if(stat == null){
            return 0L;
        }else {
            return stat.getCount();
        }
    }

    public static Comments justSave(long uid, long objectId, ModelType type, Long parent, String content){
        Comments comment = Comments.newOne();
        comment.setCommentatorId(uid);
        comment.setObjectId(objectId);
        comment.setType(ModelType.Work.getVal());
        if(parent == null){
            comment.setParent(0);
        }else {
            comment.setParent(parent);
        }
        comment.setContent(content);
        comment.setTime(new Timestamp(System.currentTimeMillis()));
        commentsRepository_.save(comment);

        CommentStat stat = findStat(objectId, type);
        if(stat == null){
            stat = new CommentStat();
            stat.setCount(1);
        }else{
            stat.setCount(stat.getCount() + 1);
        }
        commentStatRepository_.save(stat);
        return comment;
    }

    public static ResponseMessage save(long uid, long objectId, ModelType type, Long parent, String content){
        Comments comment = CommentHelper.justSave(uid, objectId, ModelType.User, parent, content);
        return new ResponseMessage()
                .set("comment", JsonHelper.toJson(comment));
    }
}
