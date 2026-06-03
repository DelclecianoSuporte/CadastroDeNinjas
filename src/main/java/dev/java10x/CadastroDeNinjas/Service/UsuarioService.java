package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.DTO.UsuarioDTO;
import dev.java10x.CadastroDeNinjas.Model.UsuarioModel;
import dev.java10x.CadastroDeNinjas.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioModel criarUsuario(UsuarioDTO dto){

        UsuarioModel usuario = new UsuarioModel();

        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRole("USER");

        return usuarioRepository.save(usuario);
    }

    public UsuarioModel buscarPorUsername(String username){

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));
    }
}
