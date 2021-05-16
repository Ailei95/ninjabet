package io.ninjabet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
