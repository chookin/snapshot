package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.helper.ServerHelper;
import cmri.utils.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 全局异常处理
 *
 * The exceptions below could be raised by any controller and they would be handled here, if not handled in the controller already.
 * Created by zhuyin on 10/30/15.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * Convert a predefined exception to an HTTP Status code.
     */

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class, AuthException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResponseMessage tinyError(HttpServletRequest request, Exception e) {
        String requestDesc = ServerHelper.getDesc(request);
        ResponseMessage response = new ResponseMessage(false, requestDesc + ". " + e.getMessage());
        String message = ServerHelper.getErrorDesc(requestDesc, response.getId())+": "+e.getMessage();
        LOG.error(message);
        return response;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ NullPointerException.class})
    @ResponseBody
    public ResponseMessage simpleError(HttpServletRequest request, Exception e) {
        ResponseMessage response = new ResponseMessage(false, e.getMessage());
        LOG.error(ServerHelper.getErrorDesc(ServerHelper.getDesc(request), response.getId()), e);
        return response;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class})
    @ResponseBody
    public ResponseMessage error(HttpServletRequest request, Throwable e) {
        ResponseMessage response = new ResponseMessage(false, e.toString() + "\t" + Arrays.toString(e.getStackTrace()));
        LOG.error(ServerHelper.getErrorDesc(ServerHelper.getDesc(request), response.getId()), e);
        return response;
    }
}
