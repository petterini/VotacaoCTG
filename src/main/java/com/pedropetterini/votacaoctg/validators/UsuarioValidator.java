package com.pedropetterini.votacaoctg.validators;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.DuplicateUserException;
import com.pedropetterini.votacaoctg.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validate(Usuario usuario) {
        if(usuarioRepository.findById(usuario.getId()).isPresent()) {
            throw new DuplicateUserException("Usuário já cadastrado.");
        }

        if (usuario.getCpf() == null || usuario.getCpf().length() != 11 || !isValidCPF(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) return false;

        int dig1 = calculaDigito(cpf.substring(0, 9));
        int dig2 = calculaDigito(cpf.substring(0, 9) + dig1);
        return cpf.equals(cpf.substring(0, 9) + dig1 + dig2);
    }

    private int calculaDigito(String base) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * (base.length() + 1 - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
}
