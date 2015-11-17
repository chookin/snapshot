package cmri.snapshot.api;

import cmri.snapshot.api.interceptor.DebugInterceptor;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.configuration.ConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhuyin on 11/16/15.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private SigInterceptor sigInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(ConfigManager.getBool("mode.dev")) {
            registry.addInterceptor(new DebugInterceptor());
        }
        registry.addInterceptor(sigInterceptor);
    }
}
