package io.ninjabet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/***********************************************************************************************************************
 *
 *  Application rule:
 *
 *      Entity:
 *          - Fields with many to one, is write only and fetch lazy mode, to update field only with minimized id
 *          - Fields with one to many, is read only mode
 *          - Object property that is referenced to a table before serialized has to minimized with
 *            referenced object id field, minimized field must decorated with @Transient and public
 *          - Fields with many to many decoration has to minimized with one to many, creating intermediate entity
 *
 **********************************************************************************************************************/

@SpringBootApplication
public class NinjaBetApplication {

    public static void main(String[] args) {
        SpringApplication.run(NinjaBetApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
