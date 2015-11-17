package cmri.snapshot.api;

import cmri.snapshot.api.interceptor.DebugInterceptor;
import cmri.snapshot.api.interceptor.SigInterceptor;
import cmri.utils.configuration.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.io.File;

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

    @Bean
    public ChuServerProperties chuServerProperties(){
        return new ChuServerProperties();
    }

    @ConfigurationProperties(prefix = "chu.server")
    public static class ChuServerProperties
            implements EmbeddedServletContainerCustomizer
    {
        protected final Logger LOG = LoggerFactory.getLogger(getClass());
        private String documentRoot;
        public String getDocumentRoot(){
            return documentRoot;
        }
        public void setDocumentRoot(String documentRoot){
            LOG.info("set document root: "+documentRoot);
            this.documentRoot = documentRoot;
        }

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container){
            if (getDocumentRoot() != null){
                container.setDocumentRoot(new File(getDocumentRoot()));
            }
        }
    }
}
