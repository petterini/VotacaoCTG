package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.UserNotFoundException;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import com.pedropetterini.votacaoctg.repositories.VotoRepository;
import com.pedropetterini.votacaoctg.validators.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final PasswordEncoder passwordEncoder;
    private final VotoRepository votoRepository;


    public Usuario addUsuario(Usuario usuario) {
        usuarioValidator.validate(usuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getCpf()));
        return usuarioRepository.save(usuario);
    }

    public Usuario getByMesa(Long mesa) {
        return usuarioRepository.findByMesa(mesa);
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "mesa"));
    }

    public Object getById(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Usuário não encontrado.")
        );
    }

    public Usuario updateUsuario(Usuario usuario) {
        if(usuarioRepository.existsByMesa(usuario.getMesa())) {
            var newUser = usuarioRepository.findByMesa(usuario.getMesa());
            newUser.setCpf(usuario.getCpf());
            newUser.setSenha(passwordEncoder.encode(usuario.getCpf()));
            newUser.setRoles(usuario.getRoles());

            usuarioValidator.validate(newUser);

            return usuarioRepository.save(newUser);
        }else{
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    public void deleteUsuario(UUID id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }else{
            throw new UserNotFoundException("Usuário não encontrado.");
        }
    }

    @Transactional
    public void deleteUsuario(Long numMesa) {
        if(usuarioRepository.existsByMesa(numMesa)) {
            usuarioRepository.deleteByMesa(numMesa);
        }else {
            throw new UserNotFoundException("Mesa não cadastrada.");
        }

    }
}
