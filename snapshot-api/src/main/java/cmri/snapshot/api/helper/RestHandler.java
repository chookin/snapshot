package cmri.snapshot.api.helper;

import cmri.snapshot.api.WebAppConfig;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.lang.MapAdapter;
import cmri.utils.web.HttpConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Created by zhuyin on 11/11/15.
 */
public class RestHandler {
    MapAdapter<String, Object> paras = new MapAdapter<>();
    String baseUrl = WebAppConfig.baseUrl;
    String path;
    /**
     * 默认的key为账号‘test’的密码的2次md5值
     */
    String key = SigInterceptor.genKey(DigestUtils.md5Hex("test"));
    String method = HttpConstant.Method.POST;

    public RestHandler setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }
    public RestHandler setPath(String path){
        this.path = path;
        return this;
    }
    public RestHandler setKey(String key){
        this.key = key;
        return this;
    }
    public RestHandler add(String name, Object value){
        paras.put(name, value);
        return this;
    }

    /**
     * 重置所有参数
     */
    public RestHandler reset(){
        this.paras.clear();
        return this;
    }

    String getUrl(){
        return this.baseUrl + path;
    }
    public ResponseMessage post(){
        updateSig();
        return new TestRestTemplate().postForObject(getUrl(), getParas(), ResponseMessage.class);
    }
    public ResponseMessage get(){
        setMethod(HttpConstant.Method.GET);
        updateSig();
        return new TestRestTemplate().getForObject(getUrl() + "?" + this.paras.join("=", "&"), ResponseMessage.class);
    }
    MultiValueMap<String, Object> getParas(){
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.setAll(paras.innerMap());
        return map;
    }

    RestHandler setMethod(String method){
        this.method = method;
        return this;
    }

    RestHandler updateSig(){
        paras.put("time", System.currentTimeMillis());
        String sig = SigInterceptor.genSig(key, method, getUrl(), paras.getSorted());
        paras.put("sig", sig);
        return this;
    }
}
