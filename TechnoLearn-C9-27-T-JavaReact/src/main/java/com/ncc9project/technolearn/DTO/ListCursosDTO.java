package com.ncc9project.technolearn.DTO;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ListCursosDTO {
    private Set<CursosDTO> cursos;
}
