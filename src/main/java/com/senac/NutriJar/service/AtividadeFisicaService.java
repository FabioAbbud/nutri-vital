package com.senac.NutriJar.service;

import com.senac.NutriJar.model.AtividadeFisica;
import com.senac.NutriJar.model.Usuario;
import com.senac.NutriJar.repository.AtividadeFisicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AtividadeFisicaService {
    private final AtividadeFisicaRepository atividadeFisicaRepository;

    public AtividadeFisicaService(AtividadeFisicaRepository atividadeFisicaRepository) {
        this.atividadeFisicaRepository = atividadeFisicaRepository;
    }

    public List<AtividadeFisica> listarAtividadesDoUsuario(Usuario usuario) {
        return atividadeFisicaRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
    }

    public AtividadeFisica salvarAtividade(AtividadeFisica atividade) {
        return atividadeFisicaRepository.save(atividade);
    }

    @Transactional
    public void excluirAtividade(Long id, Usuario usuario) {
        atividadeFisicaRepository.deleteByIdAndUsuario_IdUsuario(id, usuario.getIdUsuario());
    }
}