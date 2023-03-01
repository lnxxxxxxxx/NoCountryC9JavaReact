package com.ncc9project.technolearn.DTO;

import com.ncc9project.technolearn.Model.Cursos;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UsuarioDTO {

    private long id;

    private String nombre;

    private String email;

    private String password;

    private int suscripto;

    private Set<Cursos> cursosUsuario = new HashSet<>();

    private Set<UserInfoDTO> userInfo = new HashSet<>();
}
