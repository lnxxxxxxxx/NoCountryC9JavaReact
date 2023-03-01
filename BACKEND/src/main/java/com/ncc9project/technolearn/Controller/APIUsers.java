package com.ncc9project.technolearn.Controller;

import com.ncc9project.technolearn.DTO.*;
import com.ncc9project.technolearn.Model.Usuario;
import com.ncc9project.technolearn.Service.EmailSenderService;
import com.ncc9project.technolearn.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api")
public class APIUsers {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/todos")
    public ResponseEntity<ListUsuarioDTO> getAllUser() {
        return ResponseEntity.ok(usuarioService.getAlluser());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioDTO> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.ok(usuarioService.getUserById(id));
    }

    @PostMapping("/registro")
    public ResponseEntity<String> saveUser(@RequestBody UsuarioDTO usuarioDTO){
        UsuarioDTO nuevoUsuario = usuarioService.saveUser(usuarioDTO);
        if (nuevoUsuario != null) {
            return ResponseEntity.ok("Registro exitoso");
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/agregarCurso")
    public ResponseEntity<UsuarioCursosDTO> agregarCurso(@RequestBody UsuarioCursosDTO usuarioCursoDTO){
        return ResponseEntity.ok(usuarioService.agregarCurso(usuarioCursoDTO));
    }

    @PutMapping("/agregarProgreso/{userId}")
    public ResponseEntity<ListUserInfoDTO> agregarProgreso(@RequestBody UserInfoDTO userInfoDTO,
                                                           @PathVariable("userId")Long userId){
        return ResponseEntity.ok(usuarioService.agregarProgreso(userInfoDTO, userId));
    }

    @PostMapping("/comprarSuscripcion/{userId}")
    public ResponseEntity<MensajeDTO> comprarSuscripcion(@RequestBody TarjetaDTO tarjetaDTO,
                                                         @PathVariable("userId")Long userId){
        return ResponseEntity.ok(usuarioService.comprarSuscripcion(tarjetaDTO, userId));
    }
}
