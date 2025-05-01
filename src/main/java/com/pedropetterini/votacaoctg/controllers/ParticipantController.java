package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.DuplicateParticipantException;
import com.pedropetterini.votacaoctg.exceptions.ParticipantNotFoundException;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipanteService participanteService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/cadastrarParticipante")
    public String cadastrarParticipante(Participante participante, Model model) {
        try {
            participanteService.addParticipante(participante);
            model.addAttribute("mensagem", "Participante cadastrado com sucesso!");
        } catch (DuplicateParticipantException ex) {
            model.addAttribute("mensagem", ex.getMessage());
        }

        return "cadastrarParticipante";
    }

    @GetMapping("/listar-participantes")
    public String ListarParticipantes(Model model) {
        List<Participante> participantes = participanteService.getAllParticipants();

        List<String> ordemCategorias = List.of(
                "Chinoquinha", "Dente de Leite", "Pré-Mirim", "Mirim", "Juvenil", "Adulta", "Piazito", "Piá", "Guri", "Peão"
        );

        Map<String, List<Participante>> participantesMap = participantes.stream().collect(Collectors.groupingBy(Participante::getCategoria));

        Map<String, List<Participante>> participantesOrd = new LinkedHashMap<>();
        for (String categoria : ordemCategorias) {
            if (participantesMap.containsKey(categoria)) {
                participantesOrd.put(categoria, participantesMap.get(categoria));
            }
        }

        model.addAttribute("participantes", participantesOrd);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER")
                .replace("ROLE_", "");
        model.addAttribute("role", role);

        return "listarParticipantes";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/editarParticipante")
    public String editarParticipante(@RequestParam UUID id, Participante participante, Model model) {
        try {
            participanteService.updateParticipante(id, participante);
            model.addAttribute("mensagem", "Participante editado com sucesso!");
        } catch (ParticipantNotFoundException | DuplicateParticipantException e) {
            model.addAttribute("mensagem", e.getMessage());
        }

        List<Participante> participantes = participanteService.getAllParticipants();
        model.addAttribute("participantes", participantes);

        return "editarParticipante";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("excluirParticipante")
    public String excluirParticipante(@RequestParam UUID id, Model model) {
        try {
            participanteService.deleteParticipante(id);
        } catch (ParticipantNotFoundException e) {
            model.addAttribute("mensagem", e.getMessage());
        }

        return "redirect:/excluir-participante";
    }
}
