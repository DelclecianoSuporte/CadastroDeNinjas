package dev.java10x.CadastroDeNinjas.Repository;

import dev.java10x.CadastroDeNinjas.Enums.Dificuldade;
import dev.java10x.CadastroDeNinjas.Model.MissaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissaoRepository extends JpaRepository<MissaoModel, Long> {

    List<MissaoModel> findByDificuldade(Dificuldade dificuldade);
}
