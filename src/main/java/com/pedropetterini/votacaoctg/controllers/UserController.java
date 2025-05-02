package com.pedropetterini.votacaoctg.controllers;

import com.pedropetterini.votacaoctg.entities.Usuario;
import com.pedropetterini.votacaoctg.exceptions.DuplicateUserException;
import com.pedropetterini.votacaoctg.exceptions.InvalidCpfException;
import com.pedropetterini.votacaoctg.exceptions.UserNotFoundException;
import com.pedropetterini.votacaoctg.exceptions.UserVoteExistsException;
import com.pedropetterini.votacaoctg.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrarUsuario")
    public String cadastrarUsuario(Usuario usuario, Model model) {
        try {
            usuarioService.addUsuario(usuario);
            model.addAttribute("mensagem", "Usuario cadastrado com sucesso!");
        }catch (DuplicateUserException | InvalidCpfException e) {
            model.addAttribute("mensagem", e.getMessage());
        }
        return "cadastrarUsuario";
    }

    @PostMapping("/editarUsuario")
    public String editarUsuario(Usuario usuario, Model model) {
        try {
            usuarioService.updateUsuario(usuario);
            model.addAttribute("mensagem", "Usuario editado com sucesso!");
        }catch (UserNotFoundException | InvalidCpfException | DuplicateUserException e) {
            model.addAttribute("mensagem", e.getMessage());
        }
        return "editarUsuario";
    }

    @PostMapping("/excluirUsuario")
    public String excluirUsuario(@RequestParam Long numMesa, Model model) {
        try {
            usuarioService.deleteUsuario(numMesa);
            model.addAttribute("mensagem", "Usuario excluido com sucesso!");
        } catch (UserNotFoundException | UserVoteExistsException e) {
            model.addAttribute("mensagem", e.getMessage());
        }
        return "excluirUsuario";
    }

}
