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

import java.util.List;

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

    @GetMapping("/editar-usuario")
    public String editarUsuario() {
        return "editarUsuario";
    }

    @GetMapping("/editar-participante")
    public String mostrarEditarParticipante(Model model) {
        List<Participante> participantes = participanteService.getAllParticipants();
        model.addAttribute("participantes", participantes);
        return "editarParticipante";
    }

}
