package dev.java10x.CadastroDeNinjas.presentation.Controller;

import dev.java10x.CadastroDeNinjas.application.dto.LoginDTO;
import dev.java10x.CadastroDeNinjas.application.dto.LoginResponseDTO;
import dev.java10x.CadastroDeNinjas.domain.model.UsuarioModel;
import dev.java10x.CadastroDeNinjas.infra.repository.UsuarioRepository;
import dev.java10x.CadastroDeNinjas.infra.security.JwtService;
import dev.java10x.CadastroDeNinjas.application.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        UsuarioModel usuario = usuarioRepository.findByUsername(dto.getUsername()).orElseThrow();

        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(
                token,
                "Bearer",
                usuario.getUsername()
        );
    }

}