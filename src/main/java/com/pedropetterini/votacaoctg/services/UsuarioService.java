package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import com.pedropetterini.votacaoctg.validators.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;


    public Usuario addUsuario(Usuario usuario) {
        usuarioValidator.validate(usuario);
        return usuarioRepository.save(usuario);
    }
}
