package com.senac.NutriJar.controller;

import com.senac.NutriJar.model.Usuario;
import com.senac.NutriJar.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

@PostMapping("/cadastrar")
public String cadastrarUsuario(Usuario usuario, Model model) {
    try {
        usuarioService.criarUsuario(usuario);
        return "redirect:/login?cadastroSucesso";
    } catch (RuntimeException e) {
        model.addAttribute("erro", e.getMessage());
        return "cadastro";
    }
}
}