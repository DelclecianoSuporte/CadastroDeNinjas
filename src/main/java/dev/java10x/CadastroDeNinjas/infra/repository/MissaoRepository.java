package dev.java10x.CadastroDeNinjas.infra.repository;

import dev.java10x.CadastroDeNinjas.domain.enums.Dificuldade;
import dev.java10x.CadastroDeNinjas.domain.model.MissaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissaoRepository extends JpaRepository<MissaoModel, Long> {

    List<MissaoModel> findByDificuldade(Dificuldade dificuldade);
}
