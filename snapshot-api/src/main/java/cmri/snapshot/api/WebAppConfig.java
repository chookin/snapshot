package cmri.snapshot.api;

import cmri.snapshot.api.interceptor.DebugInterceptor;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.configuration.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class WebAppConfig extends WebMvcConfigurerAdapter {
    public static final String serverProtocol = ConfigManager.get("server.protocol");
    public static final String domain = ConfigManager.get("server.domain");
    public static final int port = ConfigManager.getInt("server.port");
    public static final String baseUrl;
    static {
        baseUrl = serverProtocol + "://" + domain + ":" + port + "/";
    }
    public static String getUrl(String path){
        return baseUrl + path;
    }

    @Autowired
    private SigInterceptor sigInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(ConfigManager.getBool("mode.dev")) {
            registry.addInterceptor(new DebugInterceptor());
        }
        registry.addInterceptor(sigInterceptor);
    }
    public static void main(String[] args) {
        SpringApplication.run(WebAppConfig.class, args);
    }
}
