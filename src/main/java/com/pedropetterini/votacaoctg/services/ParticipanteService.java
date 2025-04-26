package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.exceptions.ParticipantNotFoundException;
import com.pedropetterini.votacaoctg.repositories.ParticipanteRepository;
import com.pedropetterini.votacaoctg.validators.ParticipanteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParticipanteService {
    private final ParticipanteRepository repository;
    private final ParticipanteValidator participanteValidator;

    public Participante addParticipante(Participante participante) {
        participanteValidator.validate(participante);
        return repository.save(participante);
    }

    public List<Participante> getAllParticipants() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public Participante getParticipantByNome(String nome) {
        return repository.findByNome(nome).orElseThrow(
                () -> new ParticipantNotFoundException("Participante não cadastrado."));
    }

    public List<Participante> getParticipantsByCategoria(String categoria) {
        return repository.findByCategoria(categoria).orElseThrow(
                () -> new ParticipantNotFoundException("Nenhuma participante encontrado."));
    }

    public Participante updateParticipante(UUID id, Participante participante) {
        if(repository.existsById(id)) {
            participante.setId(id);
            participanteValidator.validate(participante);
            return repository.save(participante);
        }else{
            throw new ParticipantNotFoundException("Participante não encontrado.");
        }
    }

    public void deleteParticipante(UUID id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }else{
            throw new ParticipantNotFoundException("Participante não encontrado.");
        }
    }
}
