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
        if (participanteRepository.existsByNome(participante.getNome()) && participante.getId() == null) {
            throw new DuplicateParticipantException("Participante já cadastrado.");
        }

        if (participante.getId() != null && participanteRepository.existsByNome(participante.getNome())) {
            Participante p1 = participanteRepository.findByNome(participante.getNome()).get();
            if(p1.getId() != participante.getId()){
                throw new DuplicateParticipantException("Participante já cadastrado.");
            }
        }
    }

}
