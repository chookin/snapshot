package cmri.snapshot.api.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by zhuyin on 11/11/15.
 */
public class DebugInterceptor extends HandlerInterceptorAdapter {
     protected final org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());
//    protected static final Logger LOG = Logger.getLogger(DebugInterceptor.class);
    // 重写 preHandle()方法，在业务处理器处理请求之前对该请求进行拦截处理
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        StringBuilder strb = new StringBuilder()
                .append(request.getRequestURL())
                .append("\t")
                .append("request paras: ")
                .append("\n")
                ;
        for(Map.Entry<String, String[]> entry: request.getParameterMap().entrySet()){
            strb.append(entry.getKey())
                    .append(": ")
                    .append("[")
                    .append(StringUtils.join(entry.getValue(), ","))
                    .append("]")
                    .append("\n");
        }
        LOG.debug(strb.toString());
        return true;
    }

}
