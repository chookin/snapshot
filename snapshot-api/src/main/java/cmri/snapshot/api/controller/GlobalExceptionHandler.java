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
     * 对于易定位故障原因的异常,只需传递错误内容给客户端,并且在日志中也只需记录错误内容.
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

    /**
     * 对于简单异常,只需传递错误内容给客户端,但在日志中需记录错误堆栈.
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ NullPointerException.class})
    @ResponseBody
    public ResponseMessage simpleError(HttpServletRequest request, Exception e) {
        String requestDesc = ServerHelper.getDesc(request);
        ResponseMessage response = new ResponseMessage(false, requestDesc + ". " + e.toString());
        LOG.error(ServerHelper.getErrorDesc(requestDesc, response.getId()), e);
        return response;
    }

    /**
     * 对于其他异常,需传递错误堆栈给客户端,并在日志中记录错误堆栈.
     */
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class})
    @ResponseBody
    public ResponseMessage error(HttpServletRequest request, Throwable e) {
        String requestDesc = ServerHelper.getDesc(request);
        ResponseMessage response = new ResponseMessage(false, requestDesc + ". " + e.toString() + "\t" + Arrays.toString(e.getStackTrace()));
        LOG.error(ServerHelper.getErrorDesc(requestDesc, response.getId()), e);
        return response;
    }
}
