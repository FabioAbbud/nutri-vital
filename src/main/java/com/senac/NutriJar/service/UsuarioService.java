package com.senac.NutriJar.service;

import com.senac.NutriJar.model.Usuario;
import com.senac.NutriJar.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    
public Usuario autenticar(String nome, String senha) {
    Usuario usuario = usuarioRepository.findByNome(nome);
    if (usuario != null && usuario.getSenha().equals(senha)) {
        return usuario;
    }
    return null;
}

    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.findByNome(usuario.getNome()) != null) {
            throw new RuntimeException("Nome de usuário já existe");
        }
        
        if (usuario.getPeso() != null && usuario.getAltura() != null && usuario.getAltura() > 0) {
            double imc = usuario.getPeso() / (usuario.getAltura() * usuario.getAltura());
            usuario.setImc(imc);
        }
        
        return usuarioRepository.save(usuario);
    }

@Transactional
public void excluirUsuario(Long id) throws Exception {
    try {
        // Verifica se o usuário existe antes de excluir
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
        throw new Exception("Não é possível excluir o usuário pois possui registros associados");
    }
}
}
