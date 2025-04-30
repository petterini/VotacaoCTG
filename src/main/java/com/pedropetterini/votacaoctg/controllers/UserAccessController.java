package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.entities.Voto;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import com.pedropetterini.votacaoctg.services.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class UserAccessController {

    private final ParticipanteService participanteService;
    private final UsuarioService usuarioService;
    private final VotoService votoService;

    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long mesa = Long.parseLong(authentication.getName());

        boolean jaVotou = !votoService.podeVotar(mesa);

        model.addAttribute("jaVotou", jaVotou);

        return "user-dashboard";
    }

    @GetMapping("/votar")
    public String listarParticipantes(Model model) {
        List<Participante> participantes = participanteService.getAllParticipants();

        List<String> ordemCategorias = List.of(
                "Peão", "Guri", "Piá", "Piazito", "Adulta", "Juvenil", "Mirim", "Pré-Mirim", "Dente de Leite", "Chinoquinha"
        );

        Map<String, List<Participante>> participantesMap = participantes.stream().collect(Collectors.groupingBy(Participante::getCategoria));

        Map<String, List<Participante>> participantesOrd = new LinkedHashMap<>();
        for (String categoria : ordemCategorias) {
            if (participantesMap.containsKey(categoria)) {
                participantesOrd.put(categoria, participantesMap.get(categoria));
            }
        }

        model.addAttribute("participantes", participantesOrd);

        return "votar";
    }

    @GetMapping("/acompanhar")
    public String meusVotos(Model model, Principal principal) {
        Usuario usuario = usuarioService.getByMesa(Long.parseLong(principal.getName()));
        List<Voto> votos = votoService.getByUsuario(usuario);

        List<String> ordemCategorias = List.of(
                "Peão", "Guri", "Piá", "Piazito", "Adulta", "Juvenil",
                "Mirim", "Pré-Mirim", "Dente de Leite", "Chinoquinha"
        );

        Map<String, Map<Participante, Long>> votosAgrupados = votos.stream()
                .collect(Collectors.groupingBy(
                        voto -> voto.getParticipante().getCategoria(),
                        Collectors.groupingBy(
                                Voto::getParticipante,
                                Collectors.counting()
                        )
                ));

        Map<String, Map<Participante, Long>> votosOrdenados = new LinkedHashMap<>();

        for (String categoria : ordemCategorias) {
            if (votosAgrupados.containsKey(categoria)) {
                votosOrdenados.put(categoria, votosAgrupados.get(categoria));
            }
        }

        votosAgrupados.keySet().stream()
                .filter(categoria -> !ordemCategorias.contains(categoria))
                .forEach(categoria -> votosOrdenados.put(categoria, votosAgrupados.get(categoria)));

        model.addAttribute("votosAgrupados", votosOrdenados);
        return "meusVotos";
    }
}
