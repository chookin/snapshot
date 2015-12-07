package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by feifei on 15/12/7.
 */
@RestController
@RequestMapping("/groupShot")
public class GroupShotController {
    /**
     * 用户报名参加团拍
     * @param uid 用户id
     * @param shotId 团拍活动id
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public ResponseMessage enroll(long uid, long shotId){
        // todo
        return new ResponseMessage();
    }

    /**
     * 获取团拍详情
     * @param shotId 团拍活动id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseMessage get(long shotId){
        return new ResponseMessage()
                ;
    }
}
