package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminAccessController {

    private final ParticipanteService participanteService;
    private final UsuarioService usuarioService;

    @GetMapping("/cadastrar-participante")
    public String cadastroParticipante() {
        return "cadastrarParticipante";
    }

    @GetMapping("/admin-dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/cadastrar-usuario")
    public String cadastrarUsuario() {
        return "cadastrarUsuario";
    }

    @PostMapping("/cadastrar-usuario")
    public String cadastrarUsuario(Usuario usuario, Model model) {
        try {
            usuarioService.addUsuario(usuario);
            model.addAttribute("mensagem", "Usuario cadastrado com sucesso!");
        }catch (Exception e) {
            model.addAttribute("mensagem", "Usuário já cadastrado!");
        }
        return "cadastrarUsuario";
    }
}
