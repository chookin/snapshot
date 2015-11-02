package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 11/2/15.
 */
@RestController
@RequestMapping("/phone")
public class PhoneVerifyCodeController {
    @RequestMapping(value = "/verificationCode", method = RequestMethod.POST)
    public ResponseMessage verificationCode(){
        return new ResponseMessage();
    }
}
