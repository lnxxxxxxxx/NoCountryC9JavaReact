package com.ncc9project.technolearn;

import com.ncc9project.technolearn.Model.Usuario;
import com.ncc9project.technolearn.Repository.CursosRepository;
import com.ncc9project.technolearn.Repository.UsuarioRepository;
import com.ncc9project.technolearn.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class TechnoLearnApplication {

	public static void main(String[] args) {SpringApplication.run(TechnoLearnApplication.class, args);
	}

}
