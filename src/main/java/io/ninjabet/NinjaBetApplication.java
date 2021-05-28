package io.ninjabet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/***********************************************************************************************************************
 *
 *  Application rule:
 *
 *      Entity:
 *          - Fields with many to one, have to be set with fetch lazy mode
 *          - Fields with many to many decoration has to minimized with one to many, creating intermediate entity
 *
 **********************************************************************************************************************/

@SpringBootApplication
public class NinjaBetApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(NinjaBetApplication.class, args);
    }
}
