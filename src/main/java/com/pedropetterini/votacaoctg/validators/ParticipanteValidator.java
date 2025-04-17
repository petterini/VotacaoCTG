package com.pedropetterini.votacaoctg.validators;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.exceptions.DuplicateParticipantException;
import com.pedropetterini.votacaoctg.repositories.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipanteValidator {
    private final ParticipanteRepository participanteRepository;

    public void validate(Participante participante) {
        if(participanteRepository.existsByNome(participante.getNome()) && participante.getId() == null) {
            throw new DuplicateParticipantException("Participante já cadastrado.");
        }

        if(participante.getId() != null) {
            Participante p1 = participanteRepository.getByNome(participante.getNome());
            if(!participante.getId().equals(p1.getId())) {
                throw new DuplicateParticipantException("Participante já cadastrado.");
            }
        }
    }

}
