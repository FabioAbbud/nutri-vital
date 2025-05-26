package com.senac.NutriJar.service;

import com.senac.NutriJar.model.Refeicao;
import com.senac.NutriJar.model.Usuario;
import com.senac.NutriJar.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RefeicaoService {
    private final RefeicaoRepository refeicaoRepository;

    public RefeicaoService(RefeicaoRepository refeicaoRepository) {
        this.refeicaoRepository = refeicaoRepository;
    }

    public List<Refeicao> listarRefeicoesDoUsuario(Usuario usuario) {
        return refeicaoRepository.findByUsuario(usuario);
    }

    public Refeicao salvarRefeicao(Refeicao refeicao) {
        return refeicaoRepository.save(refeicao);
    }

    @Transactional
    public void excluirRefeicao(Long id, Usuario usuario) {
        refeicaoRepository.deleteByIdAndUsuario(id, usuario);
    }
}