package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.entities.Voto;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import com.pedropetterini.votacaoctg.services.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminAccessController {

    private final ParticipanteService participanteService;
    private final UsuarioService usuarioService;
    private final VotoService votoService;

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

    @GetMapping("/listar-usuarios")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.getAll();

        model.addAttribute("usuarios", usuarios);

        return "listarUsuarios";
    }

    @GetMapping("/excluir-usuario")
    public String excluirUsuario() {
        return "excluirUsuario";
    }

    @GetMapping("/excluir-participante")
    public String excluirParticipante(Model model) {
        List<Participante> participantes = participanteService.getAllParticipants();

        model.addAttribute("participantes", participantes);

        return "excluirParticipante";
    }

    @GetMapping("/verificar-votos")
    public String resultadoFinal(Model model) {
        List<Voto> votos = votoService.getAllVotos();

        Map<String, Map<Participante, Long>> contagem = votos.stream()
                .collect(Collectors.groupingBy(
                        voto -> voto.getParticipante().getCategoria(),
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                Voto::getParticipante,
                                Collectors.counting()
                        )
                ));
        model.addAttribute("contagemGeral", contagem);
        model.addAttribute("votacaoLiberada", votoService.isVotacaoLiberada());

        return "resultadoFinal";
    }

}
