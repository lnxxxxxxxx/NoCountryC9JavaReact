package com.ncc9project.technolearn.Controller;

import com.ncc9project.technolearn.DTO.CursosDTO;
import com.ncc9project.technolearn.DTO.ListCursosDTO;
import com.ncc9project.technolearn.Exceptions.GeneralException;
import com.ncc9project.technolearn.Service.CursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cursos")
public class APICursos {
    @Autowired CursosService cursosService;

    @GetMapping("/list")
    public ResponseEntity<ListCursosDTO> getAllCursos() {
        return ResponseEntity.ok(cursosService.getAllCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursosDTO> getCursoById(@PathVariable("id") Long id) {
        return  ResponseEntity.ok(cursosService.getCursoById(id));
    }
}
