package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.utils.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, AuthException.class})
    @ResponseBody
    public ResponseMessage nullError(Exception exception) {
        return new ResponseMessage(false, exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class})
    @ResponseBody
    public ResponseMessage error(Exception exception) {
        return new ResponseMessage(false, exception.toString() + "\t" + Arrays.toString(exception.getStackTrace()));
    }
}
