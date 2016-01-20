package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.*;
import cmri.snapshot.api.repository.LikesRepository;
import cmri.snapshot.api.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * 摄影师作品点赞
 *
 * Created by zhuyin on 1/20/16.
 */
@RestController
@RequestMapping("/workLike")
public class WorkLikeController {
    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private LikesRepository likeRepository;

    /**
     * 用户对照片点赞
     *
     * @param uid 用户id
     * @param workId 对哪个照片点赞
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseMessage add(long uid, long workId){
        Work work = workRepository.findOne(workId);
        work.setLikeCount(work.getLikeCount() + 1);
        workRepository.save(work);

        Likes like = new Likes();
        like.setCommentatorId(uid);
        like.setObjectId(workId);
        like.setType(CommentObject.Work.getVal());
        like.setTime(new Timestamp(System.currentTimeMillis()));
        likeRepository.save(like);
        return new ResponseMessage();
    }
}
