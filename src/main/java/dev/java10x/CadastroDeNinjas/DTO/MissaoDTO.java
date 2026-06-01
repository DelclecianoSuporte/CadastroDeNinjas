package dev.java10x.CadastroDeNinjas.DTO;

import dev.java10x.CadastroDeNinjas.Enums.Dificuldade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissaoDTO {

    @NotBlank(message = "O nome da missão não pode estar vazio")
    private String nome;

    @NotNull(message = "A dificuldade é obrigatória")
    private Dificuldade dificuldade;
}
