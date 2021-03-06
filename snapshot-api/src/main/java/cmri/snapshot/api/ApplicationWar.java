package cmri.snapshot.api;

import cmri.utils.configuration.ConfigManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html<br>
 * Create a deployable war file:<br>
 *    The first step in producing a deployable war file is to provide a SpringBootServletInitializer subclass and override its configure method. This makes use of Spring Framework’s Servlet 3.0 support and allows you to configure your application when it’s launched by the servlet container. Typically, you update your application’s main class to extend SpringBootServletInitializer:
 *
 */
@SpringBootApplication
public class ApplicationWar extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(ApplicationWar.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationWar.class, args);
    }
}