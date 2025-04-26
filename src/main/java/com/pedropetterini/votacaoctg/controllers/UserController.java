package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrarUsuario")
    public String cadastrarUsuario(Usuario usuario, Model model) {
        try {
            usuarioService.addUsuario(usuario);
            model.addAttribute("mensagem", "Usuario cadastrado com sucesso!");
        }catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao cadastrar usuário");
        }
        return "cadastrarUsuario";
    }
}
