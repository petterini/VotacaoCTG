package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.UserNotFoundException;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import com.pedropetterini.votacaoctg.validators.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;


    public Usuario addUsuario(Usuario usuario) {
        usuarioValidator.validate(usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Usuario getById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado.")
        );
    }

    public Usuario updateUsuario(Usuario usuario) {
        if(usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        }else{
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    public void deleteUsuario(Long id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }else{
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }
}
