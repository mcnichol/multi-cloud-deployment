package io.pivotal.demo.multideployment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView getHome() {
        ModelAndView mav = new ModelAndView("index");

        String hasGlassfishProp = System.getProperty("glassfish.version");
        String hasJettyProp = System.getProperty("jetty.git.hash");
        String hasTomcatProp = System.getProperty("catalina.base");

        Properties properties = System.getProperties();

        properties.forEach((k, v) -> {
            System.out.println(String.format("Key: %s\n\tValue: %s", k, v));
        });

        mav.addObject("isJetty", hasJettyProp != null);
        mav.addObject("isGlassfish", hasGlassfishProp != null);
        mav.addObject("isTomcat", (hasTomcatProp != null && hasGlassfishProp == null));

        return mav;
    }
}
