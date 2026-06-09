package dev.java10x.CadastroDeNinjas.domain.model;

import dev.java10x.CadastroDeNinjas.domain.enums.Dificuldade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_missoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private Dificuldade dificuldade;

    //@OneToMany - Uma missão pode ter varios ninjas
    @OneToMany(mappedBy = "missao")
    private List<NinjaModel> ninjas;
}
