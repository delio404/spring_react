package com.delios.minhas_financas.repository;

import com.delios.minhas_financas.model.entity.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
     boolean existsByEmail(String email);


     Optional<Usuario> findByEmail(String email);
}
