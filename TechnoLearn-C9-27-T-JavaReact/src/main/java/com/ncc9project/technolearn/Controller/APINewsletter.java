package com.ncc9project.technolearn.Controller;

import com.ncc9project.technolearn.DTO.MensajeDTO;
import com.ncc9project.technolearn.DTO.NewsletterDTO;
import com.ncc9project.technolearn.Service.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/newsletter")
public class APINewsletter {

    @Autowired NewsletterService newsletterService;

    @PostMapping
    public ResponseEntity<MensajeDTO> subscribeNewsletter(@RequestBody NewsletterDTO newsletterDTO){
        return ResponseEntity.ok(newsletterService.subscribeNewsletter(newsletterDTO));
    }
}
