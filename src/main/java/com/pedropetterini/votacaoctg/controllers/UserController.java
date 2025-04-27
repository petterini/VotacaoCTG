package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @PostMapping("/editarUsuario")
    public String editarUsuario(Usuario usuario, Model model) {
        try {
            usuarioService.updateUsuario(usuario);
            model.addAttribute("mensagem", "Usuario editado com sucesso!");
        }catch (Exception e) {
            model.addAttribute("mensagem", "Erro ao editar usuario");
        }
        return "editarUsuario";
    }

    @PostMapping("/excluirUsuario")
    public String excluirUsuario(@RequestParam Long numMesa, Model model) {
        try {
            usuarioService.deleteUsuario(numMesa);
            model.addAttribute("mensagem", "Usuario excluido com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mensagem", "Erro ao excluir usuario");
        }
        return "excluirUsuario";
    }

}
