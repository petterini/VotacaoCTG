package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

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
}
