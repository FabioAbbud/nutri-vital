package com.senac.NutriJar.repository;

import com.senac.NutriJar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
}