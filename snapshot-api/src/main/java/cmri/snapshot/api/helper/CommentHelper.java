package cmri.snapshot.api.helper;

import cmri.snapshot.api.domain.CommentStat;
import cmri.snapshot.api.domain.Comments;
import cmri.snapshot.api.domain.ModelType;
import cmri.snapshot.api.repository.CommentStatRepository;
import cmri.snapshot.api.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhuyin on 1/27/16.
 */
@Component
public class CommentHelper {
    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    private CommentStatRepository commentStatRepository;

    private static CommentsRepository commentsRepository_;
    private static CommentStatRepository commentStatRepository_;

    @PostConstruct
    public void init(){
        commentsRepository_= commentsRepository;
        commentStatRepository_ = commentStatRepository;
    }
    public static List<Comments> findComments(long objectId, ModelType type, Integer page, Integer step){
        Pageable pageable = new PageRequest(page == null?0:page,
                step==null?12:step,
                new Sort(Sort.Direction.DESC, "time"));
        return commentsRepository_.findByObjectIdAndType(objectId, type.getVal(), pageable);
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

    public static Comments save(long uid, long objectId, ModelType type, Long parent, String content){
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
}
