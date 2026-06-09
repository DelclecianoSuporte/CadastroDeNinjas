package dev.java10x.CadastroDeNinjas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private String tipo;
    private String username;
}
