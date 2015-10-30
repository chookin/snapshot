package cmri.snapshot.api.domain;

/**
 * Created by zhuyin on 10/28/15.
 */
public class ResponseMessage {
    public ResponseMessage(boolean success, String errorInfo){
        this.result = success? "success":"failed";
        this.errorInfo = errorInfo;
    }
    public String getResult() {
        return result;
    }
    public String getErrorInfo() {
        return errorInfo;
    }

    private String result;
    private String errorInfo;
}
