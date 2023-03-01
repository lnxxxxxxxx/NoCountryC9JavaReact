package com.ncc9project.technolearn.Service;

import com.ncc9project.technolearn.DTO.EmailDTO;
import com.ncc9project.technolearn.DTO.MensajeDTO;
import com.ncc9project.technolearn.DTO.NewsletterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsletterService {

    private EmailSenderService emailSenderService;

    @Autowired public NewsletterService(EmailSenderService emailSenderService){
        this.emailSenderService = emailSenderService;
    }

    ModelMapper mapper = new ModelMapper();

    public MensajeDTO subscribeNewsletter(NewsletterDTO newsletterDTO){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject("Suscripcion al Newsletter de TechLearn");
        emailDTO.setBody(newsletterDTO.getNombre() + " Gracias por suscribirse a nuestro sistema de " +
                "Newsletter te estaremos enviando todas nuestras nuevas noticias " +
                "sobre el mundo digital.");
        emailDTO.setToEmail(newsletterDTO.getEmail());
        emailSenderService.sendEmail(emailDTO);
        return new MensajeDTO("Suscripción exitosa");
    }
}
