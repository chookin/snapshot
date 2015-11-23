package cmri.snapshot.api.helper;

import cmri.snapshot.api.Application;
import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.lang.MapAdapter;
import cmri.utils.web.HttpConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by zhuyin on 11/11/15.
 */
public class RestHandler {
    MapAdapter<String, Object> paras = new MapAdapter<>();
    String baseUrl = Application.baseUrl;
    String path;
    /**
     * 默认的key为账号‘test’的密码的2次md5值
     */
    String secretKey = SigInterceptor.genKey(DigestUtils.md5Hex("test"));
    String method = HttpConstant.Method.POST;
    HttpHeaders headers = new HttpHeaders();
    public RestHandler(){
        initHeader();
    }
    void initHeader(){
//        header("Accept-Charset", "utf-8");
//        header("Content-type", "multipart/form-data; charset=utf-8");
    }
    public RestHandler setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        return this;
    }
    public RestHandler setPath(String path){
        this.path = path;
        return this;
    }
    public RestHandler setSecretKey(String secretKey){
        this.secretKey = secretKey;
        return this;
    }
    public RestHandler add(String name, Object value){
        paras.put(name, value);
        return this;
    }
    public RestHandler header(String key, String value){
        this.headers.add(key, value);
        return this;
    }
    /**
     * 重置所有参数
     */
    public RestHandler reset(){
        this.paras.clear();
        this.headers.clear();
        initHeader();
        return this;
    }

    String getUrl(){
        return this.baseUrl + path;
    }
    public ResponseMessage post(){
        updateSig();
        return justPost();
    }
    public ResponseMessage justPost(){
        HttpEntity<MultiValueMap<String, Object>> requestEntity  = new HttpEntity<>(getParas(), headers);
        return getRestTemplate()
                .postForObject(getUrl(), requestEntity, ResponseMessage.class);
    }
    public ResponseMessage get(){
        setMethod(HttpConstant.Method.GET);
        updateSig();
        return getRestTemplate().getForObject(getUrl() + "?" + this.paras.join("=", "&"), ResponseMessage.class);
    }
    RestTemplate getRestTemplate(){
        RestTemplate template = new TestRestTemplate();
        template.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return template;
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
        if(!paras.containsKey("time")) {
            paras.put("time", System.currentTimeMillis());
        }
        String sig = SigInterceptor.genSig(secretKey, method, getUrl(), paras.getSorted());
        paras.put("sig", sig);
        return this;
    }
}
