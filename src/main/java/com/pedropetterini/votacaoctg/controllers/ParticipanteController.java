package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Participante;
import com.pedropetterini.votacaoctg.exceptions.DuplicateParticipantException;
import com.pedropetterini.votacaoctg.exceptions.ErrorResponse;
import com.pedropetterini.votacaoctg.exceptions.ParticipantNotFoundException;
import com.pedropetterini.votacaoctg.services.ParticipanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("votacaoCTG/participantes")
@RequiredArgsConstructor
public class ParticipanteController {
    private final ParticipanteService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createParticipant(@Valid @RequestBody Participante participante) {
        try {
            var newParticipant = this.service.addParticipante(participante);
            return ResponseEntity.status(HttpStatus.CREATED).body(newParticipant);
        }catch (DuplicateParticipantException e) {
            var errorMessage = ErrorResponse.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Participante>> getAllParticipants() {
        var participants = this.service.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<Object> getParticipantByName(@PathVariable String name) {
        try {
            var participante = service.getParticipantByNome(name);
            return ResponseEntity.ok(participante);
        }catch (ParticipantNotFoundException e) {
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @GetMapping("/get-by-categoria/{categoria}")
    public ResponseEntity<Object> getParticipantByCategoria(@PathVariable String categoria) {
        try {
            var participantes = this.service.getParticipantsByCategoria(categoria);
            return ResponseEntity.ok(participantes);
        }catch (ParticipantNotFoundException e) {
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Object> updateParticipant(@PathVariable UUID id, @Valid @RequestBody Participante participante) {
//        try {
//            var part = this.service.updateParticipante(id, participante);
//            return ResponseEntity.ok(part);
//        }catch (ParticipantNotFoundException e) {
//            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
//            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
//        }catch (DuplicateParticipantException e) {
//            var errorMessage = ErrorResponse.conflictResponse(e.getMessage());
//            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
//        }
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteParticipant(@PathVariable UUID id) {
        try {
            this.service.deleteParticipante(id);
            return ResponseEntity.noContent().build();
        }catch (ParticipantNotFoundException e) {
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }
}
