package com.senac.NutriJar.controller;

import com.senac.NutriJar.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.senac.NutriJar.model.Usuario;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String mostrarLogin(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("erro", "Credenciais inválidas");
        }
        return "index";
    }

@PostMapping
public String processarLogin(@RequestParam String nome, 
                           @RequestParam String senha,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
    Usuario usuario = usuarioService.autenticar(nome, senha);
    if (usuario != null) {
        session.setAttribute("usuarioLogado", usuario);
        
        // Redireciona administradores para lista de usuários
        if (usuario.getNivel() == Usuario.NivelUsuario.ADMINISTRADOR) {
            return "redirect:/lista-usuarios";
        }
        return "redirect:/menu";
    }
    
    redirectAttributes.addAttribute("error", true);
    return "redirect:/login";
}
}