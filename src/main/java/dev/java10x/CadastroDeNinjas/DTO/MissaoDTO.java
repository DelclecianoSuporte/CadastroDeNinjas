package dev.java10x.CadastroDeNinjas.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissaoDTO {

    @NotBlank(message = "O nome da missão não pode estar vazio")
    private String nome;
    @NotBlank(message = "A dificuldade não pode estar vazia")
    private String dificuldade;
}
