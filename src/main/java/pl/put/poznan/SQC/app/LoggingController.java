package pl.put.poznan.SQC.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Zajmuje się obsługą loggera z biblioteki log4j2
 */
public class LoggingController implements ApplicationRunner {
    /**
     * Przechowuje Logger wygenerowany przez LoggerFactory
     */
    private static final Logger logger =  LoggerFactory.getLogger(LoggingController.class);
    
    //@RequestMapping("/")
    //public String index() {
    /**
     * Informuje o działaniu loggera
     * Wywoływana przy podłączeniu przez API.
     */
    @Override
    public void run(ApplicationArguments args)  {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
    }
}
