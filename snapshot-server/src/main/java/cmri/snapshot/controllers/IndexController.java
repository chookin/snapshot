package cmri.snapshot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhuyin on 10/27/15.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");

        String msg = "Running IndexController.index() method";

        mav.addObject("msg", msg);
        return mav;
    }
}
