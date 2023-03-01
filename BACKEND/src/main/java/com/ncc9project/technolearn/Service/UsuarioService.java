package com.ncc9project.technolearn.Service;

import com.ncc9project.technolearn.DTO.*;
import com.ncc9project.technolearn.Exceptions.GeneralException;
import com.ncc9project.technolearn.Model.Cursos;
import com.ncc9project.technolearn.Model.Usuario;
import com.ncc9project.technolearn.Repository.CursosRepository;
import com.ncc9project.technolearn.Repository.UsuarioRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CursosRepository cursosRepository;

    private final EmailSenderService emailSenderService;

    @Autowired public UsuarioService(UsuarioRepository usuarioRepository,
                                     CursosRepository cursosRepository,
                                     EmailSenderService emailSenderService){
        this.usuarioRepository = usuarioRepository;
        this.cursosRepository = cursosRepository;
        this.emailSenderService = emailSenderService;
    }


    ModelMapper mapper = new ModelMapper();

    public ListUsuarioDTO getAlluser() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        Set<UsuarioDTO> list = new TreeSet<>(Comparator.comparing(UsuarioDTO::getId));

        usuarios.stream().map(usuario ->
                list.add(mapper.map(usuario, UsuarioDTO.class)))
                .collect(Collectors.toSet());

        return new ListUsuarioDTO(list);
    }

    public UsuarioDTO getUserById(long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario != null){
        return mapper.map(usuario, UsuarioDTO.class);
        }
        else {
            throw new GeneralException("No existe el usuario.", HttpStatus.BAD_REQUEST);
        }
    }

    public UsuarioDTO saveUser(UsuarioDTO usuarioDTO){
        Usuario usuario = mapper.map(usuarioDTO, Usuario.class);
        Optional<Usuario> usuarioExist = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExist.isEmpty()){
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
            usuario.setPassword(hash);
            usuarioRepository.save(usuario);
            return mapper.map(usuario, UsuarioDTO.class);
        } else {
            throw new GeneralException("El email ya esta registrado", HttpStatus.BAD_REQUEST);
        }
    }

    public UsuarioCursosDTO agregarCurso(UsuarioCursosDTO usuarioCursoDTO){
        Set<Long> usuarioCursos = new HashSet<>();
        Set<Cursos> cursosSet = null;
        Usuario usuario = usuarioRepository.findById(usuarioCursoDTO.getIdUsuario()).get();
        Cursos curso = cursosRepository.findById(usuarioCursoDTO.getIdCurso()).get();
        cursosSet = usuario.getCursosUsuario();
        cursosSet.add(curso);
        usuario.setCursosUsuario(cursosSet);
        usuarioRepository.save(usuario);
        for (Cursos cursito : cursosSet){
            usuarioCursos.add(cursito.getId());
        }
        usuarioCursoDTO.setIdCursos(usuarioCursos);
        return mapper.map(usuarioCursoDTO, UsuarioCursosDTO.class);
    }

    public ListUserInfoDTO agregarProgreso(UserInfoDTO userInfoDTO, long userId) {
        Set<Cursos> cursosSet = null;
        Set<UserInfoDTO> userInfoDTOS = new HashSet<>();
        UserInfoDTO userInfoDto = new UserInfoDTO();
        Usuario usuario = usuarioRepository.findById(userId).get();
        cursosSet = usuario.getCursosUsuario();
        userInfoDTOS = usuario.getUserInfo();
        for(Cursos curso : cursosSet){
            if(curso.getId() == userInfoDTO.getIdCurso()){
                for(UserInfoDTO userinfo : userInfoDTOS){
                    if(userinfo.getIdCurso() == userInfoDTO.getIdCurso()){
                        userinfo.setProgreso(userInfoDTO.getProgreso());
                        break;
                    }
                }
                userInfoDto.setProgreso(userInfoDTO.getProgreso());
                userInfoDto.setIdCurso(userInfoDTO.getIdCurso());
                userInfoDTOS.add(userInfoDto);
                break;
            }
        }
        usuario.setUserInfo(userInfoDTOS);
        usuarioRepository.save(usuario);

        Set<UserInfoDTO> userInfo = usuario.getUserInfo();
        Set<UserInfoDTO> userInfoSet = new TreeSet<>(Comparator.comparing(UserInfoDTO::getIdCurso));
        userInfo.stream().map(userinfo ->
                        userInfoSet.add(mapper.map(userinfo, UserInfoDTO.class)))
                .collect(Collectors.toSet());

        return new ListUserInfoDTO(userInfoSet);
    }

    public MensajeDTO comprarSuscripcion(TarjetaDTO tarjetaDTO, Long userId){
        if(
                validarTarjeta(tarjetaDTO.getNumeroTarjeta()) &&
                validarVencimiento(tarjetaDTO.getFechaExpiracion())
        ){
            Usuario usuario = usuarioRepository.findById(userId).get();
            String nombre = usuario.getNombre();
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setSubject("Compra de suscripcion anual TechnoLearn");
            emailDTO.setBody(nombre + " gracias por elegirnos como tu plataforma educativa para " +
                    "crecer profesionalmente espero que disfrutes los cursos.");
            emailDTO.setToEmail(usuario.getEmail());
            emailSenderService.sendEmail(emailDTO);
            usuario.setSuscripto(1);
            usuarioRepository.save(usuario);
            return new MensajeDTO("Gracias por tu compra");
        } else {
            throw new GeneralException("Tarjeta invalida, compruebe los datos ingresados " +
                    "e intentelo nuevamente"
                    , HttpStatus.UNAUTHORIZED);
        }
    }

    private static boolean validarTarjeta(String numeroTarjeta){
        int[] tarjetaInt = new int[numeroTarjeta.length()];

        for (int i = 0; i < numeroTarjeta.length(); i++){
            tarjetaInt[i] = Integer.parseInt(numeroTarjeta.substring(i, i + 1));
        }

        for (int i = tarjetaInt.length - 2; i >= 0; i = i - 2){
            int tempValor = tarjetaInt[i];
            tempValor = tempValor * 2;
            if(tempValor > 9){
                tempValor = tempValor % 10 + 1;
            }
            tarjetaInt[i] = tempValor;
        }

        int total = 0;
        for (int i =  0; i < tarjetaInt.length; i++){
            total += tarjetaInt[i];
        }
        if (total % 10 == 0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarVencimiento(String fechaVencimiento) {
        if (!fechaVencimiento.matches("\\d{2}/\\d{2}")) {

            return false;
        }

        String[] partes = fechaVencimiento.split("/");
        int meses = Integer.parseInt(partes[0]);
        int anio = Integer.parseInt(partes[1]) + 2000;
        LocalDate vencimiento = LocalDate.of(anio, meses, 1).plusMonths(1).minusDays(1);

        return vencimiento.isAfter(LocalDate.now());
    }
}