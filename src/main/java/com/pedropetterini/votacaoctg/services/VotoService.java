package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.entities.RegistrarVotos;
import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.entities.Voto;
import com.pedropetterini.votacaoctg.exceptions.VotesExceededException;
import com.pedropetterini.votacaoctg.repositories.ParticipanteRepository;
import com.pedropetterini.votacaoctg.repositories.RegistroRepository;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import com.pedropetterini.votacaoctg.repositories.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VotoService {
    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipanteRepository participanteRepository;
    private final RegistroRepository registroRepository;

    public Voto votar(Long mesa, UUID participante) {

        if (podeVotar(mesa)) {
            Participante p1 = participanteRepository.findById(participante).orElse(null);
            Usuario u1 = usuarioRepository.findByMesa(mesa);

            Voto voto = new Voto(u1, p1);

            return votoRepository.save(voto);
        }else {
            throw new VotesExceededException("Limite de votos excedidos!");
        }
    }

    public List<Voto> getByUsuario(Usuario usuario) {
        return votoRepository.findByUsuario(usuario);
    }

    public List<Voto> getAllVotos() {
        return votoRepository.findAll();
    }

    public void excluirTodos() {
        votoRepository.deleteAll();
        registroRepository.deleteAll();
    }

    public boolean podeVotar(Long mesa) {
        var usr = usuarioRepository.findByMesa(mesa);
        int votos;

        if(registroRepository.findByUsuario(usr).isPresent()) {
            votos = registroRepository.getByUsuario(usr).getVotos();
            return votos < 4;
        }

        return true;
    }

    @Transactional
    public int contaVotos(Long mesa) {
        var usr = usuarioRepository.findByMesa(mesa);
        RegistrarVotos votos;

        if(registroRepository.findByUsuario(usr).isPresent()) {
            votos = registroRepository.getByUsuario(usr);
            votos.setVotos(votos.getVotos() + 1);
        } else {
            votos = new RegistrarVotos();
            votos.setVotos(1);
            votos.setUsuario(usr);
        }

        registroRepository.save(votos);
        return votos.getVotos();
    }
}
