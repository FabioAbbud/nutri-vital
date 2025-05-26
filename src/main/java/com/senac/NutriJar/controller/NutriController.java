package com.senac.NutriJar.controller;


import org.springframework.web.bind.annotation.GetMapping;
import com.senac.NutriJar.model.Refeicao;
import com.senac.NutriJar.model.Usuario;
import com.senac.NutriJar.service.RefeicaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import com.senac.NutriJar.model.AtividadeFisica;
import com.senac.NutriJar.service.AtividadeFisicaService;
import java.util.List;



@Controller
public class NutriController {

    private final RefeicaoService refeicaoService;
     private final AtividadeFisicaService atividadeFisicaService;
    
     public NutriController(RefeicaoService refeicaoService, AtividadeFisicaService atividadeFisicaService) {
        this.refeicaoService = refeicaoService;
        this.atividadeFisicaService = atividadeFisicaService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

@GetMapping("/menu")
public String menu(HttpSession session, Model model) {
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    if (usuarioLogado == null) {
        return "redirect:/login";
    }
    
    // Verifica se é admin (redireciona)
    if (usuarioLogado.getNivel() == Usuario.NivelUsuario.ADMINISTRADOR) {
        return "redirect:/lista-usuarios";
    }
    
    // Adiciona flag se é premium
    model.addAttribute("ehPremium", usuarioLogado.getNivel() == Usuario.NivelUsuario.PREMIUM);
    return "menu";
}
    
@GetMapping("/listagem-atividades")
public String listagemAtividades(HttpSession session, Model model) {
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    if (usuarioLogado == null) {
        return "redirect:/login";
    }
    
    List<AtividadeFisica> atividades = atividadeFisicaService.listarAtividadesDoUsuario(usuarioLogado);
    model.addAttribute("atividades", atividades);
    return "listagem-atividades";
}
    
    
    
    @GetMapping("/listagem-refeicoes")
    public String listagemRefeicoes(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("refeicoes", refeicaoService.listarRefeicoesDoUsuario(usuarioLogado));
        return "listagem-refeicoes";
    }
    
@PostMapping("/atividades")
@ResponseBody
public String cadastrarAtividade(
        @RequestParam String tipo,
        @RequestParam Integer duracao,  // Alterado para Integer
        @RequestParam String intensidade,
        @RequestParam String dia,
        @RequestParam String hora,
        HttpSession session) {
    
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    if (usuarioLogado == null) {
        return "redirect:/login";
    }
    
    AtividadeFisica atividade = AtividadeFisica.builder()
            .tipo(tipo)
            .duracaoMinutos(duracao)  // Armazena diretamente os minutos
            .intensidade(intensidade)
            .dia(LocalDate.parse(dia))
            .hora(LocalTime.parse(hora))
            .usuario(usuarioLogado)
            .build();
    
    atividadeFisicaService.salvarAtividade(atividade);
    return "Atividade cadastrada com sucesso!";
}
    
    

    @PostMapping("/refeicoes")
    @ResponseBody
    public String cadastrarRefeicao(
            @RequestParam String alimento,
            @RequestParam String quantidade,
            @RequestParam Integer kcal,
            @RequestParam String data,
            @RequestParam String hora,
            HttpSession session) {
        
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        
        Refeicao refeicao = Refeicao.builder()
                .alimento(alimento)
                .quantidade(quantidade)
                .kcal(kcal)
                .data(LocalDate.parse(data))
                .hora(LocalTime.parse(hora))
                .usuario(usuarioLogado)
                .build();
        
        refeicaoService.salvarRefeicao(refeicao);
        return "Refeição cadastrada com sucesso!";
    }

    @DeleteMapping("/refeicoes/{id}")
    @ResponseBody
    public String excluirRefeicao(@PathVariable Long id, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        
        refeicaoService.excluirRefeicao(id, usuarioLogado);
        return "Refeição excluída com sucesso!";
    }
    
    
    

    @GetMapping("/consulta")
    public String consulta(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }
        return "consulta";
    }
    
        @DeleteMapping("/atividades/{id}")
    @ResponseBody
    public String excluirAtividade(@PathVariable Long id, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        
        atividadeFisicaService.excluirAtividade(id, usuarioLogado);
        return "Atividade excluída com sucesso!";
    }
}
