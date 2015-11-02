package cmri.snapshot.api.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuyin on 10/28/15.
 */
public class ResponseMessage {
    public ResponseMessage(){
        this(true, "");
    }
    public ResponseMessage(boolean succeed, String message){
        this.succeed = succeed;
        this.message = message;
    }
    public boolean isSucceed() {
        return succeed;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getData() {
        return data;
    }
    public ResponseMessage set(String key, String val){
        data.put(key, val);
        return this;
    }
    private boolean succeed;
    private String message;
    private Map<String, String> data = new HashMap<>();
}
