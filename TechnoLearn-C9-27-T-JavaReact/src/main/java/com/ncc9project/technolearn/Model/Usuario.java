package com.ncc9project.technolearn.Model;

import com.ncc9project.technolearn.DTO.UserInfoDTO;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "usuario")
public class Usuario {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario")
        private long id;

        @Column(name = "nombre_completo")
        private String nombre;

        @Column(name = "email")
        private String email;

        @Column(name = "contraseña")
        private String password;

        @Column(name = "suscripto", columnDefinition ="integer default 0")
        private int suscripto;

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name = "usuario_cursos",
                joinColumns = @JoinColumn(name = "id_usuario"),
                inverseJoinColumns = @JoinColumn(name = "id_curso"))
        private Set<Cursos> cursosUsuario = new HashSet<>();

        @Type(JsonType.class)
        @Column(name = "userInfo", columnDefinition = "json")
        private Set<UserInfoDTO> userInfo = new HashSet<>();

}
