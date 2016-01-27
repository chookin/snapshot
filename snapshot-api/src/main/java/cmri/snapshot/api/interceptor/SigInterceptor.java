package cmri.snapshot.api.interceptor;


import cmri.snapshot.api.domain.User;
import cmri.snapshot.api.helper.ParasHelper;
import cmri.snapshot.api.repository.UserRepository;
import cmri.utils.configuration.ConfigManager;
import cmri.utils.exception.AuthException;
import cmri.utils.lang.JsonHelper;
import cmri.utils.web.HttpConstant;
import cmri.utils.web.UrlHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

/**
 * 签名校验
 * Created by zhuyin on 11/2/15.
 */
@Service
public class SigInterceptor extends HandlerInterceptorAdapter {
    protected static final Logger LOG = LoggerFactory.getLogger(SigInterceptor.class);
    public static final String defaultKey = ConfigManager.get("sig.defaultKey");
    /**
     * 用户注册和发送验证码这两个api比较特殊，虽然都是post方法，但是采用default secret key 计算签名
     */
    static String[] withoutAuthPaths = {"/user/register", "/sms/authCode"};
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        validate(request);
        return true;
    }

    void validate(HttpServletRequest request) {
        String sig = request.getParameter("sig");
        Validate.isTrue(StringUtils.isNotEmpty(sig), "para 'sig' is empty");
        if (withoutAuth(request)) {
            validateWithoutAuth(request.getMethod(),
                    request.getRequestURL().toString(),
                    ParasHelper.getParas(request).getSorted(),
                    sig);
        } else {
            validateWithAuth(request, sig);
        }
    }

    /**
     * Check whether or not need validating user info.
     *
     * @return true if not need, or else false.
     */
    static boolean withoutAuth(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpConstant.Method.GET) || withoutAuthOnPost(request.getRequestURL().toString());
    }

    static boolean withoutAuthOnPost(String url) {
        String myPath = UrlHelper.getPath(url);
        for (String path : withoutAuthPaths) {
            if (myPath.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从数据库查询用户信息，基于用户密码校验签名是否正确
     */
    void validateWithAuth(HttpServletRequest request, String sig) {
        String username = request.getParameter("username");
        String phoneNum = request.getParameter("phoneNum");
        String uid = request.getParameter("uid");
        if (username != null) {
            Validate.isTrue(StringUtils.isNotEmpty(username), "para 'username' is empty");
            validateByUsername(username,
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    ParasHelper.getParas(request).getSorted(),
                    sig);
        } else if (phoneNum != null) {
            Long myPhoneNum = Long.valueOf(phoneNum);
            validateByPhoneNum(myPhoneNum,
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    ParasHelper.getParas(request).getSorted(),
                    sig);
        } else if (uid != null) {
            Long myUid = Long.valueOf(uid);
            validateByUid(myUid,
                    request.getMethod(),
                    request.getRequestURL().toString(),
                    ParasHelper.getParas(request).getSorted(),
                    sig);
        } else {
            throw new AuthException("please assign 'username', 'phoneNum' or 'uid'");
        }
    }

    void validateWithoutAuth(String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        String mySig = genSig(defaultKey, httpMethod, url, paras);
        if (sig.equals(mySig)) {
            return;
        }
        throw new AuthException("Fail on validating signature");
    }

    User validateByUsername(String username, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        User user = userRepository.findByName(username);
        if (user == null)
            throw new AuthException("No user of " + username);
        return validateByUser(user, httpMethod, url, paras, sig);
    }

    User validateByPhoneNum(Long phoneNum, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        User user = userRepository.findByPhone(phoneNum);
        if (user == null)
            throw new AuthException("No user of " + phoneNum);
        return validateByUser(user, httpMethod, url, paras, sig);
    }

    User validateByUid(long uid, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        User user = userRepository.findById(uid);
        if (user == null)
            throw new AuthException("No user of " + uid);
        return validateByUser(user, httpMethod, url, paras, sig);
    }

    User validateByUser(User user, String httpMethod, String url, TreeMap<String, Object> paras, String sig) {
        String mySig = genSig(genKey(user.getPassword()), httpMethod, url, paras);
        if (!sig.equals(mySig)) {
            throw new AuthException("Fail on validating signature");
        }
        return user;
    }

    public static String genKey(String password) {
        return DigestUtils.md5Hex(password);
    }

    public static String genSig(String key, String httpMethod, String url, TreeMap<String, Object> paras) {
        StringBuilder strb = new StringBuilder(httpMethod).append(url);
        for (Map.Entry<String, Object> entry : paras.entrySet()) {
            if (entry.getValue() instanceof FileSystemResource) {
                continue;
            }
            if (entry.getKey().equals("sig")) {
                continue;
            }
            strb.append(entry.getKey())
                    .append("=");
            if(entry.getValue() instanceof String){
                strb.append(entry.getValue());
            }else {
                // JsonHelper.toJson 会自动地给字符串类型的添加""
                strb.append(JsonHelper.toJson(entry.getValue()));
            }
        }
        strb.append(key);
        return genSig(strb.toString());
    }

    public static String genSig(String str) {
        String sig;
        sig = DigestUtils.md5Hex(str);
//        try {
//            sig = DigestUtils.md5Hex(URLEncoder.encode(str, "utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("Broken VM does not support UTF-8");
//        }
        LOG.trace("gen sig for " + str + ", generated sig is " + sig);
        return sig;
    }
}
