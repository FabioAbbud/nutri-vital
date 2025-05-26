package com.senac.NutriJar.repository;

import com.senac.NutriJar.model.AtividadeFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface AtividadeFisicaRepository extends JpaRepository<AtividadeFisica, Long> {
    List<AtividadeFisica> findByUsuario_IdUsuario(Long idUsuario);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM AtividadeFisica a WHERE a.idAtividade = ?1 AND a.usuario.idUsuario = ?2")
    void deleteByIdAndUsuario_IdUsuario(Long id, Long idUsuario);
}