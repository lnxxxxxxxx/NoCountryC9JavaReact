package com.ncc9project.technolearn.Service;

import com.ncc9project.technolearn.DTO.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailDTO emailDTO){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cejasalandavid@gmail.com");
        message.setTo(emailDTO.getToEmail());
        message.setText(emailDTO.getBody());
        message.setSubject(emailDTO.getSubject());

        mailSender.send(message);
    }

}
