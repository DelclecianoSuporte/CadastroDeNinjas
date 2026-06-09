package dev.java10x.CadastroDeNinjas.presentation.Controller;

import dev.java10x.CadastroDeNinjas.application.dto.UsuarioDTO;
import dev.java10x.CadastroDeNinjas.domain.model.UsuarioModel;
import dev.java10x.CadastroDeNinjas.application.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioModel criarUsuario(@RequestBody UsuarioDTO dto){

        return usuarioService.criarUsuario(dto);
    }
}
