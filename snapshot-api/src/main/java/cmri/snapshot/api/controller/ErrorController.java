package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.ServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhuyin on 11/23/15.
 */
@RestController
public class ErrorController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public ResponseMessage error(HttpServletRequest request, HttpServletResponse response){
        StringBuilder msg = new StringBuilder("status ").append(response.getStatus())
                ;
        return new ResponseMessage(false, msg.toString());
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    public ResponseMessage notFound(HttpServletRequest request){
        String requestDesc = ServerHelper.getDesc(request);
        ResponseMessage response = new ResponseMessage(false, requestDesc + " not found");
        String message = ServerHelper.getErrorDesc(requestDesc, response.getId())  + ": not found";
        LOG.error(message);
        return response;
    }
}
