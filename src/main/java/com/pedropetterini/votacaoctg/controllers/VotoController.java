package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Voto;
import com.pedropetterini.votacaoctg.services.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

            votos.forEach((categoria, participanteId) -> {
                System.out.println("Quem votou: " + mesa + " Participante: " + participanteId);

                Long mesaFormated = Long.parseLong(mesa);
                UUID participanteIdFormated = UUID.fromString(participanteId);

                votoService.votar(mesaFormated, participanteIdFormated);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "redirect:/votar";
    }
}
