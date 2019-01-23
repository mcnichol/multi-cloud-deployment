package io.pivotal.demo.multideployment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

@Controller
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Value("${isEmbedded}")
    private Boolean isEmbeddedServer;

    @RequestMapping("/")
    public ModelAndView getHome() {
        ModelAndView mav = new ModelAndView("index");

        String envIaas = System.getenv("CF_IAAS");
        String envPasGuid = System.getenv("CF_INSTANCE_GUID");

        String propGlassfish = System.getProperty("glassfish.version");
        String propJetty = System.getProperty("jetty.git.hash");
        String propTomcat = System.getProperty("catalina.base");

        Properties properties = System.getProperties();

        properties.forEach((k, v) -> {
            log.info(String.format("Key: %s\n\tValue: %s", k, v));
        });

        log.info(String.format(
                "\n\tIAAS: %s" +
                        "\n\tCF_INSTANCE_GUID: %s" +
                        "\n\tGLASSFISH_VERSION: %s" +
                        "\n\tJETTY_GIT_HASH: %s" +
                        "\n\tCATALINA_BASE: %s", envIaas, envPasGuid, propGlassfish, propJetty, propTomcat));

        mav.addObject("isJetty", propJetty != null);
        mav.addObject("isGlassfish", propGlassfish != null);
        mav.addObject("isTomcat", (propTomcat != null && propGlassfish == null));
        mav.addObject("isEmbedded", isEmbeddedServer);
        mav.addObject("isPas", envPasGuid != null);
        mav.addObject("iaas", envIaas != null ? envIaas : "physical");

        return mav;
    }
}