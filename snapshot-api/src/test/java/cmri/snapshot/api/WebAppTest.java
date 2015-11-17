package cmri.snapshot.api;

import cmri.snapshot.api.helper.RestHandler;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhuyin on 11/5/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebIntegrationTest(randomPort = true)
public abstract class WebAppTest {
    protected RestHandler rest = new RestHandler();
// open inner web server
//    private String base = "http://localhost:";
//    @Value("${local.server.port}")
//    private int port;

    public WebAppTest(){
    }

    protected void log(Object o){
        System.out.println(o);
    }
}
