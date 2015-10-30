package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * https://spring.io/guides/gs/rest-service/
 * This code uses Spring 4’s new @RestController annotation, which marks the class as a controller where every method returns a domain object instead of a view. It’s shorthand for @Controller and @ResponseBody rolled together.
 *
 * When that the service is up, visit http://localhost:8080/greeting, where you see:
     {"id":1,"content":"Hello, World!"}
     Provide a name query string parameter with http://localhost:8080/greeting?name=User. Notice how the value of the content attribute changes from "Hello, World!" to "Hello User!":
     {"id":2,"content":"Hello, User!"}
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * The @RequestMapping annotation ensures that HTTP requests to /greeting are mapped to the greeting() method.
     * @RequestParam binds the value of the query string parameter name into the name parameter of the greeting() method. This query string parameter is not required; if it is absent in the request, the defaultValue of "World" is used.
     * Spring uses the Jackson JSON library to automatically marshal instances of type Greeting into JSON.
     * The Greeting object must be converted to JSON. Thanks to Spring’s HTTP message converter support, you don’t need to do this conversion manually. Because Jackson 2 is on the classpath, Spring’s MappingJackson2HttpMessageConverter is automatically chosen to convert the Greeting instance to JSON.
     *
     * @param name
     * @return
     */
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}
