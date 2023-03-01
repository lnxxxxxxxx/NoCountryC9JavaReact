package com.ncc9project.technolearn.DTO;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UsuarioCursosDTO {

    private long idUsuario;

    private long idCurso;

    private Set<Long> idCursos = new HashSet<>();

}
