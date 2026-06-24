package dev.java10x.CadastroDeNinjas.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NinjaDTO {

    private Long id;

    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @NotBlank(message = "O email não pode estar vazio")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 1, message = "A idade mínima é 1")
    private int idade;

    private Long missaoId;
}
