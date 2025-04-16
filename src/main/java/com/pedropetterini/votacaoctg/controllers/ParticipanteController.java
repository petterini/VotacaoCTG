package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.exceptions.DuplicateParticipantException;
import com.pedropetterini.votacaoctg.exceptions.ErrorResponse;
import com.pedropetterini.votacaoctg.repositories.ParticipanteRepository;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("votacaoCTG/participantes")
@RequiredArgsConstructor
public class ParticipanteController {
    private final ParticipanteService service;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Participante participante) {
        try {
            var newParticipant = service.addParticipante(participante);
            return ResponseEntity.status(HttpStatus.CREATED).body(newParticipant);
        }catch (DuplicateParticipantException e) {
            var errorMessage = ErrorResponse.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }
}
