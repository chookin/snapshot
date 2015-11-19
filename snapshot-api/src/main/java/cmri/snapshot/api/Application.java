package cmri.snapshot.api;

import cmri.utils.configuration.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.File;

/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html<br>
 * Create a deployable war file:<br>
 *    The first step in producing a deployable war file is to provide a SpringBootServletInitializer subclass and override its configure method. This makes use of Spring Framework’s Servlet 3.0 support and allows you to configure your application when it’s launched by the servlet container. Typically, you update your application’s main class to extend SpringBootServletInitializer:
 *
 */
@SpringBootApplication
public class Application{
    public static final String serverProtocol = ConfigManager.get("server.protocol");
    public static final String domain = ConfigManager.get("server.hostname");
    public static final int port = ConfigManager.getInt("server.port");
    public static final String baseUrl;
    static {
        baseUrl = serverProtocol + "://" + domain + ":" + port + "/";
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
    public static String getUrl(String path){
        return baseUrl + path;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
