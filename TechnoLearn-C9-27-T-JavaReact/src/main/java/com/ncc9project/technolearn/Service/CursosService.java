package com.ncc9project.technolearn.Service;

import com.ncc9project.technolearn.DTO.CursosDTO;
import com.ncc9project.technolearn.DTO.ListCursosDTO;
import com.ncc9project.technolearn.Exceptions.GeneralException;
import com.ncc9project.technolearn.Model.Cursos;
import com.ncc9project.technolearn.Repository.CursosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CursosService {
    private final CursosRepository cursosRepository;

    @Autowired
    public CursosService(CursosRepository cursosRepository){
        this.cursosRepository = cursosRepository;
    }

    ModelMapper mapper = new ModelMapper();
    public ListCursosDTO getAllCursos() {
        List<Cursos> cursos = cursosRepository.findAll();
        Set<CursosDTO> list = new TreeSet<>(Comparator.comparing(CursosDTO::getId));

        cursos.stream().map(curso ->
                list.add(mapper.map(curso, CursosDTO.class)))
                .collect(Collectors.toSet());
        return new ListCursosDTO(list);
    }

    public CursosDTO getCursoById(long id) {
        Cursos curso = cursosRepository.findById(id).orElse(null);
        if (curso != null){
        return mapper.map(curso, CursosDTO.class);
        }else {
            throw new GeneralException("No existe el curso.", HttpStatus.BAD_REQUEST);
        }
    }

}
