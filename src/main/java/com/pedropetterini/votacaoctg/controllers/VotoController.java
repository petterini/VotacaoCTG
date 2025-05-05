package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.exceptions.VotesBlockedException;
import com.pedropetterini.votacaoctg.exceptions.VotesExceededException;
import com.pedropetterini.votacaoctg.services.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping("/votar")
    public String votar(@RequestParam Map<String, String> votos) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String mesa = authentication.getName();
            Long mesaFormated = Long.parseLong(mesa);

            votos.forEach((categoria, participanteId) -> {
                UUID participanteIdFormated = UUID.fromString(participanteId);
                votoService.votar(mesaFormated, participanteIdFormated);
            });

            votoService.contaVotos(mesaFormated);


        } catch (VotesExceededException | VotesBlockedException ex) {
            System.out.println(ex.getMessage());
        }
        return "redirect:/user-dashboard";
    }

    @PostMapping("/excluir-todos-votos")
    public String excluirTodosVotos(RedirectAttributes redirectAttributes){
        votoService.excluirTodos();
        return "redirect:/verificar-votos";
    }

    @PostMapping("/alternar-votacao")
    public String alternarVotacao() {
        votoService.alternarVotacao();
        return "redirect:/verificar-votos";
    }

}
