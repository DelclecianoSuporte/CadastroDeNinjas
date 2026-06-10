package dev.java10x.CadastroDeNinjas.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Entity ele transforma uma classe em uma entidade do BD
@Entity
@Table(name = "tb_cadastro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NinjaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String nome;
    private String email;
    private int idade;

    //@ManyToOne - Um ninja tem uma unica missão
    @ManyToOne
    @JoinColumn(name = "missao_id") //Foreign Key chave estrangeira
    private MissaoModel missao;
}
