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
    private boolean succeed;
    private String message;
    private Map<String, String> data = new HashMap<>();
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

    @Override
    public int hashCode() {
        return (data.toString() + message + succeed).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseMessage)) return false;

        ResponseMessage that = (ResponseMessage) o;

        if (succeed != that.succeed) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return data.equals(that.data);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "succeed=" + succeed +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
