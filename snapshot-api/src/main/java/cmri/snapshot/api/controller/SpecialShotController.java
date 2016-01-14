package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 1/14/16.
 */
@RestController
@RequestMapping("/specialShot")
public class SpecialShotController {
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseMessage getDetail(long shotId){
        return new ResponseMessage()
                ;
    }


    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseMessage create(String title, int price, String summary, String location, String service, String sculpt, Long[] graphers ){
        return new ResponseMessage()
                ;
    }
}
