package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.entities.Voto;
import com.pedropetterini.votacaoctg.repositories.ParticipanteRepository;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import com.pedropetterini.votacaoctg.repositories.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VotoService {
    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipanteRepository participanteRepository;

    public Voto votar(Long mesa, UUID participante){
        Participante p1 = participanteRepository.findById(participante).orElse(null);
        Usuario u1 = usuarioRepository.findByMesa(mesa);

        Voto voto = new Voto(u1, p1);

        return votoRepository.save(voto);
    }

    public List<Voto> getByUsuario(Usuario usuario) {
        return votoRepository.findByUsuario(usuario);
    }

    public List<Voto> getAllVotos() {
        return votoRepository.findAll();
    }
}
