package com.ncc9project.technolearn.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cursos")
public class
Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private long id;

   @Column(name = "id_video")
    private String id_video;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    @Column(name = "descripcion_curso")
    private String descripcionCurso;

    @Column(name = "miniatura_curso")
    private String miniaturaCurso;

    @Type(JsonType.class)
    @Column(name = "urls", columnDefinition = "json")
    private ArrayList<Object> urls;

    @Column(name = "precio")
    private float precio;

    @JsonIgnore
    @ManyToMany(mappedBy = "cursosUsuario")
    private Set<Usuario> usuarioSet = new HashSet<>();

    @Column(name = "acceso")
    private String acceso;

    @Column(name = "instructor")
    private String instructor;
}