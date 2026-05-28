package dev.java10x.CadastroDeNinjas.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NinjaDTO {

    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    @Min(value = 1, message = "A idade mínima é 1")
    private int idade;

    private Long missaoId;
}
