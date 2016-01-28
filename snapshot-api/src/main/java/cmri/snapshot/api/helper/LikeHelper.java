package cmri.snapshot.api.helper;

import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.LikeStatRepository;
import cmri.snapshot.api.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 1/27/16.
 */
@Component
public class LikeHelper {
    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private LikeStatRepository likeStatRepository;

    private static LikesRepository likesRepository_;
    private static LikeStatRepository likeStatRepository_;
    @PostConstruct
    public void init(){
        likesRepository_ = likesRepository;
        likeStatRepository_ = likeStatRepository;
    }

    public static LikeStat findStat(long objectId, ModelType type){
        return likeStatRepository_.findByObjectIdAndType(objectId, type.getVal());
    }

    public static long findCount(long objectId, ModelType modelType){
        LikeStat stat = findStat(objectId, modelType);
        if(stat == null){
            return 0L;
        }else {
            return stat.getCount();
        }
    }

    public static ResponseMessage add(long uid, long objectId, ModelType type){
        Likes like = new Likes();
        like.setCommentatorId(uid);
        like.setObjectId(objectId);
        like.setType(type.getVal());
        like.setTime(new Timestamp(System.currentTimeMillis()));
        likesRepository_.save(like);

        LikeStat stat = findStat(objectId, type);
        if(stat == null){
            stat = new LikeStat();
            stat.setCount(1);
        }else{
            stat.setCount(stat.getCount() + 1);
        }
        likeStatRepository_.save(stat);
        return new ResponseMessage();
    }

    public static ResponseMessage delete(long commentatorId, long objectId, ModelType type){
        LikeStat stat = findStat(objectId, type);
        if(stat != null) {
            stat.setCount(Math.max(stat.getCount() - 1, 0));
        }
        likeStatRepository_.save(stat);

        likesRepository_.delete(commentatorId, objectId, type.getVal());
        return new ResponseMessage();
    }
}
