package com.senac.NutriJar.controller;

import com.senac.NutriJar.model.Usuario;
import com.senac.NutriJar.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private final UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/lista-usuarios")
    public String listarUsuarios(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        
        // Verifica se o usuário está logado e é administrador
        if (usuarioLogado == null || usuarioLogado.getNivel() != Usuario.NivelUsuario.ADMINISTRADOR) {
            return "redirect:/login";
        }
        
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "lista-usuarios";
    }
    
@PostMapping("/usuarios/excluir/{id}")
@ResponseBody
public ResponseEntity<String> excluirUsuario(
        @PathVariable Long id,
        HttpSession session) {
    
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    
    if (usuarioLogado == null || usuarioLogado.getNivel() != Usuario.NivelUsuario.ADMINISTRADOR) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso não autorizado");
    }
    
    try {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.ok("Usuário excluído com sucesso");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("Erro ao excluir usuário: " + e.getMessage());
    }
}
}
