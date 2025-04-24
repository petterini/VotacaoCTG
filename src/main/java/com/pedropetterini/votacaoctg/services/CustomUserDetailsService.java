package com.pedropetterini.votacaoctg.services;

import com.pedropetterini.votacaoctg.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = service.getByMesa(Long.parseLong(username));

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }

        return User.builder()
                .username(user.getMesa() + "")
                .password(user.getSenha())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}
