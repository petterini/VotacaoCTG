package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.DuplicateUserException;
import com.pedropetterini.votacaoctg.exceptions.ErrorResponse;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("votacaoCTG/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody Usuario usuario) {
        try {
            var newUser = this.usuarioService.addUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }catch (DuplicateUserException e) {
            var errorMessage = ErrorResponse.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }catch (IllegalArgumentException e) {
            var errorMessage = ErrorResponse.invalidCpfResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }
}
