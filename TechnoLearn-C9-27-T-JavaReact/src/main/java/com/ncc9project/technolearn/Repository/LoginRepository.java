package com.ncc9project.technolearn.Repository;

import com.ncc9project.technolearn.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
