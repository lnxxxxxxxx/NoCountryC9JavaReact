package com.ncc9project.technolearn.Controller;

import com.ncc9project.technolearn.DTO.MensajeDTO;
import com.ncc9project.technolearn.DTO.UsuarioDTO;
import com.ncc9project.technolearn.Model.Usuario;
import com.ncc9project.technolearn.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/login")
public class APILogin {
    @Autowired LoginService loginService;

    public APILogin(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping
    public ResponseEntity login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");


        if (email == null || password == null) {
            return new ResponseEntity(new MensajeDTO("Correo electrónico y contraseña son requeridos")
                    ,HttpStatus.BAD_REQUEST);
        }

        UsuarioDTO user = loginService.findByUsuario(email);
        if (user == null) {
            return new ResponseEntity(new MensajeDTO("Correo electrónico o contraseña inválidos")

            ,HttpStatus.UNAUTHORIZED);
        }
        if (loginService.isValidPassword(password, user.getPassword())) {
            return new ResponseEntity(loginService.findByUsuario(email)
                    ,HttpStatus.OK);
        } else {
            return new ResponseEntity(new MensajeDTO("Correo electrónico o contraseña inválidos")
            ,HttpStatus.UNAUTHORIZED);
        }
    }
}