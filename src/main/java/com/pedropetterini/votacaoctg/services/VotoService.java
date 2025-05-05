package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.*;
import com.pedropetterini.votacaoctg.exceptions.VotesBlockedException;
import com.pedropetterini.votacaoctg.exceptions.VotesExceededException;
import com.pedropetterini.votacaoctg.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VotoService {
    private final VotoRepository votoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ParticipanteRepository participanteRepository;
    private final RegistroRepository registroRepository;
    private final ConfigRepository configRepository;

    public Voto votar(Long mesa, UUID participante) {

        if (podeVotar(mesa)) {
            if (!isVotacaoLiberada()){
                throw new VotesBlockedException("A votação está bloqueada!");
            }
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

    public Boolean isVotacaoLiberada(){
        if(configRepository.existsById(1L)) {
            return true;
        }

        return false;
    }

    public void alternarVotacao() {
        if(configRepository.existsById(1L)) {
            configRepository.deleteById(1L);
            configRepository.save(new Config(0L));
        }else{
            configRepository.deleteById(0L);
            configRepository.save(new Config(1L));
        }
    }
}
