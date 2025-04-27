package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/cadastrarParticipante")
    public String cadastrarParticipante(Participante participante, Model model) {
        try {
            participanteService.addParticipante(participante);
            model.addAttribute("mensagem", "Participante cadastrado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("mensagem", "Esse participante já foi cadastrado!");
        }
        return "cadastrarParticipante";
    }

    @GetMapping("/listar-participantes")
    public String ListarParticipantes(Model model) {
        List<Participante> participantes = participanteService.getAllParticipants();

        List<String> ordemCategorias = List.of(
                "Peão", "Guri", "Piá", "Piazito", "Adulta", "Juvenil", "Mirim", "Dente de Leite", "Chinoquinha"
        );

        Map<String, List<Participante>> participantesMap = participantes.stream().collect(Collectors.groupingBy(Participante::getCategoria));

        Map<String, List<Participante>> participantesOrd = new LinkedHashMap<>();
        for(String categoria : ordemCategorias) {
            if(participantesMap.containsKey(categoria)) {
                participantesOrd.put(categoria, participantesMap.get(categoria));
            }
        }

        model.addAttribute("participantes", participantesOrd);

        return "listarParticipantes";
    }

    @PostMapping("/editarParticipante")
    public String editarParticipante(@RequestParam UUID id, Participante participante, Model model) {
        try {
            participanteService.updateParticipante(id, participante);
            model.addAttribute("mensagem", "Participante editado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mensagem", "Erro ao editar participante!");
        }

        List<Participante> participantes = participanteService.getAllParticipants();
        model.addAttribute("participantes", participantes);

        return "editarParticipante";
    }

    @PostMapping("excluirParticipante")
    public String excluirParticipante(@RequestParam UUID id, Model model) {

        List<Participante> participantes = participanteService.getAllParticipants();

        model.addAttribute("participantes", participantes);

        return "excluirParticipante";
    }
}
