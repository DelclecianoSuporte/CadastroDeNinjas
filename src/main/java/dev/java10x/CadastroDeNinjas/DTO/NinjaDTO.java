package dev.java10x.CadastroDeNinjas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NinjaDTO {

    private String nome;
    private String email;
    private int idade;
}
