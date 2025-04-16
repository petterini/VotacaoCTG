package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.repositories.ParticipanteRepository;
import com.pedropetterini.votacaoctg.validators.ParticipanteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipanteService {
    private final ParticipanteRepository repository;
    private final ParticipanteValidator participanteValidator;

    public Participante addParticipante(Participante participante) {
        participanteValidator.validate(participante);
        return repository.save(participante);
    }
}
