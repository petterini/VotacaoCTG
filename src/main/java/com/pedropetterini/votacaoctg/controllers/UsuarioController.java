package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.DuplicateUserException;
import com.pedropetterini.votacaoctg.exceptions.ErrorResponse;
import com.pedropetterini.votacaoctg.exceptions.UserNotFoundException;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("votacaoCTG/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Usuario usuario) {
        try {
            var newUser = this.usuarioService.addUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (DuplicateUserException e) {
            var errorMessage = ErrorResponse.conflictResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        } catch (IllegalArgumentException e) {
            var errorMessage = ErrorResponse.invalidCpfResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        var newUser = this.usuarioService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        try {
            var newUser = this.usuarioService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        }catch (UserNotFoundException e){
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody Usuario usuario) {
        try {
            var newUser = this.usuarioService.updateUsuario(usuario);
            return ResponseEntity.status(HttpStatus.OK).body(newUser);
        }catch (UserNotFoundException e){
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        try {
            this.usuarioService.deleteUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (UserNotFoundException e){
            var errorMessage = ErrorResponse.userNotFoundResponse(e.getMessage());
            return ResponseEntity.status(errorMessage.status()).body(errorMessage);
        }
    }

}
