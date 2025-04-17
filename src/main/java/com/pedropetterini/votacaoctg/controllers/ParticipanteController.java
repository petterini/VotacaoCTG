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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("votacaoCTG/participantes")
@RequiredArgsConstructor
public class ParticipanteController {
    private final ParticipanteService service;

    @PostMapping
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

    @GetMapping("/get-by-tipo/{tipo}")
    public ResponseEntity<Object> getParticipantByTipo(@PathVariable String tipo) {
        try {
            var participantes = this.service.getParticipantsByTipo(tipo);
            return ResponseEntity.ok(participantes);
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

    @GetMapping("/get-by-categoria-tipo/{categoria}/{tipo}")
    public ResponseEntity<Object> getParticipantByCategoriaAndTipo(
            @PathVariable String categoria,
            @PathVariable String tipo) {
        try {
            var participantes = this.service.getParticipantsByCategoriaAndTipo(categoria, tipo);
            return ResponseEntity.ok(participantes);
        } catch (ParticipantNotFoundException e) {
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParticipant(@PathVariable Long id, @Valid @RequestBody Participante participante) {
        try {
            var part = this.service.updateParticipante(id, participante);
            return ResponseEntity.ok(part);
        }catch (ParticipantNotFoundException e) {
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }catch (DuplicateParticipantException e) {
            var errorMessage = ErrorResponse.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParticipant(@PathVariable Long id) {
        try {
            this.service.deleteParticipante(id);
            return ResponseEntity.noContent().build();
        }catch (ParticipantNotFoundException e) {
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }
}
