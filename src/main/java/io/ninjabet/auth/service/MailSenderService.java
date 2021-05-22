package io.ninjabet.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        mimeMessageHelper.setFrom("noreply@ninjabet.io");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("confirm you email address");
        mimeMessageHelper.setText(text, true);

        mailSender.send(mimeMessage);
    }
}
