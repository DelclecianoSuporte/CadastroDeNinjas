package dev.java10x.CadastroDeNinjas.Controller;

import dev.java10x.CadastroDeNinjas.DTO.LoginDTO;
import dev.java10x.CadastroDeNinjas.DTO.LoginResponseDTO;
import dev.java10x.CadastroDeNinjas.Model.UsuarioModel;
import dev.java10x.CadastroDeNinjas.Repository.UsuarioRepository;
import dev.java10x.CadastroDeNinjas.Security.JwtService;
import dev.java10x.CadastroDeNinjas.Service.UsuarioService;
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