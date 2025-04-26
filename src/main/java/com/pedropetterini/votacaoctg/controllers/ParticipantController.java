package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipanteService participanteService;

    @PostMapping("/cadastrarParticipante")
    public String cadastrarParticipante(Participante participante, Model model) {
        try {
            participanteService.addParticipante(participante);
            model.addAttribute("mensagem", "Participante cadastrado com sucesso!");
        }catch (Exception e) {
            model.addAttribute("mensagem", "Esse participante já foi cadastrado!");
        }
        return "cadastrarParticipante";
    }

    @PostMapping("/editarParticipante")
    public String editarParticipante(@RequestParam UUID id, Participante participante, Model model) {
        try{
            participanteService.updateParticipante(id, participante);
            model.addAttribute("mensagem", "Participante editado com sucesso!");
        }catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mensagem", "Erro ao editar participante!");
        }

        List<Participante> participantes = participanteService.getAllParticipants();
        model.addAttribute("participantes", participantes);

        return "editarParticipante";
    }
}
