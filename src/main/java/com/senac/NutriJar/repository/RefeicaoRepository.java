package com.senac.NutriJar.repository;

import com.senac.NutriJar.model.Refeicao;
import com.senac.NutriJar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuario(Usuario usuario);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Refeicao r WHERE r.idRefeicao = ?1 AND r.usuario = ?2")
    void deleteByIdAndUsuario(Long id, Usuario usuario);
}