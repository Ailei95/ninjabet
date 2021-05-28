package io.ninjabet.core.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@AllArgsConstructor
@Configuration
public class NinjaBetMailSenderConfig {

    private final Environment environment;

    @Bean
    public JavaMailSender getJavaMailSender() {

        String host = environment.getProperty("spring.mail.host");
        String port = environment.getProperty("spring.mail.port");
        String username = environment.getProperty("spring.mail.username");
        String password = environment.getProperty("spring.mail.password");
        String protocol = environment.getProperty("spring.mail.protocol");
        String auth = environment.getProperty("spring.mail.properties.mail.smtp.auth");
        String enableSSL = environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable");
        String debug = environment.getProperty("spring.mail.properties.mail.debug");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        assert port != null;
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enableSSL);
        props.put("mail.debug", debug);

        return mailSender;
    }
}
