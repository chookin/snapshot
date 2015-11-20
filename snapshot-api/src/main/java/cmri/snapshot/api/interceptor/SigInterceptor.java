package cmri.snapshot.api.interceptor;


import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.helper.ParasHelper;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.exception.AuthException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhuyin on 11/2/15.
 */
@Service
public class SigInterceptor extends HandlerInterceptorAdapter {
    public static final String defaultKey = ConfigManager.get("sig.defaultKey");

    @Autowired
    private UserRepository userRepository;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        validate(request);
        return true;
    }
    void validate(HttpServletRequest request) {
        String sig = request.getParameter("sig");
        Validate.isTrue(StringUtils.isNotEmpty(sig), "para 'sig' is empty");
        String url = request.getRequestURL().toString();
        if(url.endsWith("user/register") || url.endsWith("authCode/send") || url.endsWith("captcha")){
            validate(request.getMethod(),
                    request.getRequestURL().toString(),
                    ParasHelper.getParas(request).getSorted(),
                    sig);
        }else{
            String username = request.getParameter("username");
            if(username != null){
                Validate.isTrue(StringUtils.isNotEmpty(username), "para 'username' is empty");
                validate(username,
                        request.getMethod(),
                        request.getRequestURL().toString(),
                        ParasHelper.getParas(request).getSorted(),
                        sig);
            }else{
                String str = request.getParameter("phoneNum");
                Validate.isTrue(StringUtils.isNotEmpty(str), "please assign 'username' or 'phoneNum'");
                long phoneNum = Long.parseLong(str);
                validate(phoneNum,
                        request.getMethod(),
                        request.getRequestURL().toString(),
                        ParasHelper.getParas(request).getSorted(),
                        sig);
            }
        }
    }
    void validate(String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        String mySig = genSig(httpMethod, url, paras);
        if(sig.equals(mySig)){
            return;
        }
        throw new AuthException("签名校验失败");
    }
    User validate(String username, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        User user = userRepository.findByName(username);
        if(user == null)
            throw new AuthException("不存在用户 "+username);
        String key = genKey(user.getPassword());
        String mySig = genSig(key, httpMethod, url, paras);
        if(sig.equals(mySig)){
            return user;
        }
        throw new AuthException("签名校验失败");
    }

    User validate(Long phoneNum, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        User user = userRepository.findByMobile(phoneNum);
        if(user == null)
            throw new AuthException("不存在用户 "+phoneNum);
        return validate(user, httpMethod, url, paras, sig);
    }

    User validate(User user, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        String key = genKey(user.getPassword());
        String mySig = genSig(key, httpMethod, url, paras);
        if(!sig.equals(mySig)) {
            throw new AuthException("签名校验失败");
        }
        return user;
    }
    public static String genKey(String password){
        return DigestUtils.md5Hex(password);
    }
    public static String genSig(String httpMethod, String url, TreeMap<String, Object> paras){
        return genSig(defaultKey, httpMethod, url, paras);
    }
    public static String genSig(String key, String httpMethod, String url, TreeMap<String, Object> paras){
        StringBuilder strb = new StringBuilder(httpMethod).append(url);
        for(Map.Entry<String, Object> entry: paras.entrySet()){
            if(entry.getValue() instanceof FileSystemResource){
                continue;
            }
            if(entry.getKey().equals("sig")){
                continue;
            }
            strb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }
        strb.append(key);
       return genSig(strb.toString());
    }
    public static String genSig(String str){
//        return DigestUtils.md5Hex(str);
        try {
            return DigestUtils.md5Hex(URLEncoder.encode(str, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }
    }
}
