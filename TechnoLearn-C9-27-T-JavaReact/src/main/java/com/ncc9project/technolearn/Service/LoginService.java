package com.ncc9project.technolearn.Service;


import com.ncc9project.technolearn.DTO.UsuarioDTO;
import com.ncc9project.technolearn.Model.Usuario;
import com.ncc9project.technolearn.Repository.LoginRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    ModelMapper mapper = new ModelMapper();

    public UsuarioDTO findByUsuario(String email) {
        Usuario usuario = loginRepository.findByEmail(email);
        return mapper.map(usuario, UsuarioDTO.class);
    }
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    public boolean isValidPassword(String password, String hashedPassword) {
        return argon2.verify(hashedPassword, password);
    }
}